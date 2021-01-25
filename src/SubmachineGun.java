import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SubmachineGun extends Weapon {

    SubmachineGun() {
        weaponType = "SubmachineGun";
        damage = 1f;
        fireRate = 150;
        isFiring = false;
        magazineSize = 25;

        sprite = GameScreen.textureAtlas.createSprite("pistol");
        sprite.setSize(60, 40);
        sprite.setPosition(1000, 30);
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
        magazineSize -= 1;
        if (magazineSize <= 0){
            fireRate = 3500;
        }
    }

    @Override
    public void reload() {
        magazineSize = 25;
        fireRate = 150;
    }
}
