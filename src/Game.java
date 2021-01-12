
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
      int[][] mapArr = tileMap.mapArr;
      for (int i = 0; i < TileMap.MAP_COLS - 1; i++) {
         for (int j = 0; j < TileMap.MAP_ROWS - 1; j++) {
            // Marching square edges
            a = new Vector2((float) (i + 0.5), (float) (j));
            b = new Vector2((float) (i + 1), (float) (j + 0.5));
            c = new Vector2((float) (i + 0.5), (float) (j + 1));
            d = new Vector2((float) (i), (float) (j + 0.5));

            // Which contour
            int tileCase = getTileMarchCase(mapArr[i][j], mapArr[i+1][j], mapArr[i+1][j+1], mapArr[i][j+1]);

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
         }
      }

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

   private int getTileMarchCase(int a, int b, int c, int d) {
      return a * 8 + b * 4 + c * 2 + d;
   }

   private void makeEdgeShape(Vector2 v1, Vector2 v2) {

      EdgeShape edgeShape = new EdgeShape();
      edgeShape.set(v1, v2);

      BodyDef edgeDef = new BodyDef();
      edgeDef.type = BodyType.StaticBody;

      Body edgeBody = world.createBody(edgeDef);

      edgeBody.createFixture(edgeShape, 0.0f);
      edgeShape.dispose();
   }
}