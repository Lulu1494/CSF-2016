public abstract class Tile {
    public boolean dense;
    
    public abstract String getIcon();
    
    public void draw(int tileX, int tileY) {
        StdDraw.picture(Camera.worldToScreenX(TileGame.tilemap.tileWidth * (tileX + .5)), 
                        Camera.worldToScreenY(TileGame.tilemap.tileHeight * (tileY + .5)), getIcon());
    }
    
    public static class Regional extends Tile {
        public static String REGION = "forest";
        public String name;
        
        public Regional(String name, boolean dense) {
            this.name = name;
            this.dense = dense;
        }
        
        public String getIcon() { 
            return "rsc/regions/" + REGION + "/" + name + ".png"; 
        }
        
        public void draw(int tileX, int tileY) {
            boolean inHell = REGION.equals("hell");
            boolean isPillar = name.equals("pillar") || name.equals("pillar1") || name.equals("pillar2");
            
            if(isPillar && !inHell) {
                name = "pillar";
            }
            
            // floor is drawn beneath pillars :|
            if(isPillar || name.equals("wall")) {
                new Regional("floor", false).draw(tileX, tileY);
            }
            
            // pillars in hell are animated :X
            if(isPillar && inHell) {
                name = "pillar" + (TileGame.time / 800 % 2 + 1);
            }
            
            super.draw(tileX, tileY);
        }
    }
}