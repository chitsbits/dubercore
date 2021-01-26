
/**
 * [GameOverScreen.java]
 * Screen that displays upon death/exit
 * @author Sunny Jiao
 * @version 1.0
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen extends ScreenAdapter {

    private Stage stage;
    private DuberCore dubercore;
    private Label gameOverLabel;
    private Label scoreLabel;

    /**
     * Creates the screen
     * @param dubercore Reference to game
     */
    public GameOverScreen(DuberCore dubercore) {
        this.dubercore = dubercore;
        this.stage = new Stage(new ScreenViewport());
    }

    /**
     * Called when this screen becomes the current screen for a Game.
     */
    @Override
    public void show(){

        // Update leaderboard on a separate thread so client doesn't hang
        new Thread(){
            @Override
            public void run(){
                updateLeaderboard();
            }
        }.start();

        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("assets\\skin\\craftacular-ui.json"));
        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        TextButton exitButton = new TextButton("Quit", skin);
        gameOverLabel = new Label("Game Over", skin);
        scoreLabel = new Label("Score: " + Integer.toString(dubercore.score), skin);
        table.row().pad(0, 0, 0, 0);
        table.add(gameOverLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(scoreLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(mainMenuButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exitButton).fillX().uniformX();

        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dubercore.changeScreen(DuberCore.MENU);			
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();		
            }
        });
    }

    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {      
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * @return whether or not score was successfully written
     */
    public boolean updateLeaderboard(){

        System.out.println("Connecting to leaderboard...");
        Socket sock = new Socket();
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            sock.connect(new InetSocketAddress("127.0.0.1", 5000), 5000);
        }
        catch (IOException e) {
            System.out.println("Error connecting to server.");
            LeaderboardServer.closeSocket(sock);
            return false;
        }

        // Open streams
        try {
            inputStream = new ObjectInputStream(sock.getInputStream());
            outputStream = new ObjectOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error opening streams");
            e.printStackTrace();
            LeaderboardServer.closeSocket(sock);
            return false;
        }

        // Write object
        WriteLeaderboard packet = new WriteLeaderboard();
        packet.name = dubercore.playerName;
        packet.score = dubercore.score;
        try {
            outputStream.writeObject(packet);
            outputStream.flush();
            System.out.println("Score written");
        } catch (IOException e) {
            System.out.println("Error writing score.");
            e.printStackTrace();
        } finally {
            LeaderboardServer.closeSocket(sock);
        }
        return true;
    }

}
