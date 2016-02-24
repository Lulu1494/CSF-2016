import java.awt.Color;
import java.awt.Font;

public class Shooter extends Entity {
    public double health = 10;
    
    public double angle = 0;
    public double radius = 16;
    public Color color;
    public Color gunColor = new Color(32, 32, 32);
    public Color hitColor = Color.WHITE;
    public long hitTime = 100;
    private long lastHitTime;
    
    public double bulletSpeed = 15;
    public double bulletDamage = 1;
    public double gunLength = 24;
    
    public double moveSpeed = 5;
    
    public Shooter(Vector2 position) { super(position); }
    
    public void update() {
        super.update();
        double x = position.x(), y = position.y();
        x = Math.min(Math.max(x, radius), Game.WIDTH - radius);
        y = Math.min(Math.max(y, radius), Game.HEIGHT - radius);
        position = new Vector2(x, y);
    }
    
    public void draw() {
        StdDraw.setPenColor(gunColor);
        StdDraw.setPenRadius(16 / (double) Game.WIDTH);
        Vector2 aim = Vector2.fromPolar(1, angle);
        Vector2 gunStart = position.add(aim.scale(radius));
        Vector2 gunEnd = gunStart.add(aim.scale(gunLength));
        StdDraw.line(gunStart.x(), gunStart.y(), gunEnd.x(), gunEnd.y());
        
        StdDraw.setPenRadius(1 / (double) Game.WIDTH);
        StdDraw.setPenColor(System.currentTimeMillis() - lastHitTime <= hitTime 
                                ? hitColor : color);
        StdDraw.filledCircle(position.x(), position.y(), radius);
        
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        StdDraw.text(position.x(), position.y(), String.valueOf((int) health));
    }
    
    public void shoot() {
//        StdAudio.play("shoot.wav");
        Bullet b = getNewBullet(getMuzzlePosition(), 
                                bulletDamage, 
                                bulletSpeed, 
                                getGunAngle());
        b.setShooter(this);
    }
    
    public double getGunAngle() {
        return angle;
    }
    
    public Bullet getNewBullet(Vector2 position, double damage, double speed, double angle) {
        return new Bullet(position, damage, speed, angle);
    }
    
    public Vector2 getMuzzlePosition() {
        return position.add(Vector2.fromPolar(radius + gunLength, angle));
    }
    
    public void takeDamage(double damage) {
        lastHitTime = System.currentTimeMillis();
        health -= damage;
        if(health <= 0) { die(); }
    }
    
    public void die() { health = 0; }
}