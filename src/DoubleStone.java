import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Subclass to deal with cases 5 & 10 in the marching squares algorithm
 */
public class DoubleStone extends Stone {

    Body body2;
    
    public DoubleStone(float x, float y, Body body1, Body body2){
        super(x,y);
        this.body = body1;
        this.body2 = body2;
    }
}
