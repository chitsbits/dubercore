import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TestClient {

    public static void main(String[] args) {

        Socket sock = new Socket();
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            sock.connect(new InetSocketAddress("127.0.0.1", 5000), 5000);
        }
        catch (IOException e) {
            System.out.println("Error connecting to server.");
            // e.printStackTrace();

            // Close socket
            try{
                sock.close();
                System.out.println("Socket sucessfully closed.");
            }
            catch (IOException e1){
                System.out.println("Error closing socket");
                e1.printStackTrace();               
            }
            System.exit(0);
        }


        

        // Open streams
        try {
            inputStream = new ObjectInputStream(sock.getInputStream());
            outputStream = new ObjectOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error opening streams");
            e.printStackTrace();
            System.exit(0);
        }
        
        GetLeaderboard request = new GetLeaderboard();

        // Write object
        try {
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error writing object.");
            e.printStackTrace();
        }

        // Get response
        try {
            Object packet = inputStream.readObject();
            if (packet instanceof String){
                System.out.println("Get packet recieved");
                System.out.println(packet);
            }
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Erroring reading packet.");
            e.printStackTrace();
        }

        // WriteLeaderboard packet = new WriteLeaderboard();
        // packet.name = "test";
        // packet.score = 69;

        // // Write object
        // try {
        //     outputStream.writeObject(packet);
        //     outputStream.flush();
        // } catch (IOException e) {
        //     System.out.println("Error writing object.");
        //     e.printStackTrace();
        // }

        // // Close socket
        // try {
        //     sock.close();
        // } catch (IOException e) {
        //     System.out.println("Error closing socket.");
        //     e.printStackTrace();
        // }
        // System.out.println("nice");

    }

}
