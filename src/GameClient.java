
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameClient extends ApplicationAdapter {

    // Camera dimensions in metres. TODO: scale with monitor
    public static final float CAMERA_WIDTH = 32f;
    public static final float CAMERA_HEIGHT = 18f;

    Game localGame; // Local instance of the game
    OrthographicCamera camera;
    SpriteBatch batch;
    Viewport viewport;
    Player player;

    Box2DDebugRenderer debugRenderer;

    public void create() {

        localGame = new Game();
        player = localGame.player1;
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        // viewport = new FitViewport(800, 480, camera);
        //camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.setToOrtho(false, Game.WORLD_WIDTH, Game.WORLD_HEIGHT);

        debugRenderer = new Box2DDebugRenderer();
    }

    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Focus camera on player
        camera.position.set(player.getPos().x, player.getPos().y, 0);

        // tell the camera to update its matrices.
        camera.update();

        // Player input
        // apply left impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Keys.A) && player.getVel().x > -Player.MAX_VELOCITY) {			
            player.moveLeft();
        }

        // apply right impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Keys.D) && player.getVel().x < Player.MAX_VELOCITY) {
            player.moveRight();
        }

        // apply right impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Keys.W)) {
            player.jump();
        }
        
        //System.out.println("x: " + player.getPos().x + " y: " + player.getPos().y);
        System.out.println(Gdx.graphics.getFramesPerSecond());

        // Step physics world
        localGame.doPhysicsStep(Gdx.graphics.getDeltaTime());

        // Render Box2D world
        debugRenderer.render(localGame.world, camera.combined);
    }

    public void resize(int width, int height) {

    }

}
