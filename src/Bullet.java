import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends Projectile {

    Bullet(World world, float damage, Vector2 pos){
        this.world = world;
        this.setDamage(damage);

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
        sprite.setSize(0.3f, 0.3f);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
    }

}
