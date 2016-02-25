public class Projectile extends Entity {
    public Entity owner;
    
    public double moveSpeed;
    public double angle;
    public double damage;
    public String icon;
    public int iconFrames = 2;
    public int iconDelay = 400;
    public int lifetime;
    
    public boolean hasCollision = true;
    
    private long startTime;
    
    public void draw() { 
        String fullIcon;
        if(iconFrames > 0) {
            int frame = 1 + (int) (Game.time / iconDelay % iconFrames);
            fullIcon = "rsc/weapons/" + icon + frame + ".png";
        }
        else fullIcon = "rsc/weapons/" + icon + ".png";
        StdDraw.picture((int) x, (int) y, fullIcon, Math.toDegrees(angle)); 
    }
    
    public Projectile() {
        super();
        setLayer(4);
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
        if(hasCollision) {
            Rectangle rect = getRectangle();
            for(Entity entity : Entity.all) {
                if(rect.intersects(entity.getRectangle())) {
                    if(entity instanceof Enemy) {
                        hitEnemy((Enemy) entity);
                        break;
                    }
                }
            }
        }
        if(Game.time - startTime > lifetime) destroy();
    }
    
    public void hitEnemy(Enemy enemy) {
        enemy.takeDamage(damage);
        enemy.knockback(this, 16);
        destroy();
    }
}