public abstract class Item extends Entity {
    public String icon;
    public double duration = 10000;
    private long startTime;
    
    public Item() {
        super();
        startTime = Game.time;
        setLayer(1);
    }
    
    public Rectangle getRectangle() {
        return new Rectangle(x - 4, y - 4, x + 4, y + 4);
    }
    
    public void update() { 
        Rectangle rect = getRectangle();
        if(rect.intersects(Game.player.getRectangle())) {
            pickedUp();
        }
        
        // The original Casual Quest lets you pick up items 
        // by hitting them with projectiles such as fireballs
//        for(Entity e : Entity.all) if(e instanceof Projectile) {
//            if(rect.intersects(e.getRectangle())) {
//                Projectile p = (Projectile) e;
//                if(p.owner != null && p.owner.equals(Game.player)) {
//                    pickedUp();
//                }
//            }
//        }
        
        if(Game.time - startTime >= duration) {
            destroy();
        }
        else {
            long dt = Game.time - startTime;
            visible = dt < duration - 3000 || dt / 100 % 2 == 0;
        }
    }
    
    public abstract void pickedUp();
    
    public void draw() { StdDraw.picture(x, y, "rsc/items/" + icon + ".png"); }
}