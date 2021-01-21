import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class TestServerListener extends Listener {

    public void connected(Connection c){
        System.out.println("[SERVER] >> a client has connected");
    }
    public void disconnected(Connection c){
        System.out.println("[SERVER] >> a client has disconnected");
    }

    public void recieved (Connection connection, Object object) {
        // Sent by the client upon connecting
        if (object instanceof JoinGameRequest){
            JoinGameRequest joinRequest = (JoinGameRequest) object;
            System.out.println("[CLIENT] >> " + joinRequest.name);
        }
        
        else if (object instanceof PlayerInputRequest) {
            PlayerInputRequest request = (PlayerInputRequest) object;
            System.out.println("[SERVER] >> input request from client");
        }   

    }
    
}
