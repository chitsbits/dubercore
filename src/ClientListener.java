import java.util.Scanner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {

    GameClient gameClient;

    public ClientListener(GameClient gameClient){
        this.gameClient = gameClient;
    }

    @Override
    public void connected(Connection c){
        System.out.println("[CLIENT] >> you have connected to the server");

        JoinGameRequest joinRequest = new JoinGameRequest();
        Scanner input = new Scanner(System.in);
        String name = input.next();
        joinRequest.name = name;
        gameClient.playerName = name;
        gameClient.client.sendTCP(joinRequest);
        input.close();
        System.out.println("client listener connection");
    }

    @Override
    public void disconnected(Connection c){
        System.out.println("[CLIENT] >> you have disconnected from the server");
    }
    
    @Override
    public void received(Connection connection, Object object){

        // Get updated gamestate
        if (object instanceof GameState){
            System.out.println("[CLIENT] >> GameState packet recieved");
            GameState gameUpdate = (GameState) object;
            if (gameUpdate.terrainArr != null){
                System.out.println("MAP DATA RECIEVED");
            }
            gameClient.updateGameState(gameUpdate);
            
        }
        else if (object instanceof ConnectionConfirm){
            gameClient.running = true;
            System.out.println("[CLIENT] >> ConnectionConfirm packet recieved");
            new LwjglApplication(gameClient, "DuberCore", 1280, 720);
            System.out.println("Game launched");
        }
    }
    
}
