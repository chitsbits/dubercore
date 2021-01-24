import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Grenade extends Entity {

    Player player;
    World world;

    public static final long COOLDOWN = 10000;

    Grenade(World world, Player player){
        this.player = player;
        this.world = world;

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(player.getPos());

        body = this.world.createBody(bodyDef);

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

    }
    
}
