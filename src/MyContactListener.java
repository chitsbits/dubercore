
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

    private DuberCore game;  // reference to game

    public MyContactListener(DuberCore game){
        this.game = game;
    }

    @Override
    public void endContact(Contact contact) {
        // Can jump flag for player 1
        if(contact.getFixtureA().getUserData() instanceof Player || contact.getFixtureB().getUserData() instanceof Player) {
            game.player.collidingCount -= 1;
        }

    }

    @Override
    public void beginContact(Contact contact) {

        if (contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof GruntEnemy) {
            
            GruntEnemy enemy = (GruntEnemy)(contact.getFixtureB().getUserData());
            if (enemy.enemyState.equals("pursuit")){
                if (game.player.checkCooldown(game.player.lastDamageTaken, Player.INVINCIBILITY)){
                    game.player.hp -= enemy.damage;
                }   
            }
            game.player.collidingCount += 1;
        }
        else if (contact.getFixtureB().getUserData() instanceof Player && contact.getFixtureA().getUserData() instanceof GruntEnemy) {

            GruntEnemy enemy = (GruntEnemy)(contact.getFixtureB().getUserData());
            if (enemy.enemyState.equals("pursuit")){
                if (game.player.checkCooldown(game.player.lastDamageTaken, Player.INVINCIBILITY)){
                    game.player.hp -= enemy.damage;
                }   
            }

        }
        else if (contact.getFixtureA().getUserData() instanceof Player && !(contact.getFixtureB().getUserData() instanceof GruntEnemy)){
            game.player.collidingCount += 1;
        }

        else if (contact.getFixtureB().getUserData() instanceof Player && !(contact.getFixtureA().getUserData() instanceof GruntEnemy)){
            game.player.collidingCount += 1;
        }
            // Grappling hook
        else if (contact.getFixtureA().getUserData() instanceof GrapplingHook) {
            contact.getFixtureA().getBody().setLinearVelocity(0,0);
            GrapplingHook hook = (GrapplingHook)(contact.getFixtureA().getUserData());
            hook.player.followGrapple();
            //System.out.println("grappled");
        }
        else if(contact.getFixtureB().getUserData() instanceof GrapplingHook) {
            contact.getFixtureB().getBody().setLinearVelocity(0,0);
            GrapplingHook hook = (GrapplingHook)(contact.getFixtureB().getUserData());
            hook.player.followGrapple();
            //System.out.println("grappled");
        }
        // Bullets

        else if (contact.getFixtureA().getUserData() instanceof Bullet && contact.getFixtureB().getUserData() instanceof Enemy){
            Bullet bullet = (Bullet)(contact.getFixtureA().getUserData());
            //System.out.println("enemy hit");
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }

            Enemy enemy = (Enemy)(contact.getFixtureB().getUserData());
            enemy.setHp(enemy.getHp() - bullet.getDamage());

            if (enemy.getHp() <= 0){
                //System.out.println("enemy killed");
                if (!game.entityDeletionQueue.contains(enemy)){
                    game.entityDeletionQueue.add(enemy);
                    game.score += 10;
                }
            }

        }

        
        else if (contact.getFixtureB().getUserData() instanceof Bullet && contact.getFixtureA().getUserData() instanceof Enemy){
            Bullet bullet = (Bullet)(contact.getFixtureB().getUserData());
            //System.out.println("enemy hit");
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }

            Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
            enemy.setHp(enemy.getHp() - bullet.getDamage());

            if (enemy.getHp() <= 0){
                //System.out.println("enemy killed");
                if (!game.entityDeletionQueue.contains(enemy)){
                    game.entityDeletionQueue.add(enemy);
                    game.score += 10;
                }
            }

        }

        else if (contact.getFixtureA().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet)(contact.getFixtureA().getUserData());
            //System.out.println("hit");
            //System.out.println(contact.getFixtureB().getUserData());
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }
            

        }
        
        else if (contact.getFixtureB().getUserData() instanceof Bullet){
            Bullet bullet = (Bullet)(contact.getFixtureB().getUserData());
            //System.out.println("hit");
            //System.out.println(contact.getFixtureA().getUserData());
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }
        }

        //Grenades

        else if (contact.getFixtureA().getUserData() instanceof Grenade){
            Grenade grenade = (Grenade)(contact.getFixtureA().getUserData());
            //System.out.println("boom");
            grenade.body.setLinearVelocity(0,0);

            Explosion explosion = new Explosion(grenade.body.getWorld(), grenade.body.getPosition());
            if(!game.explosionQueue.contains(explosion)){
                game.explosionQueue.add(explosion);
            }

            if (!game.entityDeletionQueue.contains(grenade)){
                game.entityDeletionQueue.add(grenade);
            }

        }

        else if (contact.getFixtureB().getUserData() instanceof Grenade){
            Grenade grenade = (Grenade)(contact.getFixtureB().getUserData());
            //System.out.println("boom");
            grenade.body.setLinearVelocity(0,0);

            Explosion explosion = new Explosion(grenade.body.getWorld(), grenade.body.getPosition());
            if(!game.explosionQueue.contains(explosion)){
                game.explosionQueue.add(explosion);
            }

            if (!game.entityDeletionQueue.contains(grenade)){
                game.entityDeletionQueue.add(grenade);
            }

        }

        //Explosions

        else if (contact.getFixtureA().getUserData() instanceof Explosion){
            Explosion explosion = (Explosion)(contact.getFixtureA().getUserData());
            //System.out.println("kerblamo");

            if (!game.entityDeletionQueue.contains(explosion)){
                game.entityDeletionQueue.add(explosion);
            }

            if (contact.getFixtureB().getUserData() instanceof Enemy){

                Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
                enemy.setHp(enemy.getHp() - explosion.getDamage());
    
                if (enemy.getHp() <= 0){
                    //System.out.println("enemy killed");
                    if (!game.entityDeletionQueue.contains(enemy)){
                        game.entityDeletionQueue.add(enemy);
                    }
                }

            }
            
        }

        else if (contact.getFixtureB().getUserData() instanceof Explosion){
            Explosion explosion = (Explosion)(contact.getFixtureB().getUserData());
            //System.out.println("kerblamo");

            if (!game.entityDeletionQueue.contains(explosion)){
                game.entityDeletionQueue.add(explosion);
            }

            if (contact.getFixtureA().getUserData() instanceof Enemy){

                Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
                enemy.setHp(enemy.getHp() - explosion.getDamage());
    
                if (enemy.getHp() <= 0){
                    //System.out.println("enemy killed");
                    if (!game.entityDeletionQueue.contains(enemy)){
                        game.entityDeletionQueue.add(enemy);
                    }
                }

            }
    
        }

        else if (contact.getFixtureA().getUserData() instanceof Enemy){
            Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
            if (enemy.enemyState.equals("wander")){
                //System.out.println("hit wall");
                game.enemyRotateQueue.add(enemy);
            }
            else if(enemy.enemyState.equals("pursuit")){
                if (contact.getFixtureB().getUserData() instanceof Terrain){
                    enemy.enemyState = "wander";
                    game.enemyRotateQueue.add(enemy);
                }
            }
        }

        else if (contact.getFixtureB().getUserData() instanceof Enemy){
            Enemy enemy = (Enemy)(contact.getFixtureB().getUserData());
            if (enemy.enemyState.equals("wander")){
                //System.out.println("hit wall");
                game.enemyRotateQueue.add(enemy);
            }
            else if(enemy.enemyState.equals("pursuit")){
                //System.out.println("got here");
                if (contact.getFixtureA().getUserData() instanceof Terrain){
                    enemy.pursuitTimer = System.currentTimeMillis();
                    enemy.enemyState = "wander";
                    game.enemyRotateQueue.add(enemy);
                }
                else if (contact.getFixtureA().getUserData() instanceof Player) {

                }
            }
        }
    }

    @Override
    public void postSolve(Contact arg0, ContactImpulse arg1) {}
    @Override
    public void preSolve(Contact arg0, Manifold arg1) {}
}