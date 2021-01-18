import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Stone extends Terrain {

    public Stone(float x, float y){
        super(x,y);
    }

    public Stone(float x, float y, Body body){
        super(x,y);
        this.body = body;
    }
    
}
