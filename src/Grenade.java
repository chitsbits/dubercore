import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Grenade extends Entity {

    transient World world;

    public static final long COOLDOWN = 10000;

    Grenade(World world, Vector2 startPoint){
        this.world = world;

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(startPoint);

        body = this.world.createBody(bodyDef);

        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(0.2f);

        FixtureDef grenadeFixtureDef = new FixtureDef();
        grenadeFixtureDef.shape = entityShape;
        grenadeFixtureDef.filter.categoryBits = Game.PROJECTILE;
        grenadeFixtureDef.filter.maskBits = Game.TERRAIN | Game.ENEMY;
        grenadeFixtureDef.friction = 1f;

        Fixture grenadeFixture = body.createFixture(grenadeFixtureDef);
        grenadeFixture.setUserData(this);
        entityShape.dispose();

    }
    
}
