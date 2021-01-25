import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreferencesScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Stage stage;
    private DuberCore dubercore;
    TextField usernameTextField;

    public PreferencesScreen(DuberCore dubercore) {
        this.dubercore = dubercore;
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
        Label nameLabel = new Label("Display Name", skin);
        usernameTextField = new TextField(dubercore.playerName , skin);

        table.add(nameLabel).fillX().uniformX();
        table.row().pad(0, 0, 0, 5);
        table.add(usernameTextField);
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
        dubercore.playerName = usernameTextField.getText();
    }

}
