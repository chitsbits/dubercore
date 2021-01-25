import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LeaderboardScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Stage stage;
    private DuberCore dubercore;
    private Label leaderboardLabel;

    public LeaderboardScreen(DuberCore dubercore) {
        this.dubercore = dubercore;
        this.font = new BitmapFont();
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show(){

        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("assets\\skin\\craftacular-ui.json"));
        TextButton backButton = new TextButton("Back", skin);
        leaderboardLabel = new Label(getLeaderboard(), skin);
        table.add(leaderboardLabel);
        table.row().pad(10, 0, 0, 0);
        table.add(backButton).fillX().uniformX().colspan(2);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dubercore.changeScreen(DuberCore.MENU);			
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

    public String getLeaderboard(){

        System.out.println("Fetching leaderboard...");
        String boardText = "Error";
        Socket sock = new Socket();
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            sock.connect(new InetSocketAddress("127.0.0.1", 5000), 5000);
        }
        catch (IOException e) {
            System.out.println("Error connecting to server.");
            closeSocket(sock);
            return "Failed to connect to server.";
        }

        // Open streams
        try {
            inputStream = new ObjectInputStream(sock.getInputStream());
            outputStream = new ObjectOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error opening streams");
            e.printStackTrace();
            closeSocket(sock);
            return "Failed to connect to server.";
        }

        // Write object
        GetLeaderboard request = new GetLeaderboard();
        try {
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error writing object.");
            e.printStackTrace();
            closeSocket(sock);
            return "Failed to connect to server.";
        }

        // Get response
        try {
            Object packet = inputStream.readObject();
            if (packet instanceof String){
                System.out.println("Get packet recieved");
                boardText = (String) packet;
            }
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Erroring reading packet.");
            e.printStackTrace();
            closeSocket(sock);
            return "Invalid data.";
        }
        closeSocket(sock);
        return boardText;
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
