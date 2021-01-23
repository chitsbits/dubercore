import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.serializers.DeflateSerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.ObjectArraySerializer;
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
        kryo.register(GameState.class);
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
        kryo.register(Pistol.class);
        kryo.register(Projectile.class);
        kryo.register(Stone.class);
        kryo.register(Weapon.class);
        kryo.register(HashMap.class);
        kryo.register(Sprite.class);
        kryo.register(Array.class);
        kryo.register(Object[].class, new DeflateSerializer(new ObjectArraySerializer()));
        kryo.register(Fixture.class);
        kryo.register(BodyDef.class);
        kryo.register(Terrain.class);
        kryo.register(Terrain[].class);
        kryo.register(Terrain[][].class);
        kryo.register(Vector2.class);
        
    }
}
