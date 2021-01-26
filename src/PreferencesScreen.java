import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreferencesScreen extends ScreenAdapter {

    private Stage stage;
    private DuberCore dubercore;
    private TextField usernameTextField;
    private CheckBox debugCheckbox;
    private Skin skin;
    private Skin skin2;

    public PreferencesScreen(DuberCore dubercore) {

        skin = new Skin(Gdx.files.internal("assets\\skin\\craftacular-ui.json"));
        skin2 = new Skin(Gdx.files.internal("assets\\skin2\\uiskin.json"));

        this.dubercore = dubercore;
        this.stage = new Stage(new ScreenViewport());
        debugCheckbox = new CheckBox(null , skin2);
        debugCheckbox.setChecked(false);
    }

    @Override
    public void show(){
        
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        TextButton backButton = new TextButton("Back", skin);
        Label nameLabel = new Label("Display Name", skin);
        Label debugModeLabel = new Label("Debug Mode", skin);
        usernameTextField = new TextField(dubercore.playerName, skin);

        table.add(nameLabel).fillY().uniformX();
        table.add(usernameTextField);
        table.row().pad(40, 0, 0, 0);
        table.add(debugModeLabel).fillX().uniformX();
        table.add(debugCheckbox);
        table.row().pad(40, 0, 0, 0);
        table.add(backButton).fillX().uniformX().colspan(2);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dubercore.changeScreen(DuberCore.MENU);			
            }
        });

        debugCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dubercore.setDebugMode(debugCheckbox.isChecked());
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
