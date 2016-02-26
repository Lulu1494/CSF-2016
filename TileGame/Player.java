public class Player {
    public static double x = 0, y = 0;
    public static double vx = 0, vy = 0;
    public static double moveSpeed = 1;
    public static double speedLerp = 1;
    
    public static Rectangle rect = new Rectangle(-8, -8, 8, 8);
    
    public static String name = "adventurer";
    public static String direction = "south";
    public static String icon;
    public static boolean attacking = false;
    public static boolean casting = false;
    
    public static long attackStartTime, attackEndTime, attackDuration = 400;
    
    public static void changeClass(String newClass) {
        if(name.equals(newClass)) return;
        name = newClass;
        if(newClass == "archer") moveSpeed = 2;
        else moveSpeed = 1;
    }
    
    public static void draw() {
        if(Camera.containsRect(rect)) {
            StdDraw.picture(Camera.worldToScreenX(x), Camera.worldToScreenY(y), "rsc/classes/"+ name +"/" + icon + ".png");
        }
    }
    
    public static void update() {
        if(canAttack()) {
            if(Input.getKeyDown(java.awt.event.KeyEvent.VK_1))      changeClass("adventurer");
            else if(Input.getKeyDown(java.awt.event.KeyEvent.VK_2)) changeClass("knight");
            else if(Input.getKeyDown(java.awt.event.KeyEvent.VK_3)) changeClass("acolyte");
            else if(Input.getKeyDown(java.awt.event.KeyEvent.VK_4)) changeClass("mage");
            else if(Input.getKeyDown(java.awt.event.KeyEvent.VK_5)) changeClass("archer");
            else if(Input.getKeyDown(java.awt.event.KeyEvent.VK_6)) changeClass("orc");
            
            else if(Input.getKeyDown(Input.ATTACK))  attack();
            else if(Input.getKeyDown(Input.SKILL_1)) skill1();
            else if(Input.getKeyDown(Input.SKILL_2)) skill2();
            else if(Input.getKeyDown(Input.SKILL_3)) skill3();
        }
        
        attacking = attackEndTime > TileGame.time;
        
        if(canMove()) {
            int inputX = Input.getAxis(Input.Axis.Horizontal);
            int inputY = Input.getAxis(Input.Axis.Vertical);
            
            vx = GameMath.lerp(vx, moveSpeed * inputX, speedLerp);
            vy = GameMath.lerp(vy, moveSpeed * inputY, speedLerp);
            
            if(inputX > 0)      direction = "east";
            else if(inputX < 0) direction = "west";
            else if(inputY > 0) direction = "north";
            else if(inputY < 0) direction = "south";
        }
        
        if(attacking) {
            icon = "attack " + direction;
        }
        else if(casting) {
            icon = "cast " + direction;
        }
        else {
            long frame = TileGame.time / 800 % 2 + 1;
            icon = direction + frame;
        }
        
        move(vx, vy);
        checkCollision();
    }
    
    public static void freeze() {
        vx = 0;
        vy = 0;
    }
    
    public static boolean canMove() {
        return canAttack();
    }
    
    public static boolean canAttack() {
        return TileGame.time > attackEndTime;
    }
    
    public static void attack() {
        freeze();
        if(!(name.equals("archer") || name.equals("mage"))) {
            attackStartTime = TileGame.time;
            attackEndTime = attackStartTime + attackDuration;
        }
    }
    
    public static void skill1() { }
    public static void skill2() { }
    public static void skill3() { }
    
    public static void moveTo(double x, double y) {
        rect.translate(x - Player.x, y - Player.y);
        Player.x = x;
        Player.y = y;
    }
    
    public static void move(double dx, double dy) {
        moveTo(x + dx, y + dy);
    }
    
    public static void checkCollision() {
        Rectangle a = rect;
        Rectangle[] obstacles = getObstacles();
        for(Rectangle b : obstacles) if(a.intersects(b)) {
            double dx = 0, dy = 0;
            
            if(a.right - b.left > b.right - a.left)
                dx = b.right - a.left;
            else dx = b.left - a.right;
            
            if(a.top - b.bottom > b.top - a.bottom)
                dy = b.top - a.bottom;
            else dy = b.bottom - a.top; 
            
            if(vy != 0 && Math.abs(dx) > Math.abs(dy)) 
                move(0, dy);
            else move(dx, 0);
        }
    }
    
    public static Rectangle[] getObstacles() {
        java.util.Set<Rectangle> obstacles = new java.util.HashSet<Rectangle>();
        int tileX = (int) Math.round(x / TileGame.tilemap.tileWidth);
        int tileY = (int) Math.round(y / TileGame.tilemap.tileHeight);
        int minTileX = (int) GameMath.clamp(tileX - 1, 0, TileGame.tilemap.tileMapWidth);
        int minTileY = (int) GameMath.clamp(tileY - 1, 0, TileGame.tilemap.tileMapHeight);
        int maxTileX = (int) GameMath.clamp(tileX + 1, 0, TileGame.tilemap.tileMapWidth);
        int maxTileY = (int) GameMath.clamp(tileY + 1, 0, TileGame.tilemap.tileMapHeight);
        for(int tx = minTileX; tx < maxTileX; tx++) {
            for(int ty = minTileY; ty < maxTileY; ty++) {
                Tile tile = TileGame.tilemap.tileAt(tx, ty);
                if(tile.dense) {
                    obstacles.add(new Rectangle(tx * TileGame.tilemap.tileWidth, 
                                                ty * TileGame.tilemap.tileHeight,
                                                (tx + 1) * TileGame.tilemap.tileWidth, 
                                                (ty + 1) * TileGame.tilemap.tileHeight));
                }
            }
        }
        return obstacles.toArray(new Rectangle[0]);
    }
}