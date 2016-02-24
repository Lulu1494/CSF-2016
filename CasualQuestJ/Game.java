// CasualQuest by Davidson Lu
// Created 2016 February 23
// Uses StdDraw modified to include isKeyPressed()

// This is a quick prototype/remake of IainPeregrine's Casual Quest found here:
//  http://www.byond.com/hub/iainperegrine/casualquest
// The art and name come straight from that game 
// (I was given the complete source)

// On that same website is this game:
//  http://www.byond.com/hub/f0lak/hazordhu
// F0lak was the designer and artist, but I was the sole programmer. 

import java.util.Random;
import java.awt.Color;
import java.awt.Font;

public class Game {
    public static void main(String[] args) { startGame(); }
    
    
    public static final int 
        NORTH = 1, SOUTH = 2, EAST = 4, WEST = 8, 
        NORTHEAST = NORTH|EAST, NORTHWEST = NORTH|WEST, 
        SOUTHEAST = SOUTH|EAST, SOUTHWEST = SOUTH|WEST;
    
    public static final int WIDTH = 320, HEIGHT = 320;
    public static final double TICKS_PER_SECOND = 60;
    public static final double FRAMES_PER_SECOND = 60;
    public static final double PER_TICK = 1 / TICKS_PER_SECOND;
    public static void setPenRadius(double radius) { StdDraw.setPenRadius(radius / (double) WIDTH); }
    
    public static Color backgroundColor = new Color(0, 128, 0);
    
    public static Player player;
    
    public static Random rand = new Random();
    
    public static long time;
    
    public static int MAX_ENEMY_COUNT = 30;
    public static int ENEMY_SPAWN_DELAY = 0;

    public static int KEY_RESET = java.awt.event.KeyEvent.VK_R;
    public static int KEY_PAUSE = java.awt.event.KeyEvent.VK_P;
    public static boolean pausing = false;
    public static boolean paused = false;
    public static boolean running = true;
    
    private static long lastUpdateTime;
    private static long lastDrawTime;
    
    public static void showTitleScreen() {
        setupWindow(240, 240);
        StdDraw.picture(120, 120, "rsc/title.png");
        StdDraw.show();
        while(!StdDraw.isKeyPressed(' '));
    }
    
    public static void populateMap() {
        for(int i = 0; i < 15; i++) {
            Tree tree = new Tree();
            tree.moveTo(WIDTH*rand.nextDouble(), HEIGHT*rand.nextDouble());
            
            int n = rand.nextInt(5);
            for(int j = 0; j < n; j++) {
                Tree t = new Tree();
                double dist = randn(16, 32);
                double angle = randn(0, 360);
                t.moveTo(tree.x + dist * Math.cos(angle),
                         tree.y + dist * Math.sin(angle));
            }
        }
    }
    
    public static Enemy spawnEnemy(Enemy enemy) {
        switch(rand.nextInt(4)) {
            case 0: enemy.moveTo(WIDTH*rand.nextDouble(), HEIGHT + 16); break;
            case 1: enemy.moveTo(WIDTH*rand.nextDouble(), -16); break;
            case 2: enemy.moveTo(-16, HEIGHT*rand.nextDouble()); break;
            case 3: enemy.moveTo(WIDTH + 16, HEIGHT*rand.nextDouble()); break;
        }
        return enemy;
    }
    
    public static void spawnRandomEnemy() {
        if(prob(95)) {
            Bug bug = (Bug) spawnEnemy(new Bug());
            if(prob(100/3.0)) bug.setType(Bug.Type.BASIC);
            else if(prob(50)) bug.setType(Bug.Type.MEDIUM);
            else bug.setType(Bug.Type.STRONG);
        }
        else spawnEnemy(new Bird());
            
    }
    
    public static void initializePlayer() {
        player = new Adventurer();
        player.moveTo(WIDTH * .5, HEIGHT * .5);
    }
    
