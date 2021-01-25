import java.io.IOException;
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

    public GameOverScreen(DuberCore dubercore) {
        this.dubercore = dubercore;
        this.stage = new Stage(new ScreenViewport());
    }

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

        System.out.println("Fetching leaderboard...");
        Socket sock = new Socket();
        ObjectOutputStream outputStream = null;
        try {
            sock.connect(new InetSocketAddress("127.0.0.1", 5000), 5000);
        }
        catch (IOException e) {
            System.out.println("Error connecting to server.");
            closeSocket(sock);
            return false;
        }

        // Open streams
        try {
            outputStream = new ObjectOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error opening streams");
            e.printStackTrace();
            closeSocket(sock);
            return false;
        }

        // Write object
        WriteLeaderboard packet = new WriteLeaderboard();
        packet.name = dubercore.playerName;
        packet.score = dubercore.score;
        try {
            outputStream.writeObject(packet);
            outputStream.flush();
            closeSocket(sock);
        } catch (IOException e) {
            System.out.println("Error writing score.");
            e.printStackTrace();
            closeSocket(sock);
        }
        System.out.println("Score written");
        return true;
    }

    private void closeSocket(Socket sock){
         try{
            sock.close();
            System.out.println("Socket sucessfully closed.");
        }
        catch (IOException e1){
            System.out.println("Error closing socket");
            e1.printStackTrace();               
        }
    }

}
