import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Shotgun extends Weapon {

    Shotgun() {
        weaponType = "Shotgun";
        damage = 3f;
        fireRate = 1200;
        isFiring = false;
        magazineSize = 4;
        reloadTime = 3000;

        sprite = GameScreen.textureAtlas.createSprite("shotgun");
        sprite.setSize(80, 13);
        sprite.setPosition(990, 47);
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
            fireRate = reloadTime;
        }
    }

    @Override
    public void reload() {
        magazineSize = 4;
        fireRate = 1200;
    }
}
