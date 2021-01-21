import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer {

    Server server;
    Game game;

    public GameServer() {
        initializeServer();
        game = new Game();
    }

    public void initializeServer() {
        server = new Server();
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addListener(new Listener() {
            public void recieved (Connection connection, Object object) {
                if (object instanceof PlayerInputRequest) {
                    PlayerInputRequest request = (PlayerInputRequest) object;
                    System.out.println("input request");
                }
                // Sent by the client upon connecting
                else if (object instanceof JoinGameRequest){
                    JoinGameRequest joinRequest = (JoinGameRequest) object;
                }
            }
        });
    }
    
}
