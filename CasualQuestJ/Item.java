public abstract class Item extends Entity {
    public String icon;
    public double duration = 10000;
    private long startTime;
    
    public Item() {
        super();
        startTime = Game.time;
    }
    
    public void update() { 
        if(getRectangle().intersects(Game.player.getRectangle())) {
            pickedUp();
        }
        if(Game.time - startTime >= duration) {
            destroy();
        }
        else {
            long dt = Game.time - startTime;
            visible = dt < duration - 3000 || dt / 100 % 2 == 0;
        }
    }
    
    public abstract void pickedUp();
    
    public void draw() { StdDraw.picture(x, y, "rsc/items/" + icon + ".png"); }
}