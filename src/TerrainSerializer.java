// import com.esotericsoftware.kryo.Kryo;
// import com.esotericsoftware.kryo.Serializer;
// import com.esotericsoftware.kryo.io.Input;
// import com.esotericsoftware.kryo.io.Output;

// public class TerrainSerializer extends Serializer<Terrain> {
    
//     @Override
//     public void write (Kryo kryo, Output output, Terrain terrain) {
//        output.writeInt(color.getRGB());
//     }
 
//     @Override
//     public Terrain read (Kryo kryo, Input input, Class<Terrain> terrain) {
//        return new Terrain(input.readInt());
//     }

//     public Terrain read (Kryo kryo, Input input, Class<? extends Terrain> terrain) {
//        return new Terrain(input.readInt());
//     }
//  }