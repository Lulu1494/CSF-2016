// Shooter Game by Davidson Lu
// created 2016 Feb 22

// I tried adding shooting sounds, but the game would freeze when too many sounds are played at once

import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.Random;

public class Game {
    public static final int WIDTH  = 1280;
    public static final int HEIGHT = 720;
    public static Color backgroundColor = new Color (0, 128, 64, 224);
    public static Random random = new Random ();
    
    public static HashSet<Entity> entities, 
        destroyedEntities, 
        createdEntities;
    
    public static Player player;
    
    public static void main(String[] args) {
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        
        // undo the 10% margin
        StdDraw.setXscale(WIDTH  * .05/1.1, WIDTH  * 1.05/1.1);
        StdDraw.setYscale(HEIGHT * .05/1.1, HEIGHT * 1.05/1.1);
        
        startGame();
    }
    
    public static void startGame() {
        showTitleScreen();
        
        entities = new HashSet<Entity> ();
        destroyedEntities = new HashSet<Entity> ();
        createdEntities = new HashSet<Entity> ();
        
        for(int i = 0; i < 5; i++) {
            new Enemy (new Vector2 (WIDTH  * random.nextDouble(),
                                    HEIGHT * random.nextDouble()));
        }
        
        player = new Player (new Vector2 (WIDTH / 2.0, HEIGHT / 2.0));
        
        while(true) {
            // update all entities
            if(!entities.isEmpty()) for(Entity e : entities) e.update();
            
            if(!destroyedEntities.isEmpty()) {
                entities.removeAll(destroyedEntities);
                destroyedEntities.clear();
            }
            
            if(!createdEntities.isEmpty()) {
                entities.addAll(createdEntities);
                createdEntities.clear();
            }
            
            // draw all entities
            StdDraw.clear(backgroundColor);
            if(!entities.isEmpty()) for(Entity e : entities) e.draw();
            StdDraw.show(16);
        }
    }
    
    public static void showTitleScreen() {
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        StdDraw.text(WIDTH * .5, HEIGHT * .75, "Shooter Game");
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        StdDraw.text(WIDTH * .5, HEIGHT * .6, "by Davidson Lu");
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        StdDraw.text(WIDTH * .5, HEIGHT * .3, "Click to start");
        StdDraw.text(WIDTH * .5, HEIGHT * .3 - 32, "Click to shoot");
        StdDraw.text(WIDTH * .5, HEIGHT * .3 - 64, "WASD to move");
        StdDraw.show(500);
        while(!StdDraw.mousePressed());
    }
    
    public static void showDeathScreen() {
        for(int i = 0; i < 25; i++) {
            StdDraw.clear(new Color(0, 0, 0, 32));
            StdDraw.show(16);
        }
        for(int i = 3; i > 0; i--) {
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.RED);
            StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            StdDraw.text(WIDTH * .5, HEIGHT * .6, "you died");
            StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            StdDraw.text(WIDTH * .5, HEIGHT * .3, "restarting in " + i);
            StdDraw.show(1000);
        }
        startGame();
    }
    
    public static void showWinScreen() {
        for(int i = 0; i < 25; i++) {
            StdDraw.clear(new Color(255, 255, 255, 32));
            StdDraw.show(16);
        }
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        StdDraw.text(WIDTH * .5, HEIGHT * .5, "you win");
        StdDraw.show();
        while(!StdDraw.mousePressed());
        startGame();
    }
}