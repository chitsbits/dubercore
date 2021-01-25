import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class RangedEnemy extends Enemy {

    public static final long ATTACK_SPEED = 1000;

    DuberCore game;
    Vector2 playerPos;
    long lastAttack;

    RangedEnemy(DuberCore game, BodyDef bodyDef) {
        super(4f, 6f);
        this.width = 0.4f;
        this.height = 0.7f;
        this.enemyState = "wander";
        this.game = game;
        this.playerPos = new Vector2(0,0);

        this.bodyDef = bodyDef;
        this.bodyDef.type = BodyType.DynamicBody;

        entityShape = new PolygonShape();
        ((PolygonShape) entityShape).setAsBox(width, height);
        
        this.body = game.world.createBody(this.bodyDef);
        this.body.setGravityScale(0);

        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = entityShape;
        bodyFixtureDef.filter.categoryBits = DuberCore.ENEMY;
        bodyFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.PROJECTILE | DuberCore.ENEMY | DuberCore.PLAYER ;
        bodyFixtureDef.friction = 1.0f;

        sprite = GameScreen.textureAtlas.createSprite("rangedspriteplaceholder");
        sprite.setSize(this.width*2, this.height*2);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        Fixture enemyFixture = body.createFixture(bodyFixtureDef);
        enemyFixture.setDensity(500);
        body.setFixedRotation(true);
        enemyFixture.setUserData(this);

        FixtureDef collisionFixtureDef = new FixtureDef();
        collisionFixtureDef.shape = entityShape;
        collisionFixtureDef.filter.categoryBits = DuberCore.SENSOR | DuberCore.ENEMY;
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

        if (heuristic(this.body.getPosition(), this.playerPos) > 12 && this.enemyState.equals("pursuit")) {
            this.body.setLinearVelocity(adj * 2, opp * 2);
        }
        else if (heuristic(this.body.getPosition(), this.playerPos) <8 && this.enemyState.equals("pursuit")) {
            this.body.setLinearVelocity(-adj * 2, -opp * 2);
        }
        else if (this.enemyState.equals("pursuit")) {
            this.body.setLinearVelocity(0, 0);
            if(DuberCore.checkCooldown(lastAttack, RangedEnemy.ATTACK_SPEED)) {
                fire();
                lastAttack = System.currentTimeMillis();
            }
            
        }
        else {
            this.body.setLinearVelocity(adj * 2, opp * 2);
        }

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
        this.playerPos = playerPos;
        move();

    }

    public void fire() {
        Vector2 spitDirection = new Vector2();
        spitDirection.x = playerPos.x - this.body.getPosition().x;
        spitDirection.y = playerPos.y - this.body.getPosition().y;
        spitDirection.clamp(40f, 40f);
        ProjectileSpit projectileSpit =  new ProjectileSpit(game.world, damage, this.body.getPosition());
        game.entityList.add(projectileSpit);
        projectileSpit.body.setLinearVelocity(spitDirection.x/3, spitDirection.y/3);
    }
    
}
