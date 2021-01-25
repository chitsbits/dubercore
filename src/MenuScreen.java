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

public class MenuScreen extends ScreenAdapter {

    private Stage stage;
    private DuberCore dubercore;

    public MenuScreen(DuberCore dubercore){
        this.dubercore = dubercore;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("assets\\skin\\craftacular-ui.json"));
        Label titleLabel = new Label("DuberCore", skin);
        TextButton startButton = new TextButton("Start", skin);
        TextButton leaderboardButton = new TextButton("Leaderboard", skin);
        TextButton preferencesButton = new TextButton("Preferences", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(titleLabel).fillY().uniformX();
        table.row().pad(50, 0, 10, 0);
        table.add(startButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(leaderboardButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferencesButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exitButton).fillX().uniformX();

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dubercore.changeScreen(DuberCore.START);			
            }
        });

        leaderboardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dubercore.changeScreen(DuberCore.LEADERBOARD);			
            }
        });

        // TODO
        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {	
                dubercore.changeScreen(DuberCore.PREFERENCES);
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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
