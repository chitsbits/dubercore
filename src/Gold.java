import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;

public class Gold extends Terrain {

    public Gold(int tileCase, float x, float y){
        super(tileCase, x,y);
        spriteName = "gold" + tileCase;
    }

    public Gold(int tileCase, float x, float y, Body body1){
        super(tileCase, x,y);
        this.body1 = body1;
        spriteName = "gold" + tileCase;
    }
    
}
