import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {

    GameServer gameServer;

    public ServerListener(GameServer gameServer){
        this.gameServer = gameServer;
    }

    @Override
    public void connected(Connection c){
        System.out.println("[SERVER] >> a client has connected");
    }

    @Override
    public void disconnected(Connection c){
        System.out.println("[SERVER] >> a client has disconnected");
    }

    @Override
    public void received (Connection connection, Object object) {

        // Sent by the client upon connecting
        if (object instanceof JoinGameRequest){
            JoinGameRequest joinRequest = (JoinGameRequest) object;
            System.out.println("[SERVER] >> client has joined: " + joinRequest.name);

            // Add the client to the server's set of clients
            //gameServer.clients.add(connection);
            gameServer.addClient(connection);
            
            // Spawn the new player
            gameServer.game.spawnPlayer(joinRequest.name);
            ConnectionConfirm confirm = new ConnectionConfirm();
            connection.sendTCP(confirm);
            System.out.println("[SERVER] >> JoinRequest packet sent");
        }
        
        // TCP packets from the user for player movement
        else if (object instanceof PlayerMovementRequest) {
            PlayerMovementRequest request = (PlayerMovementRequest) object;
        }
    }
}
