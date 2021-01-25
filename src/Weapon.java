import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Weapon {

    Sprite sprite;
    String weaponType;
    float damage;
    Vector2 bulletDirection;
    public long fireRate;

    public abstract void fire(DuberCore game, Vector3 mousePos, Vector2 playerPos);
    
}