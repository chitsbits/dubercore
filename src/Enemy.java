import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends Entity {

    public static final float MAX_HP = 5f;
    public static final long ATTENTION_SPAN = 2000;

    private float hp;
    float width;
    float height;
    float damage;
    String enemyState;
    ArrayList<Terrain> path;

    long pursuitTimer;

    Enemy(float damage, float hp){
        this.damage = damage;
        this.hp = hp;
        this.path = new ArrayList<Terrain>();
    }

    public abstract void move();

    public abstract void randRotate();

    public abstract void pursuit(Vector2 playerPos);

    public float heuristic(Vector2 enemyPos, Vector2 playerPos){
        double dx = enemyPos.x - playerPos.x;
        double dy = enemyPos.y - playerPos.y;
        return (float) Math.sqrt((dx*dx)+(dy*dy));
    }

    public float getHp(){
        return hp;
    }

    public void setHp(float hp){
        this.hp = hp;
    }


    public boolean checkCooldown(long lastUse, long cooldown){
        long time = System.currentTimeMillis();
        if (time > lastUse + cooldown){
            return true;
        }
        return false;
    }
    
}
