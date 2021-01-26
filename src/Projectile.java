/**
 * [Projectile.java]
 * abstract class for projectile entities in the game world
 * @author Viraj Bane
 * @version 1.0
 */
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