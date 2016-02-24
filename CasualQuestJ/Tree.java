public class Tree extends Entity {
    public String icon = "rsc/tree.png";
    
    public Tree() {
        super();
        icon = Game.prob(50) ? "rsc/tree.png" : "rsc/tree2.png";
    }
    
    public void moveTo(double newX, double newY) {
        super.moveTo(newX, newY);
        setLayer(1 - newY / Game.HEIGHT);
    }
    
    public void update() {
        for(Entity entity : Entity.all) {
            if(entity instanceof LivingEntity) {
                if(entity instanceof Bird) continue;
                
                double dx = entity.x - x;
                double dy = entity.y - y;
                if(Math.abs(dx) < 16 || Math.abs(dy) < 16) {
                    double d = Math.hypot(dx, dy);
                    if(d <= 16) {
                        double s = (16 - d) / d;
                        entity.move(s*dx, s*dy);
                    }
                }
            }
        }
    }
    public void draw() {
        StdDraw.picture(x, y, icon);
    }
}