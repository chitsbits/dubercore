
/**
 * [PreferencesScreen.java]
 * Screen with preferences options
 * @author Sunny Jiao
 * @version 1.0
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
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

    /**
     * Creates the screen
     * @param dubercore Refernce to game
     */
    public PreferencesScreen(DuberCore dubercore) {

        skin = new Skin(Gdx.files.internal("assets\\skin\\craftacular-ui.json"));
        skin2 = new Skin(Gdx.files.internal("assets\\skin2\\uiskin.json"));

        this.dubercore = dubercore;
        this.stage = new Stage(new ScreenViewport());
        debugCheckbox = new CheckBox(null , skin2);
        debugCheckbox.setChecked(false);
    }

    /**
     * Called when this screen becomes the current screen for a Game.
     */
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
        Label musicVolLabel = new Label("Music Volume", skin);
        Slider musicVolSlider = new Slider(0f, 0.5f, 0.01f, false, skin);
        Label sfxVolLabel = new Label("SFX Volume", skin);
        Slider sfxVolSlider = new Slider(0f, 0.5f, 0.01f, false, skin);
        usernameTextField = new TextField(dubercore.playerName, skin);

        table.add(nameLabel).fillY().uniformX();
        table.add(usernameTextField);
        table.row().pad(40, 0, 0, 0);
        table.add(debugModeLabel).fillX().uniformX();
        table.add(debugCheckbox);
        table.row().pad(40, 0, 0, 0);
        table.add(musicVolLabel).fillX().uniformX();
        table.add(musicVolSlider);
        table.row().pad(40, 0, 0, 0);
        table.add(sfxVolLabel).fillX().uniformX();
        table.add(sfxVolSlider);
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

        musicVolSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dubercore.setMusicVolume(musicVolSlider.getValue());
            }
        });

        sfxVolSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dubercore.setSFXVolume(sfxVolSlider.getValue());
            }
        });

    }

    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        dubercore.playerName = usernameTextField.getText();
    }

}
