import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Bullet extends Projectile {

    Player player;

    Bullet(World world, float damage, Player player){
        this.player = player;
        this.world = world;
        this.setDamage(damage);

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(player.getPos());

        body = this.world.createBody(bodyDef);
        body.setGravityScale(0);


        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(0.1f);

        FixtureDef bulletFixtureDef = new FixtureDef();
        bulletFixtureDef.shape = entityShape;
        bulletFixtureDef.filter.categoryBits = Game.PROJECTILE;
        bulletFixtureDef.filter.maskBits = Game.TERRAIN | Game.ENEMY;
        bulletFixtureDef.friction = 1f;
        bulletFixtureDef.density  = 1f;

        Fixture bulletFixture = body.createFixture(bulletFixtureDef);

        bulletFixture.setUserData(this);
        entityShape.dispose();


    }

}
