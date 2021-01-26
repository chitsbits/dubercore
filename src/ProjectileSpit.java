/**
 * [ProjectileSpit.java]
 * subclass of projectile for specifically ranged enemies
 * @author Viraj Bane
 * @version 1.0
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class ProjectileSpit extends Projectile{

    /**
     * Creates a projectile spit body in the game world
     * @param world world for the spit to be made in
     * @param damage amount of damage the spit deals if comes into contact with a player
     * @param pos positon for spit to be initially spawned at
     */
    ProjectileSpit(World world, float damage, Vector2 pos){
        this.world = world;
        this.damage = damage;

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(pos);

        body = this.world.createBody(bodyDef);
        body.setGravityScale(0);

        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(0.1f);

        FixtureDef spitFixtureDef = new FixtureDef();
        spitFixtureDef.shape = entityShape;
        spitFixtureDef.filter.categoryBits = DuberCore.PROJECTILE;
        spitFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.PLAYER;
        spitFixtureDef.friction = 1f;
        spitFixtureDef.density  = 1f;

        Fixture spitFixture = body.createFixture(spitFixtureDef);

        spitFixture.setUserData(this);
        entityShape.dispose();

        this.sprite = GameScreen.textureAtlas.createSprite("projectilespit");
        sprite.setSize(0.3f, 0.3f);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

    }
    
}
