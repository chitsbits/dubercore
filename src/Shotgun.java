import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Shotgun extends Weapon {

    Shotgun() {
        weaponType = "Shotgun";
        damage = 2f;
        fireRate = 1200;
        isFiring = false;
        magazineSize = 4;

        sprite = GameScreen.textureAtlas.createSprite("pistol");
        sprite.setSize(60, 40);
        sprite.setPosition(1100, 30);
    }

    @Override
    public void fire(DuberCore game, Vector3 mousePos, Vector2 playerPos) {

        for (int p = 0; p < 9; p++){
            int spreadX = (int) (Math.random() * (3));
            int spreadY = (int) (Math.random() * (3));
            bulletDirection = new Vector2();
            bulletDirection.x = mousePos.x - playerPos.x + spreadX;
            bulletDirection.y = mousePos.y - playerPos.y + spreadY;
            float randomClamp = (float) ((Math.random() * (60 - 20)) + 20);
            bulletDirection.clamp(randomClamp, randomClamp);
            Bullet bullet = new Bullet(game.world, damage, playerPos);
            game.entityList.add(bullet);
            bullet.body.setLinearVelocity(bulletDirection);
        }
        magazineSize -= 1;
        if (magazineSize <= 0){
            fireRate = 3000;
        }
    }

    @Override
    public void reload() {
        magazineSize = 4;
        fireRate = 1200;
    }
}
