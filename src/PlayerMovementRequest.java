public class PlayerMovementRequest {

    String playerName;
    boolean moveLeft;
    boolean moveRight;
    boolean jump;

    public PlayerMovementRequest(){
        this.playerName = null;
        this.moveLeft = false;
        this.moveRight = false;
        this.jump = false;
    }
    
}
