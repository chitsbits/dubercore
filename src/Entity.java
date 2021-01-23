import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;

public abstract class Entity {

    Vector2 position;
    Vector2 velocity;
    transient Shape entityShape;
    transient BodyDef bodyDef;
    transient Body body;
    String spriteName;

    public Vector2 getPos() {
        return this.position;
    }

    public Vector2 getVel() {
        return this.velocity;
    }

    public void update(){
        this.position = body.getPosition();
        this.velocity = body.getLinearVelocity();

    }
}