    public static void pause() {
        paused = true;
        StdDraw.clear(new Color(0, 0, 0, 128));
        StdDraw.setFont(new Font("Consolas", Font.BOLD, 50));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH*.5, HEIGHT*.5, "paused (p)");
        StdDraw.show();
    }
    
    public static void unpause() { paused = false; }
    
    public static void startGame() {
        showTitleScreen();
        Entity.resetEntities();
        populateMap();
        initializePlayer();
        setupWindow(WIDTH, HEIGHT);
        time = 0;
        running = true;
        long nextRandomEnemyTime = 0;
        long startTime = System.currentTimeMillis();
        while(running) {
            if(!paused) {
                if(Enemy.enemyCount < MAX_ENEMY_COUNT && time > nextRandomEnemyTime) {
                    nextRandomEnemyTime = time + ENEMY_SPAWN_DELAY;
                    spawnRandomEnemy();
                }
                
                if(time - lastUpdateTime >= 1000 / TICKS_PER_SECOND) {
                    lastUpdateTime = time;
                    Entity.updateEntities();
                }
                
                if(time - lastDrawTime >= 1000 / FRAMES_PER_SECOND) {
                    lastDrawTime = time;
                    StdDraw.clear(backgroundColor);
                    Entity.drawEntities();
                }
                
                StdDraw.setFont(new Font("Arial", Font.BOLD, 12));
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.textLeft(0, 6, "Gold: " + GoldCoin.collected);
                StdDraw.textLeft(0, 20, "Enemies: " + MAX_ENEMY_COUNT);
            }
            
            if(!StdDraw.isKeyPressed(KEY_PAUSE)) pausing = false;
            else if(!pausing) {
                pausing = true;
                if(paused) unpause();
                else pause();
            }
            
            if(StdDraw.isKeyPressed(KEY_RESET)) running = false;
            
            time = System.currentTimeMillis() - startTime;
            MAX_ENEMY_COUNT = (int) (Math.pow(time / 3000.0, 1.1));
            StdDraw.show(1);
        }
        startGame();
    }
    
    public static boolean prob(double chance) { return rand.nextDouble() * 100 <= chance; }
    public static double lerp(double a, double b, double t) { return a * (1 - t) + b * t; }
    public static double randn(double low, double high) { return lerp(low, high, rand.nextDouble()); }
    public static double clamp(double n, double min, double max) { return Math.min(Math.max(n, min), max); }
    
    public static void setupWindow(int width, int height) {
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(width  * .05 / 1.1, width  * 1.05 / 1.1);
        StdDraw.setYscale(height * .05 / 1.1, height * 1.05 / 1.1);
    }
    
    private static final double[][] arrowPolygon = {
        {-14, 10,  4,  8, 14, 14,  8,  4, 10, -14}, 
        {  2,  2,  8,  8,  2, -2, -8, -8, -2, -2}};
    public static void drawArrow(double x, double y, int dir) {
        double[][] points = translatePolygon(rotatePolygon(arrowPolygon, dirToAngle(dir)), x, y);
        StdDraw.filledPolygon(points[0], points[1]);
    }
    
    public static double[][] rotatePolygon(double[][] original, double angle) {
        double[][] result = new double[2][original[0].length];
        double cos = Math.cos(angle), sin = Math.sin(angle);
        for(int i = 0; i < original[0].length; i++) {
            result[0][i] = cos * original[0][i] - sin * original[1][i];
            result[1][i] = sin * original[0][i] + cos * original[1][i];
        }
        return result;
    }
    
    public static double[][] translatePolygon(double[][] original, double tx, double ty) {
        double[][] result = new double[2][original[0].length];
        for(int i = 0; i < original[0].length; i++) {
            result[0][i] = original[0][i] + tx;
            result[1][i] = original[1][i] + ty; 
        }
        return result;
    }
    
    private static final int[] dirToAngle = {0, 90, -90, 0, 0, 45, -45, 0, 180, 135, -135}; 
    public static double dirToAngle(int dir) {
        final double deg2rad = Math.PI / 180.0;
        return dirToAngle[dir] * deg2rad;
    }
    
    private static final String[] dirToString = {"none", "north", "south", "northsouth", "east", "northeast", "southeast", "northsoutheast", "west", "northwest", "southwest"};
    public static String dirToString(int dir) { return dirToString[dir]; }
}