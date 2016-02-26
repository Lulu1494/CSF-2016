import java.awt.Color;
import java.awt.Font;

public class TileGame {
    private TileGame() { }
    
    public static long startTime;
    public static long time;
    
    public static Tilemap tilemap;
    
    public static long drawnTiles;
    public static long drawTime;
    public static long updateTime;
    
    private static boolean running = false;
    private static boolean recentering = false;
    public static void main(String[] args) {
        StdDraw.setTitle("Casual Quest");
        Camera.setSize(240, 240, 3);
        Maps.load();
        start();
    }
    
    public static void start() {
        Maps.pickRandomMap();
        Camera.moveTo(tilemap.pixelMapWidth/2, tilemap.pixelMapHeight/2);
        Player.moveTo(tilemap.pixelMapWidth/2, tilemap.pixelMapHeight/2);
        StdDraw.show(0);
        
        double moveSpeed = 5;
        int inputX, inputY;
        double vx = 0, vy = 0;
        double speedLerp = .1;
        double recenterLerp = .3;
        
        startTime = System.currentTimeMillis();
        running = true;
        while(running) {
            time = System.currentTimeMillis() - startTime;
            
            long updateStart = System.nanoTime();
            
            if(Input.getKeyDown(Input.RESET)) Maps.pickRandomMap();
            if(Input.getKeyDown(Input.RECENTER)) recentering = true;
            if(Input.getKeyDown(Input.TOGGLE_HUD)) HUD.toggle();
            if(Input.getKeyDown(Input.TOGGLE_CONTROLS)) HUD.toggleControls();
            
            if(recentering) {
                double
                    targetX = tilemap.pixelMapWidth  / 2,
                    targetY = tilemap.pixelMapHeight / 2,
                    cameraX = Camera.getX(),
                    cameraY = Camera.getY(),
                    dx = targetX - cameraX,
                    dy = targetY - cameraY;
                if(Math.abs(dx) > .5 || Math.abs(dy) > .5) {
                    vx = recenterLerp * dx;
                    vy = recenterLerp * dy;
                }
                else {
                    vx = 0;
                    vy = 0;
                    recentering = false;
                }
            }
            else {
                inputX = Input.getAxis(Input.CAMERA_RIGHT, Input.CAMERA_LEFT);
                inputY = Input.getAxis(Input.CAMERA_UP,    Input.CAMERA_DOWN);
                
                vx = GameMath.lerp(vx, moveSpeed * inputX, speedLerp);
                vy = GameMath.lerp(vy, moveSpeed * inputY, speedLerp);
            }
            Camera.move(vx, vy);
            
            Player.update();
            
            long updateEnd = System.nanoTime();
            
            drawnTiles = 0;
            
            long drawStart = System.nanoTime();
            
            StdDraw.clear(StdDraw.BLACK);
            tilemap.draw();
            Player.draw();
            
            long drawEnd = System.nanoTime();
            
            drawTime = drawEnd - drawStart;
            updateTime = updateEnd - updateStart;
            HUD.draw();
            
            StdDraw.show(33);
        }
    }
    
    public static class HUD {
        public static boolean visible = true;
        public static boolean showControls = false;
        
        public static void toggle() { visible = !visible; }
        public static void toggleControls() { showControls = !showControls; }
        
        private HUD() { }
        
        public static Font timerFont = new Font("Arial", Font.PLAIN, 10);
        
        public static void draw() {
            if(!visible) return;
            
            int width = Camera.getWidth();
            int height = Camera.getHeight();
            
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(timerFont);
            StdDraw.textLeft(1, height - 6, "Time: " + ((long) (TileGame.time * 1e-3 * 100) / 100.0) + "s");
            StdDraw.textLeft(1, height - 16, "Tiles: " + drawnTiles);
            StdDraw.textLeft(1, height - 26, "Draw time: " + ((long) (TileGame.drawTime * 1e-6 * 10000) / 10000.0) + "ms");
            StdDraw.textLeft(1, height - 36, "Update time: " + ((long) (TileGame.updateTime * 1e-6 * 10000) / 10000.0) + "ms");
            StdDraw.textRight(width - 1, 6, "H = Toggle HUD");
            StdDraw.textRight(width - 1, 16, "V = Toggle Controls");
            
            if(showControls) {
                StdDraw.textRight(width - 1, 26, "R = Randomize Map");
                StdDraw.textRight(width - 1, 36, "C = Recenter Camera");
                StdDraw.textRight(width - 1, 46, "1-6 = Change Player Class");
                StdDraw.textRight(width - 1, 56, "WASD = Move Camera");
                StdDraw.textRight(width - 1, 66, "Arrows = Move Player");
            }
        }
    }
}