/**
 * [Weapon.java]
 * abstract class map for different weapons
 * @author Viraj Bane
 * @version 1.0 build 1 January 25th 2021
 */
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Weapon {

    public Sprite sprite;
    public String weaponType;
    public float damage;
    public Vector2 bulletDirection;
    public boolean isFiring;
    public long fireRate;
    public int ammo;
    public long reloadTime;

    /**
     * Fires the weapon
     * @param game the weapon is fired in
     * @param mousePos position of mouse
     * @param playerPos position of player
     */
    public abstract void fire(DuberCore game, Vector3 mousePos, Vector2 playerPos);

    /**
     * reloads the weapon
     */
    public abstract void reload();

}