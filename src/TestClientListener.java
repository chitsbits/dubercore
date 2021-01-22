import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class TestClientListener extends Listener {

    Client client;

    public TestClientListener(Client client){
        this.client = client;
    }

    @Override
    public void connected(Connection c){
        System.out.println("[CLIENT] >> you have connected to the server");
        System.out.println("here");
        JoinGameRequest joinRequest = new JoinGameRequest();
        joinRequest.name = "chits";
        System.out.println(joinRequest.name);
        client.sendTCP(joinRequest);
        System.out.println("reached");
    }

    @Override
    public void disconnected(Connection c){
        System.out.println("[CLIENT] >> you have disconnected from the server");
    }
    
    @Override
    public void received(Connection connection, Object object){
        if (object instanceof JoinGameRequest){
            JoinGameRequest joinGameRequest = (JoinGameRequest) object;
            System.out.println(joinGameRequest.name);
        }
    }
}
