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
            //System.out.println(los);
            return 0;
        }
            
        else if (fixture.getUserData().equals("player")){
            fixtureType = "player";
            los = true;
            //System.out.println(los);
            return 0;
        }

        else {
            //System.out.println("other");
            return -1;
        }
    }
    
}
