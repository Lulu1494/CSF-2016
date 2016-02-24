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

            double angle = Game.dirToAngle(dir);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double tipX = x + cos * swordTipDistance;
            double tipY = y + sin * swordTipDistance;
            for(Entity entity : Entity.all) {
                if(entity instanceof Enemy) {
                    if(entity.getRectangle().containsPoint(tipX, tipY)) {
                        Enemy enemy = (Enemy) entity;
                        enemy.knockback(this, 16);
                        enemy.takeDamage(attackDamage);
                    }
                }
                else if(entity instanceof Item) {
                    if(entity.getRectangle().containsPoint(tipX, tipY)) {
                        Item item = (Item) entity;
                        item.pickedUp();
                    }
                }
            }
        }
    }
}