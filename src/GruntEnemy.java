import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GruntEnemy extends Enemy {

    World world;
    Vector3 facedAngle;
    
    float clock;

    GruntEnemy(World world, BodyDef bodyDef) {
        super(2f, 5f);
        this.width = 0.4f;
        this.height =  0.7f;
        this.world = world;
        this.enemyState = "wander";

        this.bodyDef = bodyDef;
        this.bodyDef.type = BodyType.DynamicBody;

        entityShape = new PolygonShape();
        ((PolygonShape) entityShape).setAsBox(width, height);
        
        this.body = world.createBody(this.bodyDef);
        this.body.setGravityScale(0);

        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = entityShape;
        bodyFixtureDef.filter.categoryBits = DuberCore.ENEMY;
        bodyFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.PROJECTILE;
        bodyFixtureDef.friction = 1.0f;
        
        sprite = GameScreen.textureAtlas.createSprite("enemyspriteplaceholder");
        sprite.setSize(this.width*2, this.height*2);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        Fixture enemyFixture = body.createFixture(bodyFixtureDef);
        enemyFixture.setDensity(500);
        body.setFixedRotation(true);
        enemyFixture.setUserData(this);

        FixtureDef collisionFixtureDef = new FixtureDef();
        collisionFixtureDef.shape = entityShape;
        collisionFixtureDef.filter.categoryBits = DuberCore.SENSOR;
        collisionFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.PLAYER;
        collisionFixtureDef.isSensor = true;
        Fixture collisionFixture = body.createFixture(collisionFixtureDef);
        collisionFixture.setUserData(this);
        entityShape.dispose();
    }

    @Override
    public void move() {
        float bodyAngle = this.body.getAngle();
        bodyAngle = (bodyAngle * MathUtils.radiansToDegrees + 270) % 360;

        bodyAngle = bodyAngle * MathUtils.degreesToRadians;
        float opp = (float) Math.sin(bodyAngle);
        float adj = (float) Math.cos(bodyAngle);

        this.body.setLinearVelocity(adj * 4, opp * 4);

    }

    @Override
    public void randRotate() {
        this.body.setLinearVelocity(0,0);
        float bodyAngle = this.body.getAngle();
        float randAngle = (float) (Math.random() * 360 * MathUtils.degreesToRadians);
        while (bodyAngle * MathUtils.radiansToDegrees % 90 == randAngle * MathUtils.radiansToDegrees % 90){
            randAngle = (float) (Math.random() * 360 * MathUtils.degreesToRadians);
        }
        this.sprite.setRotation((bodyAngle + randAngle) * MathUtils.radiansToDegrees + 180);
        this.body.setTransform(this.body.getPosition(), bodyAngle + randAngle);
    }

    @Override
    public void pursuit(Vector2 playerPos) {
        float angleToPlayer = (float) Math.atan2(playerPos.y - this.body.getPosition().y, playerPos.x - this.body.getPosition().x);

        if (angleToPlayer < 0){
            angleToPlayer += 360 * MathUtils.degreesToRadians;
        }


        this.sprite.setRotation((angleToPlayer) * MathUtils.radiansToDegrees + 270);
        this.body.setTransform(this.body.getPosition(), angleToPlayer + 90 * MathUtils.degreesToRadians);
        move();
        //float yInt  = (playerPos.y/(slope * playerPos.x));

    }
}
