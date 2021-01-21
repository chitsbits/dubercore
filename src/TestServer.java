import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class TestServer {

    Server server;

    public TestServer() {
        initializeServer();
    }

    public void initializeServer() {
        server = new Server();
        Network2.register(server);
        server.addListener(new TestServerListener());

        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
        System.out.println("Server initiated");
    }

    public static void main(String[] args){
        new TestServer();
    }
    
}
