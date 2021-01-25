import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends Character {

    public static final long ATTENTION_SPAN = 2000;

    float width;
    float height;
    float damage;
    boolean isColliding;
    String enemyState;
    ArrayList<Terrain> path;

    long pursuitTimer;

    Enemy(float damage, float hp){
        super(hp);
        this.damage = damage;
        this.maxHp = hp;
        this.path = new ArrayList<Terrain>();
        this.isColliding = false;
    }

    public abstract void move();

    public abstract void randRotate();

    public abstract void pursuit(Vector2 playerPos);

    public float heuristic(Vector2 enemyPos, Vector2 playerPos){
        double dx = enemyPos.x - playerPos.x;
        double dy = enemyPos.y - playerPos.y;
        return (float) Math.sqrt((dx*dx)+(dy*dy));
    }
}
