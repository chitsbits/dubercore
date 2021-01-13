import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

    private Game game;  // reference to game

    public MyContactListener(Game game){
        this.game = game;
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().getUserData() instanceof Player){
            game.player1.canJump = false;
        }
        else if (contact.getFixtureB().getUserData() instanceof Player) {
            game.player1.canJump = false;
        }
    }

    @Override
    public void beginContact(Contact contact) {

        if(contact.getFixtureA().getUserData() instanceof Player){
            game.player1.canJump = true;
        }
        else if (contact.getFixtureB().getUserData() instanceof Player) {
            game.player1.canJump = true;
        }

    }

    @Override
    public void postSolve(Contact arg0, ContactImpulse arg1) {}
    @Override
    public void preSolve(Contact arg0, Manifold arg1) {}
}