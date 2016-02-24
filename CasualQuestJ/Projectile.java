public class Projectile extends Entity {
    public Entity owner;
    
    public double moveSpeed;
    public double angle;
    public double damage;
    public String icon;
    public int lifetime;
    
    private long startTime;
    
    public void draw() { 
        String fullIcon = "rsc/weapons/"+icon+"1.png";
        if(Game.time / 200 % 2 == 0) fullIcon = "rsc/weapons/"+icon+"2.png";
        StdDraw.picture((int) x, (int) y, fullIcon, Math.toDegrees(angle)); 
    }
    
    public Projectile() {
        super();
        defaultStats();
        startTime = Game.time;
    }
    
    void defaultStats() { }
    
    public void setDir(int dir) {
        angle = Game.dirToAngle(dir);
        vx = moveSpeed * Math.cos(angle);
        vy = moveSpeed * Math.sin(angle);
    }
    
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