public class Bird extends Enemy {
    void defaultStats() {
        iconName = "bird";
        initializeHealth(1);
        contactDamage = 1;
        moveSpeed = 48;
        vx = moveSpeed * (Game.prob(50) ? -1 : 1);
        vy = moveSpeed * (Game.prob(50) ? -1 : 1);
    }
    
    public void update() {
        if(x <= 8) vx = Math.abs(vx);
        else if(x >= Game.WIDTH - 8) vx = -Math.abs(vx);
        if(y <= 8) vy = Math.abs(vy);
        else if(y >= Game.HEIGHT - 8) vy = -Math.abs(vy);
        dir = vx > 0 ? Game.EAST : Game.WEST;
        super.update();
    }
}