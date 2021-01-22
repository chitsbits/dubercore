import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class TestClient {

    public static void main(String[] args){
        
        Log.set(Log.LEVEL_DEBUG);
        Client client;
        client = new Client();
        Network2.register(client);
        client.start();

        client.addListener(new TestClientListener(client));

        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            System.out.println("Unable to connect to server");
            e.printStackTrace();
        }
        boolean running = true;
        Scanner input = new Scanner(System.in);
        while(running){
            System.out.println("msg: ");
            PlayerMovementRequest req = new PlayerMovementRequest();
            String msg = input.next();
            if(msg.equals("quit")){
                running = false;
                break;
            }
            //req.message = msg;
            System.out.println(client.sendUDP(req));
        }
        input.close();
    }
    
}
