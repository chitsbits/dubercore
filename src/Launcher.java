
/**
 * [Launcher.java]
 * Launches the game client
 * @author Sunny Jiao
 * @version 1.0
 */

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Launcher {

    public static void main (String[] args) {
        new LwjglApplication(new DuberCore(), "DuberCore", 1280, 720);
    }
}