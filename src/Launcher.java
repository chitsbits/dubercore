
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Launcher {
    public static void main (String[] args) {
        new LwjglApplication(new DuberCore(), "DuberCore", 1280, 720);
    }
}