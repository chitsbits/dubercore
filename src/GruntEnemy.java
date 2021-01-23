import java.util.ArrayList;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class GruntEnemy extends Enemy {

    Game game;
    Vector3 facedAngle;
    
    float clock;

    GruntEnemy(Game game, BodyDef bodyDef) {
        super(2f, 5f);
        this.width = 0.4f;
        this.height =  0.7f;
        this.game = game;
        this.enemyState = "wander";

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
        
        Fixture enemyFixture = body.createFixture(bodyFixtureDef);
        enemyFixture.setUserData(this);
        body.setFixedRotation(true);
    
        // sprite = GameClient.textureAtlas.createSprite("enemyspriteplaceholder");
        // sprite.setSize(this.width*2, this.height*2);
        // sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        entityShape.dispose();
    }

    @Override
    public void wander() {
        float bodyAngle = this.body.getAngle();
        bodyAngle = (bodyAngle * MathUtils.radiansToDegrees + 270) % 360;
        if (bodyAngle <= (90)){
            float opp = (float) Math.sin(bodyAngle);
            float adj = (float) Math.sin(bodyAngle);

            if (this.getVel().x < Enemy.MAX_VELOCITY){
                this.body.applyLinearImpulse(opp, 0, this.body.getPosition().x, this.body.getPosition().y, true);
            }

            if (this.getVel().y < Enemy.MAX_VELOCITY){
                this.body.applyLinearImpulse(0, adj, this.body.getPosition().x, this.body.getPosition().y, true);
            }

        }
        else if (bodyAngle <= (180)){

        }
        else if (bodyAngle <= (270)){

        }
        else if (bodyAngle <= (360)){
            
        }

    }

    @Override
    public void randRotate() {
        this.body.setLinearVelocity(0,0);
        float bodyAngle = this.body.getAngle();
        float randAngle = (float) (Math.random() * 360 * MathUtils.degreesToRadians);
        this.body.setTransform(this.body.getPosition(), bodyAngle + randAngle);
        System.out.println((this.body.getAngle() * MathUtils.radiansToDegrees + 270 )% 360);
    }


    public void developPath(Terrain goalTile){
        Terrain tempTile = goalTile;
        do {
            path.add(tempTile);
            tempTile = tempTile.parent;
            
        }while (tempTile.parent != null);

    }

    public void printTile(float x, float y){
        System.out.print(x*2);
        System.out.print(" ");
        System.out.print(y*2);
        System.out.print(" "); 
        System.out.println(this.game.tileMap.terrainArr[(int) x*2][(int) y*2]);
        
    }


    
}
