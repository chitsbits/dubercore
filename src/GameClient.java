import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameClient extends ApplicationAdapter implements InputProcessor {

    // Camera dimensions in metres. TODO: scale with monitor
    public static final float CAMERA_WIDTH = 32f;
    public static final float CAMERA_HEIGHT = 18f;

    Game localGame; // Local instance of the game
    OrthographicCamera camera;
    SpriteBatch batch;
    Viewport viewport;
    Player player;
    Array<Body> tempBodies = new Array<Body>();

    Box2DDebugRenderer debugRenderer;
    ShapeRenderer sr;
    Vector2 tempMouseVector = new Vector2(0, 0);

    boolean mapNeedsRerender;

    private Texture textureStone;
    private Texture textureAir;

    @Override
    public void create() {

        localGame = new Game();
        player = localGame.player1;
        batch = new SpriteBatch();
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        // viewport = new FitViewport(800, 480, camera);
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        //camera.setToOrtho(false, Game.WORLD_WIDTH, Game.WORLD_HEIGHT);

        debugRenderer = new Box2DDebugRenderer();
        sr = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);

        mapNeedsRerender = true;
        textureStone = new Texture("assets\\stone.png");
        textureAir = new Texture("assets\\air.png");

        System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Player input
        // apply left impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Keys.A) && player.getVel().x > -Player.MAX_VELOCITY && player.canMove) {			
            player.moveLeft();
        }

        // apply right impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Keys.D) && player.getVel().x < Player.MAX_VELOCITY && player.canMove) {
            player.moveRight();
        }

        // apply right impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyJustPressed(Keys.W) && player.collidingCount > 0) {
            player.jump();
        }
        
        //System.out.println("x: " + player.getPos().x + " y: " + player.getPos().y);
        //System.out.println(Gdx.graphics.getFramesPerSecond());

        // Step physics world
        localGame.doPhysicsStep(Gdx.graphics.getDeltaTime());

        // Focus camera on player
        camera.position.set(player.getPos().x, player.getPos().y, 0);

        // tell the camera to update its matrices.
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        localGame.world.getBodies(tempBodies);
        for(Body body : tempBodies){
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        // player.playerSprite.setPosition(player.body.getPosition().x, player.body.getPosition().y);
        // player.playerSprite.draw(batch);
        if(mapNeedsRerender){
            Terrain[][] terrainArr = localGame.tileMap.terrainArr;
            for(int i = 0; i < TileMap.MAP_COLS; i++){
                for(int j = 0; j < TileMap.MAP_ROWS; j++){
                    Terrain tile = terrainArr[i][j];
                    if(tile instanceof Stone && tile.body != null) {
                        batch.draw(textureStone, tile.worldX, tile.worldY, 0.5f, 0.5f);
                    }
                }
            }
        }
        batch.end();

        // Render Box2D world
        debugRenderer.render(localGame.world, camera.combined);
        // Render test mouse line
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeType.Line);
        sr.line(player.getPos(), tempMouseVector);
        sr.end();

        sr.begin(ShapeType.Filled);
        sr.circle(player.getPos().x, player.getPos().y, 0.2f);
        sr.end();
        
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose(){
        localGame.world.dispose();
        debugRenderer.dispose();
        sr.dispose();
    }
    
    // TODO: move all actual logic to game class, so that only inputs are sent to server

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Grapple
        if (button == Input.Buttons.LEFT){
            Vector3 mousePos = camera.unproject(new Vector3(screenX, screenY, 0));  // Maps the mouse from camera pos to world pos
            player.shootGrapple(localGame.world, mousePos);
            return true; 
        }
        // Pickaxe
        else if (button == Input.Buttons.RIGHT) {
            Vector3 mouseWorldPos = camera.unproject(new Vector3(screenX, screenY, 0));  // Maps the mouse from camera pos to world pos
            Vector2 pickaxeDirection = new Vector2(mouseWorldPos.x - player.getPos().x, mouseWorldPos.y - player.getPos().y).clamp(2, 2);
            Vector2 breakPoint = new Vector2(player.getPos().x + pickaxeDirection.x, player.getPos().y + pickaxeDirection.y);

            PickaxeRayCastCallback callback = new PickaxeRayCastCallback();
            localGame.world.rayCast(callback, player.getPos(), breakPoint);
            if (callback.collisionPoint != null) {
                localGame.destroyTerrain(callback.collisionPoint);
                tempMouseVector = callback.collisionPoint;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT){
            player.retractGrapple(localGame.world);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
