public class TileGame {
    private TileGame() { }
    
    public static long startTime;
    public static long time;
    
    public static Tilemap tilemap;
    
    public static long drawnTiles;
    public static long drawTime;
    
    private static boolean running = false;
    private static boolean resetting = false;
    public static void main(String[] args) {
        Camera.setSize(240, 240, 3);
        Maps.load();
        do start(); while(resetting);
    }
    
    public static void start() {
        Maps.pickRandomMap();
        Camera.moveTo(tilemap.pixelMapWidth/2, tilemap.pixelMapHeight/2);
        StdDraw.show(0);
        
        double moveSpeed = 2;
        
        startTime = System.currentTimeMillis();
        running = true;
        while(running) {
            time = System.currentTimeMillis() - startTime;
            
            Camera.move(moveSpeed * Input.getAxis(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.KeyEvent.VK_LEFT),
                        moveSpeed * Input.getAxis(java.awt.event.KeyEvent.VK_UP, java.awt.event.KeyEvent.VK_DOWN));
            
            StdDraw.clear(StdDraw.BLACK);
            drawnTiles = 0;
            long drawStart = System.nanoTime();
            tilemap.draw();
            long drawEnd = System.nanoTime();
            drawTime = drawEnd - drawStart;
            HUD.draw();
            StdDraw.show(17);
            
            if(!StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_R)) resetting = false;
            else if(!resetting) {
                resetting = true;
                Maps.pickRandomMap();
            }
        }
    }
    
    public static class HUD {
        private HUD() { }
        
        public static java.awt.Font timerFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
        
        public static void draw() {
            int height = Camera.getHeight();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(timerFont);
            StdDraw.textLeft(1, height - 10, "Time: " + ((long) (TileGame.time * 1e-3 * 100) / 100.0) + "s");
            StdDraw.textLeft(1, height - 20, "Tiles: " + drawnTiles);
            StdDraw.textLeft(1, height - 30, "Draw time: " + ((long) (TileGame.drawTime * 1e-6 * 10000) / 10000.0) + "ms");
        }
    }
}