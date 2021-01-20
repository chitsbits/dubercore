import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Weapon {

    Sprite sprite;
    String weaponType;
    Player player;
    float damage;
    Vector2 bulletDirection;
    public long fireRate;

    Weapon(Player player){
        this.player = player;
    }

    public abstract void fire(Game game, Vector3 mousePos);
    
}