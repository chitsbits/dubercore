import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Projectile extends Entity {

    Vector2 velocity;
    World world;
    private float damage;

    public float getDamage(){
        return damage;
    }

    public void setDamage(float damage){
        this.damage = damage;
    }

}