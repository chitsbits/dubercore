/**
 * [Explosion.Java]
 * entity extending entity for blast zones of grenades
 * @author Viraj Bane
 * @version 1.0 Build 1 January 25th
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Explosion extends Entity {

    private World world;
    private Vector2 position;
    private float damage;

    /**
     * constructs an explosion object
     * @param world world to create the explosion in
     * @param position position for the explosion to start at
     */
    Explosion(World world, Vector2 position){
        this.world = world;
        this.damage = 10f;
        this.position = position;
    }

    /**
     * creates an explosion body in the game world
     */
    public void explode(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(position);

        body = this.world.createBody(bodyDef);
        body.setLinearVelocity(0,0);
        body.setGravityScale(0);

        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(3.5f);

        FixtureDef explosionFixtureDef = new FixtureDef();
        explosionFixtureDef.shape = entityShape;
        explosionFixtureDef.filter.categoryBits = DuberCore.PROJECTILE;
        explosionFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.ENEMY;

        Fixture explosionFixture = body.createFixture(explosionFixtureDef);
        explosionFixture.setUserData(this);
        entityShape.dispose();

        this.sprite = GameScreen.textureAtlas.createSprite("explosion");
        sprite.setSize(6f, 6f);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
    }

    /**
     * retuns damage that the explosion would deal
     * @return damage dealt to all enemies in blast radius
     */
    public float getDamage(){
        return damage;
    }
    
}
