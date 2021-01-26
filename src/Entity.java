/**
 * [Entity.java]
 * abstract class for entities in the game world
 * @author Viraj Bane
 * @author Sunny Jiao
 * @version Build 1 January 25th 2021
 */

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;

public abstract class Entity {

    Shape entityShape;
    Body body;
    BodyDef bodyDef;
    Sprite sprite;

    /**
     * Gets the entity's position
     * @return entity's position
     */
    public Vector2 getPos() {
        return body.getPosition();
    }

    /**
     * Gets the entity's velocity
     * @return entity's velocity
     */
    public Vector2 getVel() {
        return body.getLinearVelocity();
    }
}
