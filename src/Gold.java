
/**
 * [Gold.java]
 * Terrain ore that gives score when mined
 * @author Sunny Jiao
 * @version 1.0
 */

import com.badlogic.gdx.physics.box2d.Body;

public class Gold extends Terrain {

    /**
     * Create a subsurface tile
     * @param tileCase Marching squares case
     * @param x world X coordinates
     * @param y world Y coordinates
     */
    public Gold(int tileCase, float x, float y){
        super(tileCase, x, y);
        this.sprite = GameScreen.textureAtlas.createSprite("gold" + (tileCase));
    }

    /**
     * Create a surface tile
     * @param tileCase Marching squares case
     * @param x world x coordinates
     * @param y world y coordinates
     * @param body1 Box2d body
     */
    public Gold(int tileCase, float x, float y, Body body1){
        super(tileCase, x, y);
        this.body1 = body1;
        this.sprite = GameScreen.textureAtlas.createSprite("gold" + (tileCase));
    }
}
