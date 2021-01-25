import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class ProjectileSpit extends Projectile{

    ProjectileSpit(World world, float damage, Vector2 pos){
        this.world = world;
        this.setDamage(damage);

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
