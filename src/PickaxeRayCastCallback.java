import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class PickaxeRayCastCallback implements RayCastCallback {

    Vector2 collisionPoint;

    /**
     * Defines the raycast report for a player using a pickaxe.
     * If the collided fixture is terrain, then the collision point
     * class variable will be assigned.
     * @return 0 if the fixture is an edge, -1 to ignore and continue
     */
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 collisionPoint, Vector2 normal, float fraction) {

        if(fixture.getUserData() != null && fixture.getUserData().equals("edgeFixture")){
            this.collisionPoint = collisionPoint;
            //System.out.println("mining");
            return 0;
        }
        else {
            return -1;
        }
    }
    
}
