import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
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
        kryo.register(Game.class);
        kryo.register(ArrayList.class);
        kryo.register(Player.class);
        kryo.register(Air.class);
        kryo.register(Bullet.class);
        kryo.register(Stone.class);
        kryo.register(DoubleStone.class);
        kryo.register(Enemy.class);
        kryo.register(Explosion.class);
        kryo.register(Gold.class);
        kryo.register(GrapplingHook.class);
        kryo.register(Grenade.class);
        kryo.register(GruntEnemy.class);
        kryo.register(OpenSimplexNoise.class);
        kryo.register(PickaxeRayCastCallback.class);
        kryo.register(Pistol.class);
        kryo.register(Projectile.class);
        kryo.register(Stone.class);
        kryo.register(Terrain.class);
        kryo.register(TileMap.class);
        kryo.register(Weapon.class);
        kryo.register(World.class);
        kryo.register(HashMap.class);
        kryo.register(
    }
    
}
