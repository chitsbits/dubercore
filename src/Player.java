import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Timer.Task;

public class Player extends Entity {

    public static final float PLAYER_WIDTH = 0.5f;
    public static final float PLAYER_HEIGHT = 0.8f;
    public static final float MAX_VELOCITY = 10f;

    public boolean canJump;
    private Task jumpTimerTask;

    PolygonShape feetShape;
    FixtureDef feetFixtureDef;
    Fixture feetFixture;
    BodyDef feetBodyDef;
    
    public Player(World world){

        canJump = false;

        // Body definition
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(10,24);

        // Shape definition
        entityShape = new PolygonShape();
        entityShape.setAsBox(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);

        // Create body
        body = world.createBody(bodyDef);

        // Add main body fixture
        body.createFixture(entityShape, 0.0f);
        body.setFixedRotation(true);

        // Feet shape definition
        feetShape = new PolygonShape();
        Vector2 feetCenter = new Vector2();
        feetCenter.x = getPos().x;
        feetCenter.y = getPos().y;
        feetShape.setAsBox(Player.PLAYER_WIDTH - 0.02f, 0.15f, new Vector2(0, -0.8f), 0);

        // Add feet fixture
        feetFixtureDef = new FixtureDef();
        feetFixture = body.createFixture(feetShape, 0.0f);
        feetFixture.setSensor(true);

        // Add player's user data to the fixture
        feetFixture.setUserData(this);

        entityShape.dispose();
        feetShape.dispose();
        
    }

    public Vector2 getPos() {
        return body.getPosition();
    }

    public Vector2 getVel() {
        return body.getLinearVelocity();
    }

    public void moveLeft() {
        body.applyLinearImpulse(-0.80f, 0, getPos().x, getPos().y, true);
    }

    public void moveRight() {
        body.applyLinearImpulse(0.80f, 0, getPos().x, getPos().y, true);
    }

    public void jump() {
        Vector2 vel = body.getLinearVelocity();
        vel.y = 10;
        body.setLinearVelocity(vel);
    }
}
