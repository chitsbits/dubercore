/**
 * [Projectile.java]
 * abstract class for projectile entities in the game world
 * 
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Projectile extends Entity {

    protected World world;
    protected float damage;

    /**
     * returns damage of projectile
     * @return damage dealt by projectile
     */
    public float getDamage(){
        return damage;
    }

}