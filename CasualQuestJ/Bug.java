public class Bug extends Enemy {
    public enum Type { BASIC, MEDIUM, STRONG }
    
    public void update() {
        if(Game.prob(1)) randomizeDir();
        if(x >= Game.WIDTH - 8) dir = Game.WEST;
        else if(x <= 8) dir = Game.EAST;
        if(y >= Game.HEIGHT - 8) dir = Game.SOUTH;
        else if(y <= 8) dir = Game.NORTH;
        
        vx = moveSpeed * Math.cos(Game.dirToAngle(dir));
        vy = moveSpeed * Math.sin(Game.dirToAngle(dir));
        
        super.update();
    }
    
    public void setType(Type type) {
        switch(type) {
            case STRONG: 
                initializeHealth(4);
                contactDamage = 2;
                iconName = "strong bug";
                break;
            
            case MEDIUM:
                initializeHealth(2);
                contactDamage = 1;
                iconName = "medium bug";
                break;
            
            case BASIC:
                initializeHealth(1);
                contactDamage = 1;
                iconName = "basic bug";
                break;
        }
    }
    
    public void randomizeDir() {
        int[] dirs = {1, 2, 4, 8};
        dir = dirs[Game.rand.nextInt(4)];
    }
    
}