import com.badlogic.gdx.physics.box2d.Body;

public class HealthCrystal extends Terrain {

    public HealthCrystal(int tileCase, float x, float y){
        super(tileCase, x,y);
        this.sprite = GameScreen.textureAtlas.createSprite("crystal" + (tileCase));
    }

    public HealthCrystal(int tileCase, float x, float y, Body body1){
        super(tileCase, x, y);
        this.body1 = body1;
        this.sprite = GameScreen.textureAtlas.createSprite("crystal" + (tileCase));
    }
    
}
