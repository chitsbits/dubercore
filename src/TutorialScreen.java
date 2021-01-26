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

public class TutorialScreen extends ScreenAdapter {

    private Stage stage;
    private DuberCore dubercore;
    private Skin skin;

    public TutorialScreen(DuberCore dubercore) {

        skin = new Skin(Gdx.files.internal("assets\\skin\\craftacular-ui.json"));

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

        String tutorial = "LMB to shoot/use grapple\n" +
        "RMB to mine\n" +
        "MWHEEL to switch equipment\n" +
        "G to throw grenade\n" +
        "R to reload\n" +
        "WASD to move\n";

        Label tutorialLabel = new Label(tutorial, skin);
        TextButton backButton = new TextButton("Back", skin);

        table.add(tutorialLabel).fillY().uniformX();
        table.row().pad(40, 0, 0, 0);
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

}
