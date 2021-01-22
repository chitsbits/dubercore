import java.io.IOException;
import java.util.HashSet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer {

    Server server;
    Game game;
    HashSet<Connection> clients;

    boolean running;

    public GameServer() {
        System.out.println("bru2h");
        game = new Game();
        new Thread(game).start();
        System.out.println("bruh3");
        initializeServer();
    }

    public void initializeServer() {
        System.out.println("bruh4");
        clients = new HashSet<Connection>();
        server = new Server();

        // Register the classes that will be serialized by Kyro and sent over the network
        Network.register(server);

        server.addListener(new ServerListener(this));

        // Open server on TCP port 54555, UDP port 54777
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();

        running = true;
        while(running){
            // Send gamestate to all clients
            for(Connection connection : clients){
                connection.sendUDP(game);
            }
        }
        server.close();
    }

    public static void main(String[] args){
        System.out.println("bruh1");
        Log.set(Log.LEVEL_DEBUG);
        new GameServer();
    }
    
}
