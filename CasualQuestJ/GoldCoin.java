public class GoldCoin extends Item {
    public static int collected = 0;
    
    private boolean pickedUp;
    
    public void pickedUp() { 
        pickedUp = true;
    }
    
    public void update() {
        if(pickedUp) {
            moveTo(Game.lerp(x, -8, .15), Game.lerp(y, -8, .15));
            if(x < -4 && y < -4) {
                collected++;
                destroy();
            }
        }
        else {
            super.update();
        }
    }
    
    public void draw() {
        int frame = 1 + (int) (Game.time / 200.0) % 4;
        StdDraw.picture(x, y, "rsc/items/gold" + frame + ".png");
    }
}