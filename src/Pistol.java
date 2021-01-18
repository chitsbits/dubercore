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

    int c;

    Pistol(Player player) {
        super(player);
        weaponType = "Pistol";
        damage = 1f;
        c = 0;
        

        // TODO Auto-generated constructor stub
    }

    @Override
    public void fire(Game game, Vector3 mousePos) {
        // TODO Auto-generated method stub

        bulletDirection = new Vector2();
        bulletDirection.x = mousePos.x - player.getPos().x;
        bulletDirection.y = mousePos.y - player.getPos().y;
        bulletDirection.clamp(40f, 40f);
        Bullet bullet = new Bullet(game.world, damage, player);
        bullet.body.setLinearVelocity(bulletDirection);
        c = c+1;
        System.out.println("fired" + c);
        



    }

}
