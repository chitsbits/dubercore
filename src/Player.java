import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {

    public static final float PLAYER_WIDTH = 0.4f;
    public static final float PLAYER_HEIGHT = 0.7f;

    public static final float X_SPEED = 5;
    public static final float JUMP_SPEED = 10;
    public static final float MAX_VELOCITY = 5f;
    public static final long INVINCIBILITY = 666;
    public static final long MINING_SPEED = 300;

    public int collidingCount;
    public boolean canJump;
    public boolean canMove;
    public boolean isGrappling;
    public boolean grappleFired;
    public boolean isMining;
    private Vector2 grappleDirection;
    private Vector2 grenadeDirection;
    private Grenade grenade;
    private Weapon weapon;
    public int activeItem;
    public float hp = 100;

    private Vector2 feetCenter;
    private Fixture playerFixture;
    private FixtureDef bodyFixtureDef;

    public long lastGrenadeUse;
    public long lastWeaponFire;
    public long lastGrappleUse;
    public long lastDamageTaken;
    public long lastTerrainMined;

    public boolean grenadeReady;
    public boolean grappleReady;
    public boolean weaponReady;
    public boolean mineReady;

    public GrapplingHook grapple;
    
    public Player(World world, BodyDef bodyDef){

        collidingCount = 0;
        canJump = false;
        canMove = true;
        isGrappling = false;
        grappleFired = false;
        isMining  = false;
        activeItem = 0;

        grappleReady = true;
        grenadeReady = true;
        weaponReady = true;
        mineReady = true;
        
        //adding a default weapon
        weapon = new Pistol();

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
        bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = entityShape;
        bodyFixtureDef.filter.categoryBits = DuberCore.PLAYER;
        bodyFixtureDef.filter.maskBits = DuberCore.TERRAIN | DuberCore.PROJECTILE | DuberCore.SENSOR;
        bodyFixtureDef.friction = 1.0f;
        
        playerFixture = body.createFixture(bodyFixtureDef);
        body.setFixedRotation(true);
        playerFixture.setDensity(475);
        playerFixture.setUserData(this);

        //adding a sprite to the box2d player object
        sprite = GameScreen.textureAtlas.createSprite("player");
        sprite.setSize(PLAYER_WIDTH*2 + 0.7f, PLAYER_HEIGHT*2);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

        body.setUserData(this);

        // Feet shape definition
        PolygonShape feetShape = new PolygonShape();
        feetCenter = new Vector2();
        feetCenter.x = getPos().x;
        feetCenter.y = getPos().y;
        feetShape.setAsBox(PLAYER_WIDTH - 0.05f, 0.1f, new Vector2(0, -0.75f), 0);
        
        // Add feet fixture
        FixtureDef feetFixtureDef = new FixtureDef();
        feetFixtureDef.shape = feetShape;
        feetFixtureDef.filter.categoryBits = DuberCore.SENSOR;
        feetFixtureDef.filter.maskBits = DuberCore.TERRAIN;
        feetFixtureDef.isSensor = true;
        Fixture feetFixture = body.createFixture(feetFixtureDef);

        // Add player's user data to the fixture
        feetFixture.setUserData(this);

        entityShape.dispose();
        feetShape.dispose();
    }

    public void shootGrapple(World world, Vector3 mousePos) {

        grapple = new GrapplingHook(world, this);
        grappleFired = true;
        grappleDirection = new Vector2();
        grappleDirection.x = mousePos.x - getPos().x;
        grappleDirection.y = mousePos.y - getPos().y;
        grappleDirection.clamp(40f, 40f);
        grapple.body.setLinearVelocity(grappleDirection);
    }

    public void retractGrapple() {
        lastGrappleUse = System.currentTimeMillis();
        canMove = true;
        isGrappling = false;
        grappleReady = false;
        grappleFired = false;
    }

    public void followGrapple(){
        grappleDirection = new Vector2();
        grappleDirection.x = grapple.getPos().x - getPos().x;
        grappleDirection.y = grapple.getPos().y - getPos().y;
        grappleDirection.clamp(15f, 15f);
            
        body.setLinearVelocity(grappleDirection);
        canMove = false;
    }

    public void throwGrenade(DuberCore game, Vector3 mousePos){

        grenade = new Grenade(game.world, getPos());

        grenadeDirection = new Vector2();
        grenadeDirection.x = mousePos.x - getPos().x;
        grenadeDirection.y = mousePos.y - getPos().y;
        grenadeDirection.clamp(20f, 20f);
        grenade.body.setGravityScale(2);
        grenade.body.setLinearVelocity(grenadeDirection);
        game.entityList.add(grenade);
    }

    public void moveLeft() {
        body.applyLinearImpulse(-0.50f, 0, getPos().x, getPos().y, true);
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
