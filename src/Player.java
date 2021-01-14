import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Timer.Task;

public class Player extends Entity {

    public static final float PLAYER_WIDTH = 0.5f;
    public static final float PLAYER_HEIGHT = 0.8f;

    public static final float X_SPEED = 5;
    public static final float JUMP_SPEED = 10;
    public static final float MAX_VELOCITY = 10f;

    public int collidingCount;
    public boolean canJump;
    public boolean canMove;
    private GrapplingHook grapple;
    Vector2 grappleDirection;
    
    public Player(World world){

        collidingCount = 0;
        canJump = false;
        canMove = true;

        // Body definition
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(10,24);

        // Shape definition
        entityShape = new PolygonShape();
        Vector2[] vertices = new Vector2[6];

        vertices[0] = new Vector2(PLAYER_WIDTH, PLAYER_HEIGHT);
        vertices[1] = new Vector2(-PLAYER_WIDTH, PLAYER_HEIGHT);
        vertices[2] = new Vector2(-PLAYER_WIDTH, -PLAYER_HEIGHT + 0.2f);
        vertices[3] = new Vector2(-PLAYER_WIDTH + 0.2f, -PLAYER_HEIGHT);
        vertices[4] = new Vector2(PLAYER_WIDTH - 0.2f, -PLAYER_HEIGHT);
        vertices[5] = new Vector2(PLAYER_WIDTH, -PLAYER_HEIGHT + 0.2f);

        /*
        vertices[0] = new Vector2(0 + 0.05f, 0);
        vertices[1] = new Vector2(PLAYER_WIDTH*2 - 0.05f, 0);
        vertices[2] = new Vector2(PLAYER_WIDTH*2, 0.05f);
        vertices[3] = new Vector2(PLAYER_WIDTH*2, PLAYER_HEIGHT*2);
        vertices[4] = new Vector2(0, PLAYER_HEIGHT*2);
        vertices[5] = new Vector2(0, 0.05f);
        */
        //((PolygonShape)entityShape).setAsBox(PLAYER_WIDTH, PLAYER_HEIGHT);
        ((PolygonShape)entityShape).set(vertices);
        // Create body
        body = world.createBody(bodyDef);

        // Add main body fixture
        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = entityShape;
        bodyFixtureDef.filter.categoryBits = Game.PLAYER;
        bodyFixtureDef.filter.maskBits = Game.TERRAIN | Game.PROJECTILE;
        bodyFixtureDef.friction = 1.0f;
        body.createFixture(bodyFixtureDef);
        body.setFixedRotation(true);

        // Feet shape definition
        PolygonShape feetShape = new PolygonShape();
        Vector2 feetCenter = new Vector2();
        feetCenter.x = getPos().x;
        feetCenter.y = getPos().y;
        feetShape.setAsBox(PLAYER_WIDTH - 0.05f, 0.1f, new Vector2(0, -0.85f), 0);

        // Add feet fixture
        FixtureDef feetFixtureDef = new FixtureDef();
        feetFixtureDef.shape = feetShape;
        feetFixtureDef.filter.categoryBits = Game.SENSOR;
        feetFixtureDef.filter.maskBits = Game.TERRAIN;
        feetFixtureDef.isSensor = true;
        Fixture feetFixture = body.createFixture(feetFixtureDef);

        // Add player's user data to the fixture
        feetFixture.setUserData(this);

        entityShape.dispose();
        feetShape.dispose();
        
    }

    public void shootGrapple(World world, Vector3 mousePos) {

        grapple = new GrapplingHook(world, this);
        
        grappleDirection = new Vector2();
        grappleDirection.x = mousePos.x - getPos().x;
        grappleDirection.y = mousePos.y - getPos().y;
        grappleDirection.clamp(40f, 40f);
        grapple.body.setLinearVelocity(grappleDirection);
    }   

    public void retractGrapple(World world) {
        world.destroyBody(grapple.body);
        body.setGravityScale(1);
        canMove = true;
    }

    public void followGrapple(){
        grappleDirection = new Vector2();
        grappleDirection.x = grapple.getPos().x - getPos().x;
        grappleDirection.y = grapple.getPos().y - getPos().y;
        grappleDirection.clamp(20f, 20f);
        
        body.setLinearVelocity(grappleDirection);
        body.setGravityScale(0);
        canMove = false;
    }

    public void moveLeft() {
        body.applyLinearImpulse(-0.80f, 0, getPos().x, getPos().y, true);
        //body.setLinearVelocity(-X_SPEED, getVel().y);
    }

    public void moveRight() {
        body.applyLinearImpulse(0.80f, 0, getPos().x, getPos().y, true);
    }

    public void jump() {
        body.setLinearVelocity(getVel().x, 10);
    }
}
