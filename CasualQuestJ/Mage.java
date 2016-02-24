public class Mage extends Player {
    void defaultStats() {
        moveSpeed = 32;
        icon = "mage";
        auraRegenRate = 1;
        initializeHealth(4);
        initializeAura(4);
    }
    
    void attack() {
        if(spendAura(1)) {
            super.attack();
            launchProjectile(new Fireball());
        }
    }
    
    void attackUpdate() {
        super.attackUpdate();
        if(attackFrame >= 10) attackEnd();
    }
    
    void skill1() {
        if(spendAura(2)) {
            super.attack();
            launchProjectile(new BigFireball());
        }
    }
    
    Projectile launchProjectile(Projectile p) {
        p.owner = this;
        double angle = Game.dirToAngle(dir);
        p.moveTo(x + 11*Math.cos(angle), y + 11*Math.sin(angle));
        p.setDir(dir);
        return p;
    }
    
    public class BigFireball extends Projectile {
        void defaultStats() {
            icon = "bigFire";
            moveSpeed = 128;
            lifetime = 1800;
            damage = 2;
            setLayer(4);
        }
    }
    
    public class Fireball extends Projectile {
        void defaultStats() {
            icon = "fireball";
            moveSpeed = 128;
            lifetime = 700;
            damage = 1; 
            setLayer(4);
        }
        
        public Rectangle getRectangle() { 
            return new Rectangle(x-3, y-3, x+3, y+3); 
        }
    }
}