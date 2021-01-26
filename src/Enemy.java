/**
 * [Enemy.java]
 * subclass of character for specically enemy related entities
 * @author Viraj Bane
 * @version 2.0 Build 2 January 25th 2021
 */

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends Character {

    public static final long ATTENTION_SPAN = 2000;

    protected float width;
    protected float height;
    protected float damage;
    private boolean isColliding;
    protected String enemyState;
    public long pursuitTimer;

    Enemy(float damage, float hp){
        super(hp);
        this.damage = damage;
        this.maxHp = hp;
        this.enemyState = "wander";
        this.isColliding = false;
    }

    /**
     * moves the enemy entity in the game world based on the angle of the body
     */
    public abstract void move();

    /**
     * randomly rotates the entity in the game world
     */
    public abstract void randRotate();

    /**
     * chases and faces player in the game world
     */
    public abstract void pursuit(Vector2 playerPos);

    /**
     * determines the distance from player to the enemy
     * @param enemyPos enemy position in the game world
     * @param playerPos player position in the game world
     * @return distance between the two
     */
    public float heuristic(Vector2 enemyPos, Vector2 playerPos){
        double dx = enemyPos.x - playerPos.x;
        double dy = enemyPos.y - playerPos.y;
        return (float) Math.sqrt((dx*dx)+(dy*dy));
    }

    /**
     * sets the colliding with player flag
     * @param flag true false flag
     */
    public void setColliding(boolean flag){
        this.isColliding = flag;
    }

    /**
     * returns if the enemy is colliding with the player
     * @return collision with player flag
     */
    public boolean getColliding() {
        return this.isColliding;
    }

    /**
     * sets the enemy state to param String
     * @param state enemy state to be set to
     */
    public void setEnemyState(String state){
        this.enemyState = state;
    }

    /**
     * returns the current enemy state
     * @return current enemy state
     */
    public String getEnemyState(){
        return this.enemyState;
    }


}
