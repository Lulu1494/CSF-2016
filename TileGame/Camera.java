public class Camera {
    private static double x, y;
    public static Rectangle rect;
    
    public static double getX() { return x; }
    public static double getY() { return y; }
    
    public static boolean containsRect(Rectangle r) {
        return rect.intersects(r);
    }
    
    public static void moveTo(double x, double y) {
        rect.translate(x - Camera.x, y - Camera.y);
        Camera.x = x;
        Camera.y = y;
    }
    
    public static void move(double dx, double dy) {
        moveTo(x + dx, y + dy);
    }
    
    public static double worldToScreenX(double x) {
        return x - Camera.x + Camera.halfWidth;
    }
    
    public static double worldToScreenY(double y) {
        return y - Camera.y + Camera.halfHeight;
    }
    
    public static int getMinTileX() {
        return (int) Math.min(Math.max(Math.floor((x - halfWidth) / TileGame.tilemap.tileWidth), 0), TileGame.tilemap.tileMapWidth);
    }
    
    public static int getMinTileY() {
        return (int) Math.min(Math.max(Math.floor((y - halfHeight) / TileGame.tilemap.tileHeight), 0), TileGame.tilemap.tileMapHeight);
    }
    
    public static int getMaxTileX() {
        return (int) Math.min(Math.max(Math.ceil((x + halfWidth) / TileGame.tilemap.tileWidth), 0), TileGame.tilemap.tileMapWidth);
    }
    
    public static int getMaxTileY() {
        return (int) Math.min(Math.max(Math.ceil((y + halfHeight) / TileGame.tilemap.tileHeight), 0), TileGame.tilemap.tileMapHeight);
    }
    
    private static double zoom;
    public static double getZoom() { return zoom; }
    
    private static int width, height;
    private static int halfWidth, halfHeight;
    public static int getWidth() { return width; }
    public static int getHeight() { return height; }
    public static int getHalfWidth() { return halfWidth; }
    public static int getHalfHeight() { return halfHeight; }
    
    public static void setSize(int width, int height, double zoom) {
        Camera.width = width;
        Camera.height = height;
        halfWidth = width / 2;
        halfHeight = height / 2;
        Camera.zoom = zoom;
        Camera.rect = new Rectangle(-halfWidth, -halfHeight, halfWidth, halfHeight);
        rect.translate(x, y);
        StdDraw.setCanvasSize(width, height, zoom);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
    }
}