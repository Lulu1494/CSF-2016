public class Adventurer extends Player {
    String swordIcon;
    
    void defaultStats() {
        icon = "adventurer";
        swordIcon = "sword";
        moveSpeed = 32;
        attackDamage = 1;
        initializeHealth(4);
    }
    
    public void draw() {
        if(attacking) drawSword();
        super.draw();
    }
    
    private double swordTipDistance;
    private String fullSwordIcon;
    void drawSword() {
        double angle = Game.dirToAngle(dir);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        StdDraw.picture(Math.round(x + 16 * cos), 
                        Math.round(y + 16 * sin) + (dir == Game.WEST ? -1 : 0), 
                        fullSwordIcon, 
                        Math.toDegrees(angle));
    }
    
    void attackUpdate() {
        super.attackUpdate();
        if(attackFrame >= 12) { 
            attackEnd();
        } else {
            int swordFrame;
            if(attackFrame <= 2) {
                swordFrame = 1;
                swordTipDistance = 6;
            } else if(attackFrame <= 4) {
                swordFrame = 2;
                swordTipDistance = 12;
            } else if(attackFrame <= 6) {
                swordFrame = 3;
                swordTipDistance = 16;
            } else if(attackFrame <= 8) {
                swordFrame = 2;
                swordTipDistance = 12;
            } else {
                swordFrame = 1;
                swordTipDistance = 6;
            }
            swordTipDistance += radius;
            fullSwordIcon = "rsc/weapons/" + swordIcon + swordFrame + ".png";

            Rectangle swordRect = null;
            switch(dir) {
                case Game.NORTH: swordRect = new Rectangle(x - 2, y + 8, x + 2, y + swordTipDistance); break;
                case Game.SOUTH: swordRect = new Rectangle(x - 2, y - swordTipDistance, x + 2, y - 8); break;
                case Game.EAST:  swordRect = new Rectangle(x + 8, y - 2, x + swordTipDistance, y + 2); break;
                case Game.WEST:  swordRect = new Rectangle(x - swordTipDistance, y - 2, x - 8, y + 2); break;
            }
            for(Entity entity : Entity.all) {
                if(entity instanceof Enemy) {
                    if(entity.getRectangle().intersects(swordRect)) {
                        Enemy enemy = (Enemy) entity;
                        enemy.knockback(this, 16);
                        enemy.takeDamage(attackDamage);
                    }
                }
                else if(entity instanceof Item) {
                    if(entity.getRectangle().intersects(swordRect)) {
                        Item item = (Item) entity;
                        item.pickedUp();
                    }
                }
            }
        }
    }
}