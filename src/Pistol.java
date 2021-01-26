/**
 * [Pistol.java]
 * subclass of weapon for a certain type of firerate and damage per bullet
 * @author Viraj Bane
 * @version 1.0 Build 1 January 25th 2021
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Pistol extends Weapon {

    public static final int MAGAZINE_SIZE = 8;

    /**
     * Create a new pistol object
     */
    Pistol() {
        weaponType = "Pistol";
        damage = 4f;
        fireRate = 400;
        isFiring = false;
        ammo = 8;
        reloadTime = 2000;

        sprite = GameScreen.textureAtlas.createSprite("pistol");
        sprite.setSize(50, 30);
        sprite.setPosition(805, 40);
    }

    @Override
    /**
     * Fires the weapon
     * @param game the weapon is fired in
     * @param mousePos position of mouse
     * @param playerPos position of player
     */
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
    /**
     * reloads the weapon
     */
    public void reload() {
        ammo = MAGAZINE_SIZE;
        fireRate = 400;
    }

}
