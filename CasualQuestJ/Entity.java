import java.util.HashSet;

public abstract class Entity {
    public static HashSet<Entity> all = new HashSet<Entity>();
    public static HashSet<Entity> newEntities = new HashSet<Entity>();
    public static HashSet<Entity> removedEntities = new HashSet<Entity>();
    
    public static void updateEntities() {
        if(!newEntities.isEmpty()) {
            all.addAll(newEntities);
            newEntities.clear();
        }
        for(Entity e : all) {
            e.update();
            if(!e.enabled) removedEntities.add(e);
        } 
        if(!removedEntities.isEmpty()) {
            all.removeAll(removedEntities);
            removedEntities.clear();
        }
    }
    
    public static void drawEntities() { for(Entity e : all) if(e.enabled && e.visible) e.draw(); }
    
    public static void resetEntities() {
        all.clear();
        newEntities.clear();
        removedEntities.clear();
    }
                                                            
    public boolean enabled = true;
    public boolean visible = true;
    
    // by default, everything is a 16x16 square
    public Rectangle getRectangle() { return new Rectangle(x - 8, y - 8, x + 8, y + 8); }
    
    public Entity() { newEntities.add(this); }
    public void destroy() { enabled = false; }
    
    public double x, y, vx, vy;
    
    public abstract void draw();
    
    public void update() { physicsUpdate(); }
    public void physicsUpdate() { 
        double dt = Game.PER_TICK;
        move(vx * dt, vy * dt); 
    }
    
    public void moveTo(double nx, double ny) { x = nx; y = ny; }
    public void move(double dx, double dy) { moveTo(x + dx, y + dy); }
    
    public double distanceTo(Entity other) { return Math.hypot(other.x - x, other.y - y); }
}