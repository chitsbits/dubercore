import java.util.ArrayList;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GruntEnemy extends Enemy {

    Game game;

    GruntEnemy(Game game, BodyDef bodyDef) {
        super(2f, 5f);
        this.width = 0.4f;
        this.height =  0.7f;
        this.game = game;

        this.bodyDef = bodyDef;
        this.bodyDef.type = BodyType.DynamicBody;
        //bodyDef.position.set(10, 24);

        entityShape = new PolygonShape();
        ((PolygonShape) entityShape).setAsBox(width, height);
        
        this.body = game.world.createBody(this.bodyDef);
        this.body.setGravityScale(0);

        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = entityShape;
        bodyFixtureDef.filter.categoryBits = Game.ENEMY;
        bodyFixtureDef.filter.maskBits = Game.TERRAIN | Game.PROJECTILE;
        bodyFixtureDef.friction = 1.0f;

        Fixture bodyFixture = body.createFixture(bodyFixtureDef);
        bodyFixture.setUserData(this);
        body.setFixedRotation(true);
        


        entityShape.dispose();
    }


    @Override
    public void pathfind(Vector2 goalPos) {
        TerrainComparator comparator = new TerrainComparator();
        PriorityQueue<Terrain> frontier = new PriorityQueue<Terrain>(11, comparator);
        PriorityQueue<Terrain> searched = new PriorityQueue<Terrain>(11, comparator);
        Vector2 startPos = new Vector2();
        Terrain currentTile;

        //adjusting world positions for tilemap scale 
        //1 tile = 0.5x0.5 game world units
        startPos.x = this.body.getPosition().x*2;
        startPos.y = this.body.getPosition().y*2;
        goalPos.x = goalPos.x*2;
        goalPos.x = goalPos.x*2;
        System.out.println(goalPos.x);
        System.out.println(goalPos.y);
        System.out.println(this.game.tileMap.terrainArr[(int) goalPos.x][(int) goalPos.y]);

        Terrain startTile = this.game.tileMap.terrainArr[ (int) startPos.x][ (int) startPos.y];
        
        startTile.setFGH(0, heuristicCost(startPos.x, startPos.y, goalPos.x, goalPos.y));
        frontier.add(startTile);

        do {
            currentTile = frontier.peek();
            int cTileX = (int) currentTile.worldX*2;
            int cTileY = (int) currentTile.worldY*2;

            searched.add(currentTile);
            frontier.remove(currentTile);
            
            ArrayList<Terrain> neighbours = new ArrayList<Terrain>();
            neighbours.add(this.game.tileMap.terrainArr[cTileX][cTileY + 1]);
            neighbours.add(this.game.tileMap.terrainArr[cTileX + 1][cTileY + 1]);
            neighbours.add(this.game.tileMap.terrainArr[cTileX + 1][cTileY]);
            neighbours.add(this.game.tileMap.terrainArr[cTileX + 1][cTileY - 1]);
            neighbours.add(this.game.tileMap.terrainArr[cTileX][cTileY - 1]);
            neighbours.add(this.game.tileMap.terrainArr[cTileX - 1][cTileY - 1]);
            neighbours.add(this.game.tileMap.terrainArr[cTileX - 1][cTileY]);
            neighbours.add(this.game.tileMap.terrainArr[cTileX - 1][cTileY + 1]);

            for (int i = 0; i < 8; i++){

                boolean walkable = false;
                if (neighbours.get(i) instanceof Air && !searched.contains(neighbours.get(i))){
                    walkable = true;

                }

                if (walkable && !frontier.contains(neighbours.get(i))){
                    neighbours.get(i).setParent(currentTile);
                    neighbours.get(i).setFGH(neighbours.get(i).parent.distanceSC+1, heuristicCost(neighbours.get(i).worldX*2, neighbours.get(i).worldY*2, goalPos.x, goalPos.y));
                    frontier.add(neighbours.get(i));
                    
                }
                
            }


        }while(!currentTile.equals(this.game.tileMap.terrainArr[(int) goalPos.x][(int) goalPos.y]) || frontier.isEmpty());

        Terrain tempTile = currentTile;
        do{
            path.add(0, tempTile);
            tempTile = tempTile.parent;
        }while(tempTile.parent != null);


    }
    
}
