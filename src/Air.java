public class Air extends Terrain {

    public Air(int tileCase, float x, float y){
        super(tileCase, x,y);
        this.sprite = GameScreen.textureAtlas.createSprite("air");
    }
    
}
