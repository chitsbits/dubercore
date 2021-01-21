import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class TestClientListener extends Listener {

    Client client;

    public TestClientListener(Client client){
        this.client = client;
    }

    public void connected(Connection c){
        System.out.println("[CLIENT] >> you have connected to the server");
        JoinGameRequest joinRequest = new JoinGameRequest("chits");
        client.sendTCP(joinRequest);
        System.out.println("reached");
    }
    public void disconnected(Connection c){
        System.out.println("[CLIENT] >> you have disconnected from the server");
    }
    
    public void recieved(Connection connection, Object object){
        if (object instanceof JoinGameRequest){
            JoinGameRequest joinGameRequest = (JoinGameRequest) object;
            System.out.println(joinGameRequest.name);
        }
    }
}
