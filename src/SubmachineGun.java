import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SubmachineGun extends Weapon {

    public static final int MAGAZINE_SIZE = 25;

    SubmachineGun() {
        weaponType = "SubmachineGun";
        damage = 1f;
        fireRate = 150;
        isFiring = false;
        ammo = 25;
        reloadTime = 3500;

        sprite = GameScreen.textureAtlas.createSprite("smg");
        sprite.setSize(70, 30);
        sprite.setPosition(895, 40);
    }

    @Override
    public void fire(DuberCore game, Vector3 mousePos, Vector2 playerPos) {
        bulletDirection = new Vector2();
        bulletDirection.x = mousePos.x - playerPos.x;
        bulletDirection.y = mousePos.y - playerPos.y;
        bulletDirection.clamp(40f, 40f);
        Bullet bullet = new Bullet(game.world, damage, playerPos);
        game.entityList.add(bullet);
        bullet.body.setLinearVelocity(bulletDirection);
        ammo -= 1;
        if (ammo <= 0){
            fireRate = reloadTime;
        }
    }

    @Override
    public void reload() {
        ammo = MAGAZINE_SIZE;
        fireRate = 150;
    }
}
