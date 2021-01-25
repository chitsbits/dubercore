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

    public abstract void fire(DuberCore game, Vector3 mousePos, Vector2 playerPos);

    public abstract void reload();

}