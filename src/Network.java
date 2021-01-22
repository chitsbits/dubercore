import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {

    /**
     * Registers serializable objects that are sent over the network
     * @param endPoint Endpoint to register (server & client)
     */
    static public void register(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(JoinGameRequest.class);
        kryo.register(PlayerMovementRequest.class);
        kryo.register(ConnectionConfirm.class);
        kryo.register(GameUpdate.class);

    }
    
}
