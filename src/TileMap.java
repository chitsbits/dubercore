public class TileMap {
    
    // Each tile on the map is 1 meter
    public static final int MAP_COLS = 240;
    public static final int MAP_ROWS = 135;

    private float increment = 0.1f;

    public int[][] cornerArr;
    OpenSimplexNoise noise;
    public TileMap(){
        generateNewMap();
    }

    /**
     * TODO: use float values from noise.eval to generate minerals
     */
    public void generateNewMap() {
        noise = new OpenSimplexNoise((long)(Math.random() * Long.MAX_VALUE));
        cornerArr = new int[MAP_COLS+1][MAP_ROWS+1];

        float xoffset = 0f;
        for(int i = 0; i < MAP_COLS+1; i++){
            xoffset += increment;
            float yoffset = 0f;
            for(int j = 0; j < MAP_ROWS+1; j++){
                float value = (float)noise.eval(xoffset, yoffset); // eval returns a double from -1 to 1
                cornerArr[i][j] = (int)(Math.ceil(value));
                yoffset += increment;
            }
        }
    }

}
