public class Maps {
    public static Tilemap[] tilemaps;
    public static String[] regions = { 
        "castle", "forest", "cave", "desert", 
        "graveyard", "hell", "swamp", "pagota", "ice" };
    
    public static void pickRandomMap() {
        java.util.Random r = new java.util.Random();
        TileGame.tilemap = tilemaps[r.nextInt(tilemaps.length)];
        Tile.Regional.REGION = regions[r.nextInt(regions.length)];
    }
    
    public static void load() {
        java.util.Map<String, Tile> key = new java.util.HashMap<String, Tile>();
        key.put(". ", new Tile.Regional("floor", false));
        key.put("==", new Tile.Regional("bridge_hor", false));
        key.put("||", new Tile.Regional("bridge_ver", false));
        key.put("ii", new Tile.Regional("pillar", true));
        key.put("[]", new Tile.Regional("wall", true));
        key.put("w0", new Tile.Regional("water_0", true));
        key.put("w1", new Tile.Regional("water_1", true));
        key.put("w2", new Tile.Regional("water_2", true));
        key.put("w4", new Tile.Regional("water_4", true));
        key.put("w5", new Tile.Regional("water_5", true));
        key.put("w6", new Tile.Regional("water_6", true));
        key.put("w8", new Tile.Regional("water_8", true));
        key.put("w9", new Tile.Regional("water_9", true));
        key.put("wA", new Tile.Regional("water_10", true));
        
        tilemaps = new Tilemap[] {
            new Tilemap(16, 16, key, new String[] {
                    "[][][][][][]. . . [][][][][][]",
                    "[][][][][][]. . . [][][][][][]",
                    "[][][][][][]. . . [][][][][][]",
                    "[][][][][]. . . . [][][][][][]",
                    "[][][]. . . . . . [][][][][][]",
                    "[][][]. . . . . . [][][][][][]",
                    "[][]. . . . . . . . . . . . []",
                    "[]. . . . . . . . . . ii. . []",
                    ". . . . . . . . . . . . . . . ",
                    ". . . . . . . . . . . . . . . ",
                    ". . . . . . . . . . . . . . . ",
                    "[]. . ii. . . . . . . ii. . []",
                    "[]. . . . . . . . . . . . . []",
                    "[][][][][]. . . . . [][][][][]",
                    "[][][][][]. . . . . [][][][][]"
            }), 
                new Tilemap(16, 16, key, new String[] {
                    "w4. . . . . wAw0w0w0w4. . w8w0",
                    "w0w5. . . . . w8w0w0w4. . wAw0",
                    "w0w0w5. . . w9w0w2w2w4. . . w8",
                    "w0w0w0||||w1w0w6. . ==. . . w8",
                    "w0w0w0||||w0w4. . . ==. . w9w0",
                    "w0w0w6. . w8w4. . . wAw1w1w0w0",
                    "w0w4. . . ====. . . . wAw0w0w0",
                    "w0w4. . . w8w0w5. . . . wAw0w0",
                    "w0w4. . . w8w0w4. . . . . wAw0",
                    "w0w2||w1w1w0w0w0w5. . . . . wA",
                    "w4. . w8w0w2w2w2w2w1w5. . . . ",
                    "w4. . ====. . . . wAw4. . . . ",
                    "w0w1w1w0w4. . . . . ==. . . w9",
                    "w0w0w0w0w4. . . . . ==. . w9w0",
                    "w0w0w0w0w0w5. . . w9w0w1w1w0w0"
            }),
                new Tilemap(16, 16, key, new String[] {
                    "[][][][][][]. . . [][][][][][]",
                    "[][][]. [][]. . . [][][][][][]",
                    "[][]. ii[][]. . . []ii[]. [][]",
                    "[]. . iiiiii. . . iiii[]. . []",
                    "[]. . iiiiii. . . iiii[]. . []",
                    "[]. . . . . . . . . . . . . []",
                    "[]. . . . . . . . . . . . . . ",
                    "[]. . . ii. . . . . . . . . . ",
                    "[]. . ii. . . . . . . . . . . ",
                    "[]. . . . . . . . . . . . . . ",
                    "[]. . . . . . . . . . . . . . ",
                    "[]. . ii. . . . . . . . . . . ",
                    "[]. . ii. . . . . . . . . . []",
                    "[]. . . . . . . . . . . . . []",
                    "[][][][][][]. . . [][][][][][]"
            })
        };
    }
}