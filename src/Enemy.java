public abstract class Enemy extends Entity {

    private float hp;
    float width;
    float height;
    float damage;
    Game game;

    Enemy(Game game, float damage, float hp){
        this.game = game;
        this.damage = damage;
        this.hp = hp;

    }

    public abstract void pathfind();

    public float getHp(){
        return hp;
    }

    public void setHp(float hp){
        this.hp = hp;
    }
    
    
}
