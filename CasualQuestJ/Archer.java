public class Archer extends Player {
    
    void defaultStats() {
        icon = "archer";
        attackIcon = null;
        castIcon = null;
        moveSpeed = 64;
        attackDamage = 1;
        initializeHealth(4);
    }
    
    private Arrow arrow;
    
    void attack() {
        if(arrow != null) arrow.destroy();
        arrow = (Arrow) launchProjectile(new Arrow());
        arrow.horizontal = (dir & (Game.EAST | Game.WEST)) != 0;
    }
    
    public class Arrow extends Projectile {
        public boolean horizontal;
        
        void defaultStats() {
            icon = "arrow";
            iconFrames = 0;
            moveSpeed = 128;
            lifetime = 6000;
            damage = 1;
        }
        
        public Rectangle getRectangle() {
            if(horizontal) return new Rectangle(x - 8, y - 1, x + 8, y + 1);
            else return new Rectangle(x - 1, y - 8, x + 1, y + 8);
        }
    }
}