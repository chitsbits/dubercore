import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.esotericsoftware.kryonet.Client;

public class GameClient extends ApplicationAdapter implements InputProcessor {

    // Camera dimensions in metres. TODO: scale with monitor
    public static final float CAMERA_WIDTH = 32f;
    public static final float CAMERA_HEIGHT = 18f;

    Client client;

    Game localGame; // Local instance of the game
    OrthographicCamera camera;
    SpriteBatch worldBatch;
    SpriteBatch hudBatch;
    Viewport viewport;
    Player player;

    Box2DDebugRenderer debugRenderer;
    ShapeRenderer sr;

    boolean useDebugCamera = false;

    BitmapFont font;

    public static TextureAtlas textureAtlas;

    int screenX;
    int screenY;
    float clock;

    @Override
    public void create() {

        textureAtlas = new TextureAtlas("assets\\sprites.txt");
        font = new BitmapFont();
        worldBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        // viewport = new FitViewport(800, 480, camera);

        if (useDebugCamera)
            camera.setToOrtho(false, Game.WORLD_WIDTH, Game.WORLD_HEIGHT);
        else
            camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

        debugRenderer = new Box2DDebugRenderer();
        sr = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);

        connectToServer();

        System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
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
        // System.out.println(Gdx.graphics.getFramesPerSecond());

        // Focus camera on player
        if (!useDebugCamera)
            camera.position.set(player.getPos().x, player.getPos().y, 0);

        // tell the camera to update its matrices.
        camera.update();

        worldBatch.begin();
        worldBatch.setProjectionMatrix(camera.combined);

        // Draw map sprites
        Terrain[][] terrainArr = localGame.tileMap.terrainArr;

        // Set bounds of the map to render
        int iStart = (int) (Math.max(0f, (camera.position.x - camera.viewportWidth / 2f) * 2));
        int jStart = (int) (Math.max(0f, (camera.position.y - camera.viewportHeight / 2f) * 2));

        int iEnd = (int) (Math.min(TileMap.MAP_COLS, (camera.position.x + camera.viewportWidth / 2f) * 2 + 1));
        int jEnd = (int) (Math.min(TileMap.MAP_ROWS - 1, (camera.position.y + camera.viewportHeight / 2f) * 2) + 1);

        for (int i = iStart; i < iEnd; i++) {
            for (int j = jStart; j < jEnd; j++) {
                Terrain tile = terrainArr[i][j];
                Sprite sprite = tile.sprite;
                sprite.setBounds(tile.worldX, tile.worldY, 0.5f, 0.5f);
                sprite.draw(worldBatch);
            }
        }

        // Draw entities
        for (Entity ent : localGame.entityList) {
            Sprite sprite = ((Entity) (ent.body.getUserData())).sprite;
            sprite.setPosition(ent.body.getPosition().x - sprite.getWidth() / 2,
                    ent.body.getPosition().y - sprite.getHeight() / 2);
            sprite.draw(worldBatch);
        }
        worldBatch.end();

        // Draw hud
        hudBatch.begin();
        font.draw(hudBatch, "Score: " + Integer.toString(localGame.score), 20, 20);
        hudBatch.end();

        // Render Box2D world
        debugRenderer.render(localGame.world, camera.combined);
        clock += Gdx.graphics.getDeltaTime();

        if (clock > 10) {
            System.out.println("spawned");
            localGame.spawnEnemy();
            clock = 0;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        localGame.world.dispose();
        worldBatch.dispose();
        hudBatch.dispose();
        textureAtlas.dispose();
        debugRenderer.dispose();
        sr.dispose();
        font.dispose();
    }

    public void connectToServer() {
        client = new Client();
        client.start();
        try {
            client.connect(5000, "127.0.0.1", 54555, 54777);
            JoinGameRequest joinRequest = new JoinGameRequest("chits");
            client.sendTCP(joinRequest);

        } catch (IOException e) {
            System.out.println("Unable to connect to server");
            e.printStackTrace();
            quitClient(-1); 
        }
    }

    /**
     * Properly dispose and close the client
     * @param status System.exit status
     */
    public void quitClient(int status){
        dispose();
        Gdx.app.exit();
        System.exit(status);  
    }
    
    // TODO: move all actual logic to game class, so that only inputs are sent to server

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.G && player.checkCooldown(player.lastGrenadeUse, Grenade.COOLDOWN)){
            Vector3 mousePos = camera.unproject(new Vector3(screenX, screenY, 0));

            player.throwGrenade(localGame, mousePos);
            player.lastGrenadeUse = System.currentTimeMillis();
            //player.grenadeCount = player.grenadeCount - 1;
            return true;
        }
        
        else if (keycode == Input.Keys.NUM_1){
            player.activeItem = 1;
            System.out.println("gun go pew");
            if(player.isGrappling){
                player.retractGrapple();
                localGame.bodyDeletionList.add(player.grapple.body);
            }
            return true;
        }
        else if (keycode == Input.Keys.NUM_2){
            player.activeItem = 2;
            System.out.println("grapple go hook");
            return true;
        }
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
        // Pickaxe
        if (button == Input.Buttons.RIGHT) {
            Vector3 mouseWorldPos = camera.unproject(new Vector3(screenX, screenY, 0));  // Maps the mouse from camera pos to world pos
            Vector2 pickaxeDirection = new Vector2(mouseWorldPos.x - player.getPos().x, mouseWorldPos.y - player.getPos().y).clamp(2, 2);
            Vector2 breakPoint = new Vector2(player.getPos().x + pickaxeDirection.x, player.getPos().y + pickaxeDirection.y);

            PickaxeRayCastCallback callback = new PickaxeRayCastCallback();
            localGame.world.rayCast(callback, player.getPos(), breakPoint);
            if (callback.collisionPoint != null) {
                localGame.destroyTerrain(callback.collisionPoint);
            }
            return true;
        }
        //firing weapon/grapple hook
        else if(button == Input.Buttons.LEFT){

            if (player.activeItem == 1 && player.checkCooldown(player.lastWeaponFire, player.getWeapon().fireRate)){
                Vector3 mousePos = camera.unproject(new Vector3(screenX, screenY, 0));
                player.getWeapon().fire(localGame, mousePos);
                player.lastWeaponFire = System.currentTimeMillis();
                return true;
            }

            else if (player.activeItem == 2 && player.checkCooldown(player.lastGrappleUse, GrapplingHook.COOLDOWN)){
                Vector3 mousePos = camera.unproject(new Vector3(screenX, screenY, 0));  // Maps the mouse from camera pos to world pos
                //System.out.println("shot grapple");
                player.shootGrapple(localGame.world, mousePos);
                player.lastGrappleUse = System.currentTimeMillis();
                return true; 
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT){
            if (player.activeItem == 2 && player.isGrappling){
                //System.out.println("released grapple");
                player.retractGrapple();
                localGame.bodyDeletionList.add(player.grapple.body);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        this.screenX = screenX;
        this.screenY = screenY;

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        if (amountY == 1 || amountY == -1){
            player.activeItem += amountY;
            if (player.activeItem > 2) {
                player.activeItem = 1;
            }
            else if (player.activeItem < 1) {
                player.activeItem = 2;
            }
            else if (player.activeItem == 1){
                System.out.println("gun go pew");
                if (player.isGrappling){
                    player.retractGrapple();
                    localGame.bodyDeletionList.add(player.grapple.body);
                }
            }
            else {
                System.out.println("grapple go hook");
            }
            return true;
        }
        return false;
    }

}
