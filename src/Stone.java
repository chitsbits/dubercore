import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Stone extends Terrain {

    public Stone(int tileCase, float x, float y){
        super(tileCase, x,y);
        spriteName = "stone" + tileCase;
    }

    public Stone(int tileCase, float x, float y, Body body1){
        super(tileCase, x,y);
        this.body1 = body1;
        spriteName = "stone" + tileCase;
    }
    
}
