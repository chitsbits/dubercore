import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Pistol extends Weapon {

    Pistol(Player player) {
        super(player);
        weaponType = "Pistol";
        damage = 1f;
        fireRate = 500;
        

        // TODO Auto-generated constructor stub
    }

    @Override
    public void fire(DuberCore game, Vector3 mousePos) {
        // TODO Auto-generated method stub

        bulletDirection = new Vector2();
        bulletDirection.x = mousePos.x - player.getPos().x;
        bulletDirection.y = mousePos.y - player.getPos().y;
        bulletDirection.clamp(40f, 40f);
        Bullet bullet = new Bullet(game.world, damage, player);
        bullet.body.setLinearVelocity(bulletDirection);
        



    }

}
