import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class EnemyAiRayCastCallback implements RayCastCallback {
    
    float minFloat;
    String fixtureType;
    

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 enemyPos, Vector2 playerPos, float fraction) {
        
        if (fixture.getUserData().equals("edgeFixture")){
            System.out.println("collidedT");
            fixtureType = "terrain";
            return 0;
        }

        if (fixture.getUserData() instanceof Player){
            System.out.println("collidedP");
            fixtureType = "player";
            return 0;
        }
        else {
            if (fraction < minFloat){
                minFloat = fraction;
                fixtureType = "other";
            }
            return -1;
        }
    }
    
}
