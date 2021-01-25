import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class EnemyAiRayCastCallback implements RayCastCallback {
    
    float minFloat;
    String fixtureType;
    Boolean los = false;
    

    @Override
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
