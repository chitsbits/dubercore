/**
 * [Grenade.java]
 * subclass of entity for creating grenade bodies in the game world
 * @author Viraj Bane
 * @version 1.0 Build 1 January 25th 2021
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Grenade extends Entity {

    public static final long COOLDOWN = 10000;

    /**
     * Creates a grenade body in the game world
     * @param world world for the body to be made in
     * @param pos position for the grenade to be initially spawned at in the world
     */
    Grenade(World world, Vector2 pos){

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(pos);

        body = world.createBody(bodyDef);

        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(0.2f);

        FixtureDef grenadeFixtureDef = new FixtureDef();
        grenadeFixtureDef.shape = entityShape;
        grenadeFixtureDef.filter.categoryBits = DuberCore.PROJECTILE;
        grenadeFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.ENEMY;
        grenadeFixtureDef.friction = 1f;

        Fixture grenadeFixture = body.createFixture(grenadeFixtureDef);
        grenadeFixture.setUserData(this);
        entityShape.dispose();

        this.sprite = GameScreen.textureAtlas.createSprite("grenade");
        sprite.setSize(0.5f, 0.5f);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

    }
    
}
