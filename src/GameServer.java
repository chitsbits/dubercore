import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer {

    Server server;
    Game game;
    HashSet<Connection> clients;
    boolean mutableReady;

    boolean running;

    public GameServer() {
        game = new Game();
        new Thread(game).start();
        initializeServer();
    }

    public void initializeServer() {
        clients = new HashSet<Connection>();
        server = new Server(4194304, 4194304);

        // Register the classes that will be serialized by Kyro and sent over the
        // network
        Network.register(server);

        server.addListener(new ServerListener(this));

        // Open server on TCP port 54555, UDP port 54777
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start(); // Creates a new thread and starts the listener

        running = true;
        while (running) {
            // Send gamestate to all clients
            synchronized (clients) {
                for (Connection connection : clients) {
                    GameState gameStatePacket = game.generateGameState();
                    connection.sendUDP(gameStatePacket);
                }
            }
        }
        server.close();
    }

    public void addClient(Connection connection) {
        synchronized (clients) {
            clients.add(connection);
        }
    }

    public void removeClient(Connection connection) {
        synchronized (clients){
            clients.remove(connection);
        }
    }

    public static void main(String[] args) {
        Log.set(Log.LEVEL_DEBUG);
        new GameServer();
    }

}
