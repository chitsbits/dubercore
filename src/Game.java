
import java.util.ArrayList;
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

   private float accumulator = 0;

   public World world;
   public TileMap tileMap;
   //public ArrayList<Entity> entityList;
   public ArrayList<Body> bodyDeletionList;
   public ArrayList<Explosion> explosionBodyList;

   public Player player1;
   public GruntEnemy testDummy;

   public Game() {
      initialize();
   }

   public void initialize() {

      // Initialize Box2d World
      Box2D.init();
      world = new World(new Vector2(0, -20), true);
      bodyDeletionList = new ArrayList<Body>();
      explosionBodyList = new ArrayList<Explosion>();

      MyContactListener contactListener = new MyContactListener(this);
      world.setContactListener(contactListener);

      // World edge
      Vector2 a = new Vector2(0, WORLD_HEIGHT);
      Vector2 b = new Vector2(WORLD_WIDTH, WORLD_HEIGHT);
      Vector2 c = new Vector2(WORLD_WIDTH, 0);
      Vector2 d = new Vector2(0, 0);

      makeEdgeShape(a, b);
      makeEdgeShape(b, c);
      makeEdgeShape(c, d);
      makeEdgeShape(d, a);

      // Initialize map
      tileMap = new TileMap();
      int[][] cornerArr = tileMap.cornerArr;
      for (int i = 0; i < TileMap.MAP_COLS; i++) {
         for (int j = 0; j < TileMap.MAP_ROWS; j++) {
            // Marching square edges
            a = new Vector2((float) (i + 0.5), (float) (j));
            b = new Vector2((float) (i + 1), (float) (j + 0.5));
            c = new Vector2((float) (i + 0.5), (float) (j + 1));
            d = new Vector2((float) (i), (float) (j + 0.5));

            // Which contour - determined by the 4 corners of the tile
            int tileCase = getTileMarchCase(cornerArr[i][j], cornerArr[i+1][j], cornerArr[i+1][j+1], cornerArr[i][j+1]);

            switch (tileCase) {
               case 1:
                  makeEdgeShape(c, d);
                  break;
               case 2:
                  makeEdgeShape(b, c);
                  break;
               case 3:
                  makeEdgeShape(b, d);
                  break;
               case 4:
                  makeEdgeShape(a, b);
                  break;
               case 5:
                  makeEdgeShape(a, d);
                  makeEdgeShape(b, c);
                  break;
               case 6:
                  makeEdgeShape(a, c);
                  break;
               case 7:
                  makeEdgeShape(a, d);
                  break;
               case 8:
                  makeEdgeShape(a, d);
                  break;
               case 9:
                  makeEdgeShape(a, c);
                  break;
               case 10:
                  makeEdgeShape(a, b);
                  makeEdgeShape(c, d);
                  break;
               case 11:
                  makeEdgeShape(a, b);
                  break;
               case 12:
                  makeEdgeShape(b, d);
                  break;
               case 13:
                  makeEdgeShape(b, c);
                  break;
               case 14:
                  makeEdgeShape(c, d);
                  break;
            }

            // do sprite stuff on tile
         }
      }

      makeCircleTest();

      // Create player
      player1 = new Player(this);
      testDummy = new GruntEnemy(this);

   }

   public void doPhysicsStep(float deltaTime) {
      // fixed time step
      // max frame time to avoid spiral of death (on slow devices)
      float frameTime = Math.min(deltaTime, 0.25f);
      accumulator += frameTime;
      while (accumulator >= STEP_TIME) {
         world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

         if (!this.bodyDeletionList.isEmpty()){

            for (Body body : this.bodyDeletionList) {
            
               body.setActive(false);
               this.world.destroyBody(body);
               //System.out.println("removed");
         }
        this.bodyDeletionList.clear();
        }

        if (!this.explosionBodyList.isEmpty()){

            for (Explosion explosion : this.explosionBodyList) {
               //System.out.println("explosion :o");
               explosion.explode();
               
            }
        }
        explosionBodyList.clear();

         accumulator -= STEP_TIME;
      }
   }

   private int getTileMarchCase(int a, int b, int c, int d) {
      return a * 8 + b * 4 + c * 2 + d;
   }

   private void makeEdgeShape(Vector2 v1, Vector2 v2) {

      EdgeShape edgeShape = new EdgeShape();
      edgeShape.set(v1, v2);

      BodyDef edgeDef = new BodyDef();
      edgeDef.type = BodyType.StaticBody;

      Body edgeBody = world.createBody(edgeDef);

      FixtureDef edgeFixtureDef = new FixtureDef();
      edgeFixtureDef.shape = edgeShape;
      edgeFixtureDef.filter.categoryBits = Game.TERRAIN;
      edgeFixtureDef.filter.maskBits = Game.PLAYER | Game.ENEMY | Game.PROJECTILE | Game.GRAPPLE | Game.SENSOR;
      edgeFixtureDef.friction = 0.5f;
      edgeBody.createFixture(edgeFixtureDef);

      edgeShape.dispose();
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