import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends Entity {

    private float hp;
    public static final float MAX_VELOCITY = 5f;
    float width;
    float height;
    float damage;
    String enemyState;
    ArrayList<Terrain> path = new ArrayList<Terrain>();

    Enemy(float damage, float hp){
        this.damage = damage;
        this.hp = hp;

    }

    public abstract void wander();

    public abstract void randRotate();

    public abstract void pursuit();

    public float heuristic(Vector2 enemyPos, Vector2 playerPos){
        double dx = enemyPos.x - playerPos.x;
        double dy = enemyPos.x - playerPos.y;
        return (float) Math.sqrt((dx*dx)+(dy*dy));
        
    }

    public float getHp(){
        return hp;
    }

    public void setHp(float hp){
        this.hp = hp;
    }
    
    
}
