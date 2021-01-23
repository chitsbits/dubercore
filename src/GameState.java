import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.physics.box2d.World;
import java.io.Serializable;

public class GameState implements Serializable {

    ArrayList<Entity> entityList;
    HashMap<String, Player> playerMap;
    int score;
    Terrain[][] terrainArr;
}
