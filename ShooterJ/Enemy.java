public class Enemy extends Shooter {
    public static int enemyCount = 0;
    
    private long nextShotTime;
    private long nextMoveTime;
    private Vector2 moveTarget;
    
    public Enemy(Vector2 position) {
        super(position);
        color = java.awt.Color.RED;
//        bulletSpeed = 5;
        enemyCount++;
        nextShotTime = 3000 + (long) (Game.random.nextDouble() * 1000) + System.currentTimeMillis();
        nextMoveTime = 1000 + (long) (Game.random.nextDouble() * 1000) + System.currentTimeMillis();
    }
    
    public void update() {
        if(Game.player != null) {
            angle = Math.atan2(Game.player.position.y() - position.y(), 
                               Game.player.position.x() - position.x());
        }
        if(System.currentTimeMillis() >= nextShotTime) {
            nextShotTime = 1000 + (long) (Game.random.nextDouble() * 2000) + System.currentTimeMillis();
            shoot();
        }
        
        if(moveTarget != null) {
            Vector2 toTarget = moveTarget.subtract(position);
            if(toTarget.sqrMagnitude() > moveSpeed * moveSpeed) {
                velocity = toTarget.normalized().scale(moveSpeed);
            }
            else {
                velocity = toTarget;
                moveTarget = null;
                nextMoveTime = 500 + (long) (Game.random.nextDouble() * 1000) + System.currentTimeMillis();
            }
        }
        else if(System.currentTimeMillis() >= nextMoveTime) {
            if(Game.player != null) {
                moveTarget = Game.player.position.add(new Vector2(Game.random.nextDouble() * 100 - 50,
                                                                  Game.random.nextDouble() * 100 - 50));
            }
        }
        
        super.update();
    }
    
    public void die() {
        super.die();
        destroy();
        if(--enemyCount <= 0) Game.showWinScreen();
    }
}