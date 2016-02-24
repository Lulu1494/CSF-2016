public class Player extends Shooter {
    public double fireRate = 15; // shots per second
    private long nextShotTime;
    
    public double speedLerp = .3;
    
    public Player(Vector2 position) {
        super(position);
        color = java.awt.Color.BLUE;
    }
    
    private boolean canShoot() {
        return System.currentTimeMillis() >= nextShotTime;
    }
    
    public double getGunAngle() {
        return angle + (Game.random.nextDouble() * 5 - 2.5) * Math.PI / 180;
    }
    
    public void update() {
        Vector2 input = new Vector2(
            (StdDraw.isKeyPressed('d')?1:0)-(StdDraw.isKeyPressed('a')?1:0),
            (StdDraw.isKeyPressed('w')?1:0)-(StdDraw.isKeyPressed('s')?1:0));
        
        if(input.x() != 0 && input.y() != 0) input = input.normalized();
        
        velocity = Vector2.lerp(velocity, input.multiply(moveSpeed), speedLerp);
        
        angle = Math.atan2(StdDraw.mouseY() - position.y(), 
                           StdDraw.mouseX() - position.x());
        
        super.update();
        
        if(StdDraw.mousePressed() && canShoot()) {
            nextShotTime = (long) (1000 / fireRate) + System.currentTimeMillis();
            shoot();
        }
    }
    
    public void die() {
        Game.showDeathScreen();
    }
}