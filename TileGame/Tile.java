public abstract class Tile {
    public abstract String getIcon();
    
    public void draw(int tileX, int tileY) {
        StdDraw.picture(Camera.worldToScreenX(TileGame.tilemap.tileWidth * (tileX + .5)), 
                        Camera.worldToScreenY(TileGame.tilemap.tileHeight * (tileY + .5)), getIcon());
    }
    
    public static class Wall extends Tile {
        public String getIcon() { return null; }
        
        public void draw(int tileX, int tileY) {
            StdDraw.setPenColor(java.awt.Color.GRAY);
            StdDraw.filledRectangle(Camera.worldToScreenX(TileGame.tilemap.tileWidth * (tileX + .5)), 
                                    Camera.worldToScreenY(TileGame.tilemap.tileHeight * (tileY + .5)), 
                                    TileGame.tilemap.tileWidth / 2, 
                                    TileGame.tilemap.tileHeight / 2);
        }
    }
    
    public static class Floor extends Tile {
        public String getIcon() { return null; }
        
        public void draw(int tileX, int tileY) {
            StdDraw.setPenColor(new java.awt.Color(200, 200, 200));
            StdDraw.filledRectangle(Camera.worldToScreenX(TileGame.tilemap.tileWidth * (tileX + .5)), 
                                    Camera.worldToScreenY(TileGame.tilemap.tileHeight * (tileY + .5)), 
                                    TileGame.tilemap.tileWidth / 2, 
                                    TileGame.tilemap.tileHeight / 2);
        }
    }
    
    public static class Start extends Tile {
        public String getIcon() { return null; }
        
        public void draw(int tileX, int tileY) {
            StdDraw.setPenColor(new java.awt.Color(200, 200, 200));
            StdDraw.filledRectangle(Camera.worldToScreenX(TileGame.tilemap.tileWidth * (tileX + .5)), 
                                    Camera.worldToScreenY(TileGame.tilemap.tileHeight * (tileY + .5)), 
                                    TileGame.tilemap.tileWidth / 2, 
                                    TileGame.tilemap.tileHeight / 2);
            
            StdDraw.setPenColor(new java.awt.Color(192, 192, 192));
            StdDraw.filledRectangle(Camera.worldToScreenX(TileGame.tilemap.tileWidth * (tileX + .5)), 
                                    Camera.worldToScreenY(TileGame.tilemap.tileHeight * (tileY + .5)), 
                                    TileGame.tilemap.tileWidth / 2, 
                                    TileGame.tilemap.tileHeight / 2);
        }
    }
}