
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import java.io.Serializable;

public abstract class Terrain {
    String spriteName;
    float worldX;
    float worldY;
    transient Body body1;
    transient Body body2;
    transient int numEdges;
    int marchingSquaresCase;

    public Terrain(){

    }

    public Terrain(int tileCase, float x, float y){
        this.marchingSquaresCase = tileCase;
        this.worldX = x;
        this.worldY = y;

        if(marchingSquaresCase == 5 || marchingSquaresCase == 10){
            numEdges = 2;
        }
        else if (marchingSquaresCase > 0 && marchingSquaresCase < 15){
            numEdges = 1;
        }
        else {
            numEdges = 0;
        }

    }
    
}
