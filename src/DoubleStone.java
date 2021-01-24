import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Subclass to deal with cases 5 & 10 in the marching squares algorithm
 */
public class DoubleStone extends Stone {
    
    public DoubleStone(int tileCase, float x, float y, Body body1, Body body2){
        super(tileCase, x,y);
        this.body1 = body1;
        this.body2 = body2;
        //this.sprite = new Sprite(GameClient.stoneTextures[tileCase-1]);
        this.sprite = GameScreen.textureAtlas.createSprite("stone" + (tileCase));
    }
}
