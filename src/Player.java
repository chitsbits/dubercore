import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Timer.Task;

public class Player extends Entity {

    public static final float PLAYER_WIDTH = 0.4f;
    public static final float PLAYER_HEIGHT = 0.7f;

    public static final float X_SPEED = 5;
    public static final float JUMP_SPEED = 10;
    public static final float MAX_VELOCITY = 5f;

    Sprite playerSprite;

    public int collidingCount;
    public boolean canJump;
    public boolean canMove;
    public int grenadeCount;
    private Vector2 grappleDirection;
    private Vector2 grenadeDirection;
    private Grenade grenade;
    private Weapon weapon;

    public GrapplingHook grapple;
    
    public Player(World world, BodyDef bodyDef){

        collidingCount = 0;
        canJump = false;
        canMove = true;
        grenadeCount = 5;
        //adding a default weapon
        weapon = new Pistol(this);

        // Body definition
        this.bodyDef = bodyDef;
        bodyDef.type = BodyType.DynamicBody;

        // Shape definition
        entityShape = new PolygonShape();
        Vector2[] vertices = new Vector2[6];

        vertices[0] = new Vector2(PLAYER_WIDTH, PLAYER_HEIGHT);
        vertices[1] = new Vector2(-PLAYER_WIDTH, PLAYER_HEIGHT);
        vertices[2] = new Vector2(-PLAYER_WIDTH, -PLAYER_HEIGHT + 0.2f);
        vertices[3] = new Vector2(-PLAYER_WIDTH + 0.2f, -PLAYER_HEIGHT);
        vertices[4] = new Vector2(PLAYER_WIDTH - 0.2f, -PLAYER_HEIGHT);
        vertices[5] = new Vector2(PLAYER_WIDTH, -PLAYER_HEIGHT + 0.2f);

        ((PolygonShape)entityShape).set(vertices);
        // Create body
        body = world.createBody(bodyDef);

        // Add main body fixture
        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = entityShape;
        bodyFixtureDef.filter.categoryBits = Game.PLAYER;
        bodyFixtureDef.filter.maskBits = Game.TERRAIN | Game.PROJECTILE;
        bodyFixtureDef.friction = 1.0f;
        body.createFixture(bodyFixtureDef);
        body.setFixedRotation(true);

        //adding a sprite to the box2d player object
        playerSprite = new Sprite(new Texture("assets\\playerspriteplaceholder.png"));
        playerSprite.setSize(PLAYER_WIDTH*2 ,PLAYER_HEIGHT*2);
        playerSprite.setOrigin(playerSprite.getWidth()/2, playerSprite.getHeight()/2);
        body.setUserData(playerSprite);

        // Feet shape definition
        PolygonShape feetShape = new PolygonShape();
        Vector2 feetCenter = new Vector2();
        feetCenter.x = getPos().x;
        feetCenter.y = getPos().y;
        feetShape.setAsBox(PLAYER_WIDTH - 0.05f, 0.1f, new Vector2(0, -0.75f), 0);

        // Add feet fixture
        FixtureDef feetFixtureDef = new FixtureDef();
        feetFixtureDef.shape = feetShape;
        feetFixtureDef.filter.categoryBits = Game.SENSOR;
        feetFixtureDef.filter.maskBits = Game.TERRAIN;
        feetFixtureDef.isSensor = true;
        Fixture feetFixture = body.createFixture(feetFixtureDef);

        // Add player's user data to the fixture
        feetFixture.setUserData(this);

        entityShape.dispose();
        feetShape.dispose();
        //playerSprite.getTexture().dispose();
        
    }

    public void shootGrapple(World world, Vector3 mousePos) {

        grapple = new GrapplingHook(world, this);
        
        grappleDirection = new Vector2();
        grappleDirection.x = mousePos.x - getPos().x;
        grappleDirection.y = mousePos.y - getPos().y;
        grappleDirection.clamp(40f, 40f);
        grapple.body.setLinearVelocity(grappleDirection);
    }

    public void retractGrapple() {
        body.setGravityScale(1);
        canMove = true;
    }

    public void followGrapple(){
        grappleDirection = new Vector2();
        grappleDirection.x = grapple.getPos().x - getPos().x;
        grappleDirection.y = grapple.getPos().y - getPos().y;
        grappleDirection.clamp(15f, 15f);
        
        body.setLinearVelocity(grappleDirection);
        body.setGravityScale(0);
        canMove = false;
    }

    public void throwGrenade(Game game, Vector3 mousePos){

        grenade = new Grenade(game.world, this);

        grenadeDirection = new Vector2();
        grenadeDirection.x = mousePos.x - getPos().x;
        grenadeDirection.y = mousePos.y - getPos().y;
        grenadeDirection.clamp(40f, 40f);
        grenade.body.setGravityScale(5);
        grenade.body.setLinearVelocity(grenadeDirection);
        //System.out.println("Barmee qunbelah yadaweeyah!");
        

    }

    public void moveLeft() {
        body.applyLinearImpulse(-0.50f, 0, getPos().x, getPos().y, true);
        //body.setLinearVelocity(-X_SPEED, getVel().y);
    }

    public void moveRight() {
        body.applyLinearImpulse(0.50f, 0, getPos().x, getPos().y, true);
    }

    public void jump() {
        body.setLinearVelocity(getVel().x, 10);
    }

    public Weapon getWeapon(){
        return this.weapon;
    }
}
