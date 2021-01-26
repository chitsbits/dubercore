/**
 * [EnemyAiRayCastCallback.java]
 * implements raycast callback, used to determine los from enemies to player
 * @author Viraj Bane
 * @version 1.0 Build 1 January 25th
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class EnemyAiRayCastCallback implements RayCastCallback {
    
    float minFloat;
    String fixtureType;
    Boolean los = false;
    

    @Override
    /**
     * Called for each fixture found in the query. 
     * You control how the ray cast proceeds by returning a float: 
     * return -1: ignore this fixture and continue 
     * return 0: terminate the ray cast return 
     * fraction: clip the ray to this point 
     * return 1: don't clip the ray and continue. 
     * The Vector2 instances passed to the callback will be reused for future calls
     * 
     * @param fixture the fixture hit by the ray
     * @param point the point of initial intersection
     * @param normal  the normal vector at the point of intersection
     * @return -1 to filter, 0 to terminate, fraction to clip the ray for closest hit, 1 to continue
     */
    public float reportRayFixture(Fixture fixture, Vector2 enemyPos, Vector2 playerPos, float fraction) {

        if (fixture.getUserData().equals("edgeFixture")){
            fixtureType = "terrain";
            los = false;
            return 0;
        }
            
        else if (fixture.getUserData() instanceof Player){
            fixtureType = "player";
            los = true;
            return 0;
        }

        else {
            return -1;
        }
    }
    
}
