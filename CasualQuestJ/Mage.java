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
            Fireball f = new Fireball();
            double angle = Game.dirToAngle(dir);
            f.moveTo(x + 11*Math.cos(angle), y + 11*Math.sin(angle));
            f.setDir(dir);
        }
    }
    
    void attackUpdate() {
        super.attackUpdate();
        if(attackFrame == 1) {
            
        } else if(attackFrame >= 10) {
            attackEnd();
        }
    }
    
    void skill1() {
        if(spendAura(2)) {
            super.attack();
            BigFireball f = new BigFireball();
            double angle = Game.dirToAngle(dir);
            f.moveTo(x + 11*Math.cos(angle), y + 11*Math.sin(angle));
            f.setDir(dir);
        }
    }
    
    public class BigFireball extends Entity {
        int lifetime = 5000;
        double moveSpeed = 128;
        double angle = 0;
        double damage = 2;
        String icon = "bigFire";
        
        public void setDir(int dir) {
            angle = Game.dirToAngle(dir);
            vx = moveSpeed * Math.cos(angle);
            vy = moveSpeed * Math.sin(angle);
        }
        
        public void draw() { 
            String fullIcon = "rsc/weapons/"+icon+"1.png";
            if(Game.time / 200 % 2 == 0) fullIcon = "rsc/weapons/"+icon+"2.png";
            StdDraw.picture(x, y, fullIcon, Math.toDegrees(angle)); 
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
            if(--lifetime <= 0) destroy();
        }
    }
    
    public class Fireball extends BigFireball {
        public Fireball() { super(); 
            damage = 1; 
            icon = "fireball";
        }
        public Rectangle getRectangle() { return new Rectangle(x-3, y-3, x+3, y+3); }
    }
}