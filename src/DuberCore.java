
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Holds all game logic
 */
public class DuberCore extends Game {

   public final static int START = 0;
   public final static int MENU = 1;
   public final static int LEADERBOARD = 2;
   public final static int PREFERENCES = 3;

   public static final float STEP_TIME = 1f / 60f;
   public static final int VELOCITY_ITERATIONS = 6;
   public static final int POSITION_ITERATIONS = 2;

   public static final float WORLD_WIDTH = 240f;
   public static final float WORLD_HEIGHT = 135f;

   // Bit flags for collision categories
   public static final short TERRAIN = 0x0002;
   public static final short PLAYER = 0x0004;
   public static final short ENEMY = 0x0008;
   public static final short GRENADE = 0x0010;
   public static final short GRAPPLE = 0x0020;
   public static final short PROJECTILE = 0x0040;
   public static final short SENSOR = 0x0080;
   public static final short DESTRUCTION = 0x0100;

   private float accumulator = 0;

   public World world;
   public TileMap tileMap;
   public ArrayList<Body> bodyDeletionList;
   public ArrayList<Explosion> explosionBodyList;
   public ArrayList<Enemy> enemyRotateList;
   public ArrayList<Entity> entityList;

   public Player player;
   public int score;

   private GameScreen gameScreen;
   private MenuScreen menuScreen;
   private LeaderboardScreen leaderboardScreen;
   private PreferencesScreen preferencesScreen;

   @Override
   public void create() {
      this.changeScreen(MENU);
   }

   public void changeScreen(int screen) {
      switch (screen) {
         case START :
            if (gameScreen == null) {
               gameScreen = new GameScreen(this);
            }
            this.setScreen(gameScreen);
            break;
         case MENU :
            if (menuScreen == null) {
               menuScreen = new MenuScreen(this);
            }
            this.setScreen(menuScreen);
            break;
         case PREFERENCES :
            if (preferencesScreen == null) {
               preferencesScreen = new PreferencesScreen(this);
            }
            this.setScreen(preferencesScreen);
            break;
         case LEADERBOARD :
            if (leaderboardScreen == null) {
               leaderboardScreen = new LeaderboardScreen(this);
            }
            this.setScreen(leaderboardScreen);
            break;
      }
   }

   public void initialize() {

      // Initialize Box2d World
      Box2D.init();
      world = new World(new Vector2(0, -20), true);
      bodyDeletionList = new ArrayList<Body>();
      explosionBodyList = new ArrayList<Explosion>();
      enemyRotateList = new ArrayList<Enemy>();
      entityList = new ArrayList<Entity>();

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

         if (!this.bodyDeletionList.isEmpty()) {
            for (Body body : this.bodyDeletionList) {
               body.setActive(false);
               this.world.destroyBody(body);
            }
            this.bodyDeletionList.clear();
         }
         if (!this.explosionBodyList.isEmpty()) {
            for (Explosion explosion : this.explosionBodyList) {
               explosion.explode();
            }
         }
         explosionBodyList.clear();

         if (!this.enemyRotateList.isEmpty()) {
            for (Enemy enemy : this.enemyRotateList) {
               enemy.randRotate();
            }
         }
         enemyRotateList.clear();

         accumulator -= STEP_TIME;
      }
   }

   public void destroyTerrain(Vector2 breakPoint) {
      // Convert breakpoint to tilemap coords
      Vector2 tileMapBreakPoint = new Vector2(breakPoint.x * 2f, breakPoint.y * 2f);
      score += tileMap.clearTile(tileMapBreakPoint);
   }

   public void spawnPlayer() {
      // Create player
      BodyDef player1BodyDef = new BodyDef();
      boolean validSpawn;
      int x, y;
      do {
         validSpawn = true;
         x = (int) (Math.random() * (TileMap.MAP_COLS - 14) + 7);
         y = (int) (Math.random() * (TileMap.MAP_ROWS - 14) + 7);

         // Test if the surround 3x3 tiles are air
         for (int a = -6; a < 6; a++) {
            for (int b = -6; b < 6; b++) {
               if (!(tileMap.terrainArr[x + a][y + b] instanceof Air)) {
                  validSpawn = false;
                  break;
               }
            }
         }
      } while (!validSpawn);
      player1BodyDef.position.set(x / 2, y / 2);
      player = new Player(world, player1BodyDef);
      entityList.add(player);
   }

   public void spawnEnemy() {
      BodyDef enemyBodyDef = new BodyDef();
      boolean validSpawn;
      int x, y;
      do {
         validSpawn = true;
         x = (int) (Math.random() * (TileMap.MAP_COLS - 14) + 7);
         y = (int) (Math.random() * (TileMap.MAP_ROWS - 14) + 7);

         // Test if the surround 3x3 tiles are air
         for (int a = -6; a < 6; a++) {
            for (int b = -6; b < 6; b++) {
               if (!(tileMap.terrainArr[x + a][y + b] instanceof Air)) {
                  validSpawn = false;
                  break;
               }
            }
         }
      } while (!validSpawn);
      enemyBodyDef.position.set(x / 2, y / 2);
      GruntEnemy enemy = new GruntEnemy(this.world, enemyBodyDef);
      enemyRotateList.add(enemy);
      entityList.add(enemy);
      

   }
}