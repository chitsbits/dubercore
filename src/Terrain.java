import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Terrain {

    float worldX;
    float worldY;
    Body body1;
    Body body2;
    Sprite sprite;
    int marchingSquaresCase;
    int numEdges;

    //A* related variables, f =  g+h
    public float totalCost; //f = total cost of the nod
    public float distanceSC; //g = distance between starting node and current node
    public float heuristicCost; //h = estimated distance from the current node to the end node;
    public Terrain parent;


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

    public void setFGH(float g, float h){
        this.distanceSC = g;
        this.heuristicCost = h;
        this.totalCost = g + h;

    }

    public void setParent(Terrain parent){
        this.parent = parent;
    }
    
}
