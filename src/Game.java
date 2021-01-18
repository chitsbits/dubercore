
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
import com.badlogic.gdx.physics.box2d.EdgeShape;
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

   // Bit flags for collision categories
   public static final short TERRAIN      = 0x0002;
   public static final short PLAYER       = 0x0004;
   public static final short ENEMY        = 0x0008;
   public static final short GRENADE      = 0x0010;
   public static final short GRAPPLE      = 0x0020;
   public static final short PROJECTILE   = 0x0040;
   public static final short SENSOR       = 0x0080;
   public static final short DESTRUCTION  = 0x0100;

   private float accumulator = 0;

   public World world;
   public TileMap tileMap;

   public Player player1;

   public Game() {
      initialize();
   }

   public void initialize() {

      // Initialize Box2d World
      Box2D.init();
      world = new World(new Vector2(0, -20), true);

      MyContactListener contactListener = new MyContactListener(this);
      world.setContactListener(contactListener);

      tileMap = new TileMap(world);

      makeCircleTest();

      // Create player
      BodyDef player1BodyDef = new BodyDef();
      /* boolean validSpawn = false;
      do {
         int x = (int)(Math.random() * (WORLD_WIDTH - 1));
         int y = (int)(Math.random() * (WORLD_HEIGHT - 1));

         int i = x / 2;
         int j = TileMap.MAP_ROWS - y / 2;
         if(tileMap.terrainArr[i][j] instanceof Air) {
            player1BodyDef.position.set(x, y);
            validSpawn = true;
         }
      }
      while(!validSpawn); */
      player1BodyDef.position.set(2, 2);
      player1 = new Player(world, player1BodyDef);

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

   public void destroyTerrain(Vector2 breakPoint){
      // Convert breakpoint to tilemap coords
      Vector2 tileMapBreakPoint = new Vector2(breakPoint.x / 2f, TileMap.MAP_ROWS - breakPoint.y / 2f);
      tileMap.clearTile(tileMapBreakPoint);
   }

   public void makeCircleTest(){
      
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
      fixtureDef.filter.categoryBits = Game.PLAYER;
      fixtureDef.filter.maskBits = Game.TERRAIN | Game.PROJECTILE;
      fixtureDef.restitution = 0.6f; // Make it bounce a little bit

      // Create our fixture and attach it to the body
      Fixture fixture = body.createFixture(fixtureDef);

      // Remember to dispose of any shapes after you're done with them!
      // BodyDef and FixtureDef don't need disposing, but shapes do.
      circle.dispose();
   }
}