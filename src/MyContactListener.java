/**
 * [MyContactListener.java]
 * contact listner for body collisions in the game world
 * @author Viraj Bane, Sunny Jiao
 * @version 1.0 Build 1 January 25th
 */
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

    private DuberCore game;  // reference to game

    /**
     * creates a new contact callback method inside the game
     */
    public MyContactListener(DuberCore game){
        this.game = game;
    }

    @Override
    /**
     * Called when two fixtures cease to touch.
     */
    public void endContact(Contact contact) {

        if (contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof Enemy) {
            
            Enemy enemy = (Enemy) contact.getFixtureB().getUserData();
            if (enemy.enemyState.equals("pursuit")){
                enemy.setColliding(false); 
            }
        }
        else if (contact.getFixtureB().getUserData() instanceof Player && contact.getFixtureA().getUserData() instanceof Enemy) {

            Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
            if (enemy.enemyState.equals("pursuit")){
                enemy.setColliding(false);
            }

        }
        // Can jump flag for player 1
        else if(contact.getFixtureA().getUserData() instanceof Player && !(contact.getFixtureB().getUserData() instanceof ProjectileSpit)) {
            game.player.collidingCount -= 1;
        }
        else if(contact.getFixtureB().getUserData() instanceof Player && !(contact.getFixtureB().getUserData() instanceof ProjectileSpit)) {
            game.player.collidingCount -= 1;
        }     
    }
    
    @Override
    /**
     * Called when two fixtures begin to touch.
     */
    public void beginContact(Contact contact) {

        if (contact.getFixtureA().getUserData() instanceof Player && contact.getFixtureB().getUserData() instanceof Enemy) {
            
            Enemy enemy = (Enemy)(contact.getFixtureB().getUserData());
            if (enemy.enemyState.equals("pursuit")){
                enemy.setColliding(true); 
            }
        }
        else if (contact.getFixtureB().getUserData() instanceof Player && contact.getFixtureA().getUserData() instanceof Enemy) {

            Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
            if (enemy.enemyState.equals("pursuit")){
                enemy.setColliding(true);
            }

        }
        else if (contact.getFixtureA().getUserData() instanceof ProjectileSpit && contact.getFixtureB().getUserData() instanceof Player){
            ProjectileSpit spit = (ProjectileSpit)(contact.getFixtureA().getUserData());
            if (!game.entityDeletionQueue.contains(spit)){
                game.entityDeletionQueue.add(spit);
            }
            if (DuberCore.checkCooldown(game.player.lastDamageTaken, Player.INVINCIBILITY)){
                DuberCore.PLAYER_HURT_SOUND.play();
                game.player.damage(spit.getDamage());
                game.player.lastDamageTaken = System.currentTimeMillis();
            }
        }
        else if (contact.getFixtureB().getUserData() instanceof ProjectileSpit && contact.getFixtureA().getUserData() instanceof Player){
            ProjectileSpit spit = (ProjectileSpit)(contact.getFixtureB().getUserData());
            if (!game.entityDeletionQueue.contains(spit)){
                game.entityDeletionQueue.add(spit);
            }
            if (DuberCore.checkCooldown(game.player.lastDamageTaken, Player.INVINCIBILITY)){
                DuberCore.PLAYER_HURT_SOUND.play();
                game.player.damage(spit.getDamage());
                game.player.lastDamageTaken = System.currentTimeMillis();
            }
        }
        else if (contact.getFixtureA().getUserData() instanceof Player){
            game.player.collidingCount += 1;

        }

        else if (contact.getFixtureB().getUserData() instanceof Player){
            game.player.collidingCount += 1;
        }
            // Grappling hook
        else if (contact.getFixtureA().getUserData() instanceof GrapplingHook) {
            contact.getFixtureA().getBody().setLinearVelocity(0,0);
            GrapplingHook hook = (GrapplingHook)(contact.getFixtureA().getUserData());
            hook.player.isGrappling = true;
        }
        else if(contact.getFixtureB().getUserData() instanceof GrapplingHook) {
            contact.getFixtureB().getBody().setLinearVelocity(0,0);
            GrapplingHook hook = (GrapplingHook)(contact.getFixtureB().getUserData());
            hook.player.isGrappling = true;
        }
        // Bullets
        else if (contact.getFixtureA().getUserData() instanceof Bullet && contact.getFixtureB().getUserData() instanceof Enemy){
            Bullet bullet = (Bullet)(contact.getFixtureA().getUserData());
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }
            Enemy enemy = (Enemy)(contact.getFixtureB().getUserData());
            enemy.damage(bullet.getDamage());
            DuberCore.ENEMY_HURT_SOUND.play();

            if (enemy.getHp() <= 0){
                DuberCore.ENEMY_KILLED_SOUND.play();
                if (!game.entityDeletionQueue.contains(enemy)){
                    game.entityDeletionQueue.add(enemy);
                    game.score += 10;
                }
            }
        }
        else if (contact.getFixtureB().getUserData() instanceof Bullet && contact.getFixtureA().getUserData() instanceof Enemy){
            Bullet bullet = (Bullet)(contact.getFixtureB().getUserData());
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }
            Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
            enemy.damage(bullet.getDamage());
            DuberCore.ENEMY_HURT_SOUND.play();

            if (enemy.getHp() <= 0){
                DuberCore.ENEMY_KILLED_SOUND.play();
                if (!game.entityDeletionQueue.contains(enemy)){
                    game.entityDeletionQueue.add(enemy);
                    game.score += 10;
                }
            }
        }
        else if (contact.getFixtureA().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet)(contact.getFixtureA().getUserData());
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }
        }
        else if (contact.getFixtureB().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet)(contact.getFixtureB().getUserData());
            if (!game.entityDeletionQueue.contains(bullet)){
                game.entityDeletionQueue.add(bullet);
            }
        }
        else if (contact.getFixtureA().getUserData() instanceof ProjectileSpit) {
            ProjectileSpit spit = (ProjectileSpit)(contact.getFixtureA().getUserData());
            if (!game.entityDeletionQueue.contains(spit)){
                game.entityDeletionQueue.add(spit);
            }
        }
        
        else if (contact.getFixtureB().getUserData() instanceof ProjectileSpit){
            ProjectileSpit spit = (ProjectileSpit)(contact.getFixtureB().getUserData());
            if (!game.entityDeletionQueue.contains(spit)){
                game.entityDeletionQueue.add(spit);
            }
        }
        //Grenades
        else if (contact.getFixtureA().getUserData() instanceof Grenade){
            Grenade grenade = (Grenade)(contact.getFixtureA().getUserData());
            grenade.body.setLinearVelocity(0,0);

            Explosion explosion = new Explosion(grenade.body.getWorld(), grenade.body.getPosition());
            if(!game.explosionQueue.contains(explosion)){
                game.explosionQueue.add(explosion);
                game.entityList.add(explosion);
            }
            if (!game.entityDeletionQueue.contains(grenade)){
                game.entityDeletionQueue.add(grenade);
            }
        }
        else if (contact.getFixtureB().getUserData() instanceof Grenade){
            Grenade grenade = (Grenade)(contact.getFixtureB().getUserData());
            grenade.body.setLinearVelocity(0,0);

            Explosion explosion = new Explosion(grenade.body.getWorld(), grenade.body.getPosition());
            if(!game.explosionQueue.contains(explosion)){
                game.explosionQueue.add(explosion);
                game.entityList.add(explosion);
            }
            if (!game.entityDeletionQueue.contains(grenade)){
                game.entityDeletionQueue.add(grenade);
            }
        }
        //Explosions
        else if (contact.getFixtureA().getUserData() instanceof Explosion){
            Explosion explosion = (Explosion)(contact.getFixtureA().getUserData());
            if (!game.entityDeletionQueue.contains(explosion)){
                game.entityDeletionQueue.add(explosion);
            }
            if (contact.getFixtureB().getUserData() instanceof Enemy){
                Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
                enemy.damage(explosion.getDamage());
    
                if (enemy.getHp() <= 0){
                    if (!game.entityDeletionQueue.contains(enemy)){
                        game.entityDeletionQueue.add(enemy);
                    }
                }
            }
        }

        else if (contact.getFixtureB().getUserData() instanceof Explosion){
            Explosion explosion = (Explosion)(contact.getFixtureB().getUserData());

            if (!game.entityDeletionQueue.contains(explosion)){
                game.entityDeletionQueue.add(explosion);
            }
            if (contact.getFixtureA().getUserData() instanceof Enemy){

                Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
                enemy.damage(explosion.getDamage());
    
                if (enemy.getHp() <= 0){
                    if (!game.entityDeletionQueue.contains(enemy)){
                        game.entityDeletionQueue.add(enemy);
                    }
                }
            }
        }
        else if (contact.getFixtureA().getUserData() instanceof Enemy){
            Enemy enemy = (Enemy)(contact.getFixtureA().getUserData());
            if (enemy.enemyState.equals("wander")){
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
                game.enemyRotateQueue.add(enemy);
            }
            else if(enemy.enemyState.equals("pursuit")){
                if (contact.getFixtureA().getUserData() instanceof Terrain){
                    enemy.pursuitTimer = System.currentTimeMillis();
                    enemy.enemyState = "wander";
                    game.enemyRotateQueue.add(enemy);
                }
            }
        }
    }

    @Override
    /**
     * ??? official libgdx documentation has no info on this method
     */
    public void postSolve(Contact arg0, ContactImpulse arg1) {}
    @Override
    /**
     * ??? official libgdx documentation has no info on this method
     */
    public void preSolve(Contact arg0, Manifold arg1) {}
}