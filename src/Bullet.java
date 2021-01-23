import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Bullet extends Projectile {

    Bullet(World world, float damage, Vector2 startPoint){
        this.world = world;
        this.setDamage(damage);

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(startPoint);

        body = this.world.createBody(bodyDef);
        body.setGravityScale(0);

        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(0.1f);

        FixtureDef bulletFixtureDef = new FixtureDef();
        bulletFixtureDef.shape = entityShape;
        bulletFixtureDef.filter.categoryBits = Game.PROJECTILE;
        bulletFixtureDef.filter.maskBits = Game.TERRAIN | Game.ENEMY;
        bulletFixtureDef.friction = 1f;

        Fixture bulletFixture = body.createFixture(bulletFixtureDef);
        bulletFixture.setUserData(this);
        entityShape.dispose();


    }

}
