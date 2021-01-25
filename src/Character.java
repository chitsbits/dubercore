public abstract class Character extends Entity {
 
    protected float maxHp;
    private float hp;

    public Character(float maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public float getHp() {
        return hp;
    }

    public void heal(float add){
        this.hp += add;
        if (this.hp > 100){
            this.hp = 100;
        }
    }

    public void damage(float damage){
        this.hp -= damage;
    }
    
}
