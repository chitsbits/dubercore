import com.esotericsoftware.kryonet.Connection;

public class GameStateUpdateThread implements Runnable {

    Connection client;
    GameServer gameServer;
    boolean running;

    public GameStateUpdateThread(Connection client, GameServer gameServer){
            this.client = client;
            this.gameServer = gameServer;
            running = true;
    }

    @Override
    public void run() {
        while(running){
            GameState gameStatePacket = gameServer.game.generateGameState();
            client.sendUDP(gameStatePacket);
        }
    }

    public void sendUpdatedMap(Terrain[][] terrainArr){
        client.sendTCP(terrainArr);
    }
    
}
