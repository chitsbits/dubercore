import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends Entity {

    public static final float PLAYER_WIDTH = 0.7f;
    public static final float PLAYER_HEIGHT = 1f;
    public static final float MAX_VELOCITY = 10f;
    
    public Player(){

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(10,24);
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
