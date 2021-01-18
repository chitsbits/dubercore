import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Terrain {

    float worldX;
    float worldY;
    Body body;
    Sprite sprite;
    int marchingSquaresCase;

    public Terrain(int tileCase, float x, float y){
        this.marchingSquaresCase = tileCase;
        this.worldX = x;
        this.worldY = y;
    }
    
}
