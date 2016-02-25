public class Mage extends Player {
    void defaultStats() {
        moveSpeed = 32;
        icon = "mage";
        auraRegenRate = 1;
        initializeHealth(4);
        initializeAura(4);
    }
    
    void attack() {
        cast(200);
        launchProjectile(new Fireball());
    }
    
    void attackUpdate() {
        super.attackUpdate();
        if(attackFrame >= 10) attackEnd();
    }
    
    void skill1() {
        if(spendAura(2)) {
            cast(300);
            launchProjectile(new BigFireball());
        }
    }
    
    void skill2() {
        if(spendAura(3)) {
            cast(200);
            launchProjectile(new Seeker());
        }
    }
    
    void skill3() {
        if(spendAura(4)) {
            cast(3000);
            int balls = 12;
            for(int i = 0; i < balls; i++) {
                double angle = Math.toRadians(i*360/balls);
                BigFireball f = new BigFireball();
                f.moveTo(x + 6*Math.cos(angle), y + 6*Math.sin(angle));
                f.lifetime = 500;
                f.moveSpeed = 96;
                f.setAngle(angle);
            }
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
            lifetime = 500;
            damage = 1; 
            setLayer(4);
        }
        
        public Rectangle getRectangle() { 
            return new Rectangle(x-3, y-3, x+3, y+3); 
        }
    }
    
    public class Seeker extends Projectile {
        void defaultStats() {
            icon = "seeker";
            iconFrames = 4;
            iconDelay = 400;
            moveSpeed = 64;
            lifetime = 6000;
            damage = 2;
            setLayer(4);
        }
        
        public Rectangle getRectangle() { 
            return new Rectangle(x-3, y-3, x+3, y+3); 
        }
        
        public void update() {
            Entity closestEnemy = null;
            double distance = 0;
            for(Entity e : Entity.all) {
                if(e instanceof Enemy) {
                    double d = distanceTo(e);
                    if(closestEnemy == null || d < distance) {
                        closestEnemy = e;
                        distance = d;
                    }
                }
            }
            if(closestEnemy != null) {
                double accel = Game.TICKS_PER_SECOND;
                double dx = closestEnemy.x - x;
                double dy = closestEnemy.y - y;
                if(vx <  moveSpeed && dx >  2) vx += accel;
                if(vx > -moveSpeed && dx < -2) vx -= accel;
                if(vy <  moveSpeed && dy >  2) vy += accel;
                if(vy > -moveSpeed && dy < -2) vy -= accel;
            }
            super.update();
        }
    }
}