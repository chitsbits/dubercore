import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Pistol extends Weapon {

    Pistol() {
        weaponType = "Pistol";
        damage = 1f;
        fireRate = 500;
    }

    @Override
    public void fire(Game game, Vector3 mousePos, Vector2 startPoint) {
        bulletDirection = new Vector2();
        bulletDirection.x = mousePos.x - startPoint.x;
        bulletDirection.y = mousePos.y - startPoint.y;
        bulletDirection.clamp(40f, 40f);
        Bullet bullet = new Bullet(game.world, damage, startPoint);
        bullet.body.setLinearVelocity(bulletDirection);
        



    }

}
