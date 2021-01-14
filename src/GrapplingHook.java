import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GrapplingHook extends Entity {
    
    Player player;  // Reference to host player

    public GrapplingHook(World world, Player player){
        this.player = player;

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(player.getPos());

        body = world.createBody(bodyDef);
        body.setGravityScale(0);
        
        entityShape = new CircleShape();
        ((CircleShape) entityShape).setRadius(0.2f);
        
        FixtureDef grappleFixtureDef = new FixtureDef();
        grappleFixtureDef.shape = entityShape;
        grappleFixtureDef.filter.categoryBits = Game.GRAPPLE;
        grappleFixtureDef.filter.maskBits = Game.TERRAIN;
        grappleFixtureDef.friction = 1f;

        Fixture grappleFixture = body.createFixture(grappleFixtureDef);
        grappleFixture.setUserData(this);
        entityShape.dispose();
    }
}
