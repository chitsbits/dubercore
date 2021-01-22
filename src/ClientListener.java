import java.util.Scanner;

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
    }

    @Override
    public void disconnected(Connection c){
        System.out.println("[CLIENT] >> you have disconnected from the server");
    }
    
    @Override
    public void received(Connection connection, Object object){

        // Get updated gamestate
        if (object instanceof GameUpdate){
            GameUpdate gameUpdate = (GameUpdate) object;
            gameClient.localGame = gameUpdate.game;
            
        }
        if (object instanceof ConnectionConfirm){
            gameClient.running = true;
        }
    }
    
}
