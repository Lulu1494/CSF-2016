public class Tilemap {
    public final int tileWidth, tileHeight;
    public final int tileMapWidth, tileMapHeight;
    public final int pixelMapWidth, pixelMapHeight;
    public final java.util.Map<String, Tile> mapKey;
    public final String[] mapString;
    public final Tile[] grid;
    
    public Tilemap(int tileWidth, int tileHeight, java.util.Map<String, Tile> mapKey, String[] mapString) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.mapKey = mapKey;
        this.mapString = mapString;
        tileMapHeight = mapString.length;
        tileMapWidth = mapString[0].length() / 2;
        pixelMapWidth = tileWidth * tileMapWidth;
        pixelMapHeight = tileHeight * tileMapHeight;
        
        grid = new Tile[coordToIndex(tileMapWidth, tileMapHeight)];
        
        double startX = 0, startY = 0;
        for(int x = 0; x < tileMapWidth; x++) {
            int pos = x * 2, afterPos = pos + 2;
            for(int y = 0; y < tileMapHeight; y++) {
                String tileID = mapString[tileMapHeight - 1 - y].substring(pos, afterPos);
                Tile tile = mapKey.get(tileID);
                if(tile == null) System.out.println("Unrecognized tile ID (" + tileID + ").");
                setTile(x, y, tile);
            }
        }
    }
    
    public int coordToIndex(int x, int y) {
        return x + y * tileMapWidth;
    }
    
    public Tile tileAt(int x, int y) {
        return grid[coordToIndex(x, y)];
    }
    
    public void setTile(int x, int y, Tile t) {
        grid[coordToIndex(x, y)] = t;
    }
    
    public void draw() {
        if(mapString.length == 0) return;
        int camMinTileX = Camera.getMinTileX();
        int camMinTileY = Camera.getMinTileY();
        int camMaxTileX = Camera.getMaxTileX();
        int camMaxTileY = Camera.getMaxTileY();
        for(int x = camMinTileX; x < camMaxTileX; x++) {
            for(int y = camMinTileY; y < camMaxTileY; y++) {
                tileAt(x, y).draw(x, y);
                TileGame.drawnTiles++;
            }
        }
    }
}