
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

public class Launcher {
    public static void main (String[] args) {

        Log.set(Log.LEVEL_DEBUG);

        Client client = new Client();
        Network.register(client);
        client.start();

        client.addListener(new TestClientListener(client));

        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            System.out.println("Unable to connect to server");
            e.printStackTrace();
        }
        new LwjglApplication(new GameClient(client), "DuberCore", 1280, 720);
    }
}