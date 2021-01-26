/**
 * [Character.java]
 * Subclass of the entity class for creating specifically hp based entities
 * @author Viraj Bane, Sunny Jiao
 * @version 1.0 Build 1 January 25th 2021
 */
public abstract class Character extends Entity {
 
    protected float maxHp;
    private float hp;

    /**
     * Creates a character subclass of Entity with more specifics related to enemies and player entities
     */
    public Character(float maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    /**
     * gets hp of character entity
     * @return current hp
     */
    public float getHp() {
        return hp;
    }

    /**
     * heals hp of character entity
     * @param add amount of hp to add
     */
    public void heal(float add){
        this.hp += add;
        if (this.hp > 100){
            this.hp = 100;
        }
    }

    /**
     * damages the character entity by a set amount
     * @param damage amount to remove from hp
     */
    public void damage(float damage){
        this.hp -= damage;
    }
    
}
