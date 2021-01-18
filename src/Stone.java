import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Stone extends Terrain {

    public Stone(int tileCase, float x, float y){
        super(tileCase, x,y);
        this.sprite = new Sprite(GameClient.stoneTextures[tileCase-1]);
    }

    public Stone(int tileCase, float x, float y, Body body){
        super(tileCase, x,y);
        this.body = body;
        this.sprite = new Sprite(GameClient.stoneTextures[tileCase-1]);
    }
    
}
