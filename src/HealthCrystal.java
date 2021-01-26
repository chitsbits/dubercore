/**
 * [HealthCrystal.java]
 * Terrain tile that heals the player when mined
 * @author Sunny Jiao
 * @version 1.0
 */

import com.badlogic.gdx.physics.box2d.Body;

public class HealthCrystal extends Terrain {

    /**
     * Create a subsurface tile
     * @param tileCase Marching squares case
     * @param x world X coordinates
     * @param y world Y coordinates
     */
    public HealthCrystal(int tileCase, float x, float y){
        super(tileCase, x,y);
        this.sprite = GameScreen.textureAtlas.createSprite("crystal" + (tileCase));
    }

    /**
     * Create a surface tile
     * @param tileCase Marching squares case
     * @param x world X coordinates
     * @param y world Y coordinates
     * @param body1 Box2D body
     */
    public HealthCrystal(int tileCase, float x, float y, Body body1){
        super(tileCase, x, y);
        this.body1 = body1;
        this.sprite = GameScreen.textureAtlas.createSprite("crystal" + (tileCase));
    }
    
}
