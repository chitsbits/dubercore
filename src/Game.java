
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

      spawnPlayer();

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

   public void spawnPlayer(){
      // Create player
      BodyDef player1BodyDef = new BodyDef();
      boolean validSpawn;
      int x, y;
      do {
         validSpawn = true;
         x = (int) (Math.random() * (TileMap.MAP_COLS - 14) + 7);
         y = (int) (Math.random() * (TileMap.MAP_ROWS - 14) + 7);

         // Test if the surround 3x3 tiles are air
         for(int a = -6; a < 6; a++){
            for(int b = -6; b < 6; b++){
               if(!(tileMap.terrainArr[x+a][y+b] instanceof Air)){
                  System.out.println("bad spawn found");
                  validSpawn = false;
                  break;
               }
            }
         }
      }
      while(!validSpawn);
      player1BodyDef.position.set(x/2, y/2);
      player1 = new Player(world, player1BodyDef);
   }
}