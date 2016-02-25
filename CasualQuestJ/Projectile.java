public class Projectile extends Entity {
    public Entity owner;
    
    public double moveSpeed;
    public double angle;
    public double damage;
    public String icon;
    public int iconFrames = 2;
    public int iconDelay = 400;
    public int lifetime;
    
    private long startTime;
    
    public void draw() { 
        int frame = 1 + (int) (Game.time / iconDelay % iconFrames);
        String fullIcon = "rsc/weapons/" + icon + frame + ".png";
        StdDraw.picture((int) x, (int) y, fullIcon, Math.toDegrees(angle)); 
    }
    
    public Projectile() {
        super();
        defaultStats();
        startTime = Game.time;
    }
    
    void defaultStats() { }
    
    public void setAngle(double a) {
        angle = a;
        vx = moveSpeed * Math.cos(angle);
        vy = moveSpeed * Math.sin(angle);
    }
    
    public void setDir(int dir) { setAngle(Game.dirToAngle(dir)); }
    
    public void update() {
        super.update();
        Rectangle rect = getRectangle();
        for(Entity entity : Entity.all) {
            if(entity instanceof Enemy) {
                if(rect.intersects(entity.getRectangle())) {
                    Enemy enemy = (Enemy) entity;
                    enemy.takeDamage(damage);
                    enemy.knockback(this, 16);
                    destroy();
                    break;
                }
            }
        }
        if(Game.time - startTime > lifetime) destroy();
    }
}