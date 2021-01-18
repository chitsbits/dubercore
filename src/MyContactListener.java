import com.badlogic.gdx.Gdx;
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
        // Can jump flag for player 1
        if(contact.getFixtureA().getUserData() instanceof Player || contact.getFixtureB().getUserData() instanceof Player) {
            game.player1.collidingCount -= 1;
        }
    }

    @Override
    public void beginContact(Contact contact) {

        if(contact.getFixtureA().getUserData() instanceof Player || contact.getFixtureB().getUserData() instanceof Player){
            game.player1.collidingCount += 1;
        }
        // Grappling hook
        else if (contact.getFixtureA().getUserData() instanceof GrapplingHook){
            contact.getFixtureA().getBody().setLinearVelocity(0,0);
            GrapplingHook hook = (GrapplingHook)(contact.getFixtureA().getUserData());
            hook.player.followGrapple();
            System.out.println("grappled");
        }
        else if(contact.getFixtureB().getUserData() instanceof GrapplingHook) {
            contact.getFixtureB().getBody().setLinearVelocity(0,0);
            GrapplingHook hook = (GrapplingHook)(contact.getFixtureB().getUserData());
            hook.player.followGrapple();
            System.out.println("grappled");
        }
        // Bullets

        else if (contact.getFixtureA().getUserData() instanceof Bullet && contact.getFixtureB().getUserData() instanceof Enemy){
            Bullet bullet = (Bullet)(contact.getFixtureA().getUserData());
            System.out.println("enemy hit");
            if (!game.bodyDeletionList.contains(bullet.body)){
                game.bodyDeletionList.add(bullet.body);
            }

            Enemy enemy = (Enemy)(contact.getFixtureB().getUserData());
            enemy.setHp(enemy.getHp() - bullet.getDamage());

            if (enemy.getHp() <= 0){
                System.out.println("enemy killed");
                if (!game.bodyDeletionList.contains(enemy.body)){
                    game.bodyDeletionList.add(enemy.body);
                }
            }

        }

        
        else if (contact.getFixtureB().getUserData() instanceof Bullet && contact.getFixtureA().getUserData() instanceof Enemy){
            Bullet bullet = (Bullet)(contact.getFixtureB().getUserData());
            System.out.println("enemy hit");
            if (!game.bodyDeletionList.contains(bullet.body)){
                game.bodyDeletionList.add(bullet.body);
            }

            Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
            enemy.setHp(enemy.getHp() - bullet.getDamage());

            if (enemy.getHp() <= 0){
                System.out.println("enemy killed");
                if (!game.bodyDeletionList.contains(enemy.body)){
                    game.bodyDeletionList.add(enemy.body);
                }
            }

        }

        else if (contact.getFixtureA().getUserData() instanceof Bullet){
            Bullet bullet = (Bullet)(contact.getFixtureA().getUserData());
            System.out.println("hit");
            //System.out.println(contact.getFixtureB().getUserData());
            if (!game.bodyDeletionList.contains(bullet.body)){
                game.bodyDeletionList.add(bullet.body);
            }
            

        }
        
        else if (contact.getFixtureB().getUserData() instanceof Bullet){
            Bullet bullet = (Bullet)(contact.getFixtureB().getUserData());
            System.out.println("hit");
            //System.out.println(contact.getFixtureA().getUserData());
            if (!game.bodyDeletionList.contains(bullet.body)){
                game.bodyDeletionList.add(bullet.body);
            }
        }
    }

    @Override
    public void postSolve(Contact arg0, ContactImpulse arg1) {}
    @Override
    public void preSolve(Contact arg0, Manifold arg1) {}
}