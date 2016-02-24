public class Bullet extends Entity {
    private double damage;
    private Shooter shooter;
    
    public Bullet(Vector2 position, double damage, double speed, double angle) { 
        super(position); 
        this.damage = damage;
        velocity = Vector2.fromPolar(speed, angle);
    }
    
    public void setShooter(Shooter s) { shooter = s; }
    
    public void update() {
        super.update();
        
        double x = position.x(), y = position.y();
        if(x <= 0 || x >= Game.WIDTH || y <= 0 || y >= Game.HEIGHT) {
            destroy();
        }
        else {
            for(Entity e : Game.entities) {
                if(!e.equals(shooter) && e instanceof Shooter) {
                    
                    // don't let enemies shoot other enemies
                    if(shooter instanceof Enemy && e instanceof Enemy) continue;
                    
                    Shooter s = (Shooter) e;
                    if(isInside(s)) {
                        s.takeDamage(damage);
                        destroy();
                    }
                }
            }
        }
    }
    
    private boolean isInside(Shooter s) {
        return s.position.subtract(position).sqrMagnitude() <= s.radius * s.radius;
    }
    
    public void draw() {
        StdDraw.setPenColor(java.awt.Color.YELLOW);
        StdDraw.filledCircle(position.x(), position.y(), 8);
    }
}