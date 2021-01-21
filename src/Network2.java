import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// Class to store members common to both client and server
public class Network2 {
    
    public static final int TCPport = 54555;

    /**
     * Registers serializable objects that are sent over the network
     * @param endPoint Endpoint to register (server & client)
     */
    static public void register(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(JoinGameRequest.class);
        kryo.register(PlayerInputRequest.class);

    }
}
