
/**
 * [Stone.java]
 * Terrain representing stone
 * @author Sunny Jiao
 * @version 1.0
 */

import com.badlogic.gdx.physics.box2d.Body;

public class Stone extends Terrain {

    /**
     * Create a subsurface tile
     * @param tileCase the marching squares case
     * @param x worldX coordinates
     * @param y worldY coordinates
     */
    public Stone(int tileCase, float x, float y){
        super(tileCase, x,y);
        this.sprite = GameScreen.textureAtlas.createSprite("stone" + (tileCase));
    }

    /**
     * create a surface tile
     * @param tileCase the marching squares case
     * @param x worldX coordinates
     * @param y worldY coordinates
     * @param body1 Box2D abody
     */
    public Stone(int tileCase, float x, float y, Body body1){
        super(tileCase, x,y);
        this.body1 = body1;
        this.sprite = GameScreen.textureAtlas.createSprite("stone" + (tileCase));
    }
    
}
