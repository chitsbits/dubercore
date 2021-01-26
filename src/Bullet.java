/**
 * [Bullet.Java]
 * subclass of projectile for creating bullet object bodies in the game world
 * @author Viraj Bane
 * @version 1.0 Build 1 January 25th 2021
 */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends Projectile {

    /**
     * Creates a bullet body in the game world
     * @param world world for the bullet to be made in
     * @param damage amount of damage the bullet deals if it comes into contact with an enemy
     * @param pos position for the bullet to be initially spawned at
     */
    Bullet(World world, float damage, Vector2 pos){
        this.world = world;
        this.damage = damage;

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(pos);

        body = this.world.createBody(bodyDef);
        body.setGravityScale(0);

        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(0.1f);

        FixtureDef bulletFixtureDef = new FixtureDef();
        bulletFixtureDef.shape = entityShape;
        bulletFixtureDef.filter.categoryBits = DuberCore.PROJECTILE;
        bulletFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.ENEMY;
        bulletFixtureDef.friction = 1f;
        bulletFixtureDef.density  = 1f;

        Fixture bulletFixture = body.createFixture(bulletFixtureDef);

        bulletFixture.setUserData(this);
        entityShape.dispose();

        this.sprite = GameScreen.textureAtlas.createSprite("bullet");
        sprite.setSize(0.2f, 0.2f);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
    }

}
