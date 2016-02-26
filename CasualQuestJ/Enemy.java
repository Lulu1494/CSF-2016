public class Enemy extends LivingEntity {
    public static int enemyCount = 0;
    public static int enemiesKilled = 0;
    
    public String iconName;
    public double contactDamage;
    
    public double moveSpeed = 16;
    public int dir = Game.SOUTH;
    public double radius = 8;
    
    public Enemy() {
        super();
        enemyCount++;
    }
    
    public void destroy() {
        enemyCount--;
        enemiesKilled++;
        super.destroy();
    }
    
    public void die() {
        if(Game.prob(50)) { // 50% chance of getting nothing
            if(Game.prob(50)) new GoldCoin().moveTo(x, y); // 25%
            else if(Game.prob(50)) new Cherry().moveTo(x, y);   // 12.5%
            else if(Game.prob(50)) new Bottle().moveTo(x, y);   // 6.25%
            else if(Game.prob(50)) new Plum().moveTo(x, y);     // 3.125%
            else if(Game.prob(50)) new Shield().moveTo(x, y);   // 1.5625%
        }
        super.die();
    }
    
    public void update() {        
        if(Game.player.enabled && !Game.player.isInvincible() && getRectangle().intersects(Game.player.getRectangle())) {
            Game.player.takeDamage(contactDamage);
            Game.player.knockback(this, 16);
        }
        super.update();
    }
    
    public void draw() {
        super.draw();
        int frame = 1 + (int) (Game.time / 800.0) % 2;
        String icon = "rsc/enemies/" + iconName + "/" + Game.dirToString(dir) + frame + ".png";
        StdDraw.picture(x, y, icon);
    }
}