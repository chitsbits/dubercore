import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Explosion extends Entity {

    World world;
    Vector2 position;
    float damage;

    Explosion(World world, Vector2 position){
        this.world = world;
        this.damage = 10f;
        this.position = position;
    }

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

    public float getDamage(){
        return damage;
    }
    
}
