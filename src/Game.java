
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

/**
 * Holds all game logic
 */
public class Game {

   public static final float STEP_TIME = 1f / 60f;
   public static final int VELOCITY_ITERATIONS = 6;
   public static final int POSITION_ITERATIONS = 2;

   public static final float WORLD_WIDTH = 240f;
   public static final float WORLD_HEIGHT = 135f;

   private float accumulator = 0;

   public World world;
   
   public Player player1;

   public Game() {
      initialize();
   }
   
   public void initialize() {

      // Initialize Box2d World
      Box2D.init();
      world = new World(new Vector2(0, -10), true);

      // First we create a body definition
      BodyDef bodyDef = new BodyDef();
      // We set our body to dynamic, for something like ground which doesn't move we
      // would set it to StaticBody
      bodyDef.type = BodyType.DynamicBody;
      // Set our body's starting position in the world
      bodyDef.position.set(12, 30);

      // Create our body in the world using our body definition
      Body body = world.createBody(bodyDef);

      // Create a circle shape and set its radius to 6
      CircleShape circle = new CircleShape();
      circle.setRadius(1f);

      // Create a fixture definition to apply our shape to
      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.shape = circle;
      fixtureDef.density = 0.5f;
      fixtureDef.friction = 0.4f;
      fixtureDef.restitution = 0.6f; // Make it bounce a little bit

      // Create our fixture and attach it to the body
      Fixture fixture = body.createFixture(fixtureDef);

      // Remember to dispose of any shapes after you're done with them!
      // BodyDef and FixtureDef don't need disposing, but shapes do.
      circle.dispose();

      // Create our body definition
      BodyDef groundBodyDef = new BodyDef();
      // Set its world position
      groundBodyDef.position.set(new Vector2(0, 10));

      // Create a body from the definition and add it to the world
      Body groundBody = world.createBody(groundBodyDef);

      // Create a polygon shape
      PolygonShape groundBox = new PolygonShape();
      // Set the polygon shape as a box which is twice the size of our view port and
      // 20 high
      // (setAsBox takes half-width and half-height as arguments)
      groundBox.setAsBox(24f, 5.0f);
      // Create a fixture from our polygon shape and add it to our ground body
      groundBody.createFixture(groundBox, 0.0f);
      // Clean up after ourselves
      groundBox.dispose();


      player1 = new Player();
      player1.body = world.createBody(player1.bodyDef);
      PolygonShape playerShape = new PolygonShape();
      playerShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
      player1.body.createFixture(playerShape, 0.0f);
      playerShape.dispose();

      //generateWorld();
   }

   public void doPhysicsStep(float deltaTime) {
      // fixed time step
      // max frame time to avoid spiral of death (on slow devices)
      float frameTime = Math.min(deltaTime, 0.25f);
      accumulator += frameTime;
      while (accumulator >= STEP_TIME) {
         world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
         accumulator -= STEP_TIME;
      }
   }

   // scuffed not working
   private void generateWorld() {
      Vector2[] vertices = new Vector2[100];
      Random random = new Random();
      for(int i = 0; i <= 100; i++){
         vertices[i] = new Vector2(random.nextFloat() * WORLD_WIDTH, random.nextFloat() * WORLD_HEIGHT);
      }
      PolygonShape polygon = new PolygonShape();
      polygon.set(vertices);

      BodyDef bodyDef = new BodyDef();
      // Set its world position
      bodyDef.position.set(new Vector2(0, 10));

      Body polyBody = world.createBody(bodyDef);
      polyBody.createFixture(polygon, 0.0f);
      polygon.dispose();

   }
}