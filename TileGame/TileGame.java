public class TileGame {
    private TileGame() { }
    
    public static long startTime;
    public static long time;
    public static Tilemap tilemap;
    
    public static long drawnTiles;
    public static long drawTime;
    
    public static void main(String[] args) {
        Camera.setSize(320, 320, 2);
        loadMap();
        start();
    }
    
    public static void loadMap() {
        java.util.Map<String, Tile> tileMapKey = new java.util.HashMap<String, Tile>();
        tileMapKey.put("##", new Tile.Wall());
        tileMapKey.put(". ", new Tile.Floor());
        tileMapKey.put("<>", new Tile.Start());
        tilemap = new Tilemap(16, 16, tileMapKey, new String[] {
                "##########################################################",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. <>. . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##. . . . . . . . . . . . . . . . . . . . . . . . . . . ##",
                "##########################################################"});
    }
    
    public static void start() {
        Camera.setPosition((tilemap.startX + .5) * tilemap.tileWidth, 
                           (tilemap.startY + .5) * tilemap.tileHeight);
        StdDraw.show(0);
        startTime = System.currentTimeMillis();
        double moveSpeed = 2;
        while(true) {
            time = System.currentTimeMillis() - startTime;
            
            Camera.setPosition(Camera.getX() + moveSpeed * Input.getAxis(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.KeyEvent.VK_LEFT), 
                               Camera.getY() + moveSpeed * Input.getAxis(java.awt.event.KeyEvent.VK_UP,    java.awt.event.KeyEvent.VK_DOWN));
            
            StdDraw.clear(StdDraw.BLACK);
            drawnTiles = 0;
            long drawStart = System.nanoTime();
            tilemap.draw();
            long drawEnd = System.nanoTime();
            drawTime = drawEnd - drawStart;
            HUD.draw();
            StdDraw.show(17);
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