// Rectangle Collision by Davidson Lu
// Created 2016 February 23

// Collision detection and resolving between axis-aligned rectangles of various sizes
// using StdDraw (with isKeyPressed())

import java.awt.event.KeyEvent;
import java.util.Random;

public class Main {
    public static final int 
        WIDTH = 800, 
        HEIGHT = 800;
    
    public static Random rand = new Random();
    
    public static void main(String[] args) {
        setupWindow(WIDTH, HEIGHT);
        
        Rectangle a = new Rectangle(WIDTH*.5 - 16, HEIGHT*.5 - 16, WIDTH*.5 + 16, HEIGHT*.5 + 16);
        Rectangle[] obstacles = new Rectangle[30];
        
        for(int i = 0; i < obstacles.length; i++) {
            double width = 32 + 128 * rand.nextDouble();
            double height = 32 + 128 * rand.nextDouble();
            double x = (WIDTH - width) * rand.nextDouble();
            double y = (HEIGHT - height) * rand.nextDouble();
            obstacles[i] = new Rectangle(x, y, x + width, y + height);
        }
        
        double moveSpeed = 2;
        int inputX, inputY;
        double vx, vy;
        
        while(true) {
            inputX = 0;
            inputY = 0;
            
            if(StdDraw.isKeyPressed(KeyEvent.VK_UP))    { inputY++; }
            if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN))  { inputY--; }
            if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) { inputX++; }
            if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT))  { inputX--; }
            
            vx = moveSpeed * inputX;
            vy = moveSpeed * inputY;
            
            a.translate(vx, vy);
            
            boolean intersecting = false;
            for(Rectangle b : obstacles) if(a.intersects(b)) {
                intersecting = true;
                
                double dx = 0, dy = 0;
                
                if(a.right - b.left > b.right - a.left)
                    dx = b.right - a.left;
                else dx = b.left - a.right;
                
                if(a.top - b.bottom > b.top - a.bottom)
                    dy = b.top - a.bottom;
                else dy = b.bottom - a.top; 
                
                if(vy != 0 && Math.abs(dx) > Math.abs(dy)) 
                    a.translate(0, dy);
                else a.translate(dx, 0);
            }
            
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.setPenRadius(1 / (double) WIDTH);
            StdDraw.setPenColor(StdDraw.RED);
            for(Rectangle b : obstacles) b.draw();
            StdDraw.setPenColor(intersecting ? StdDraw.GREEN : StdDraw.BLUE);
            a.draw();
            
            StdDraw.show(16);
        }
    }
    
    public static void setupWindow(int width, int height) {
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(width  * .05 / 1.1, width  * 1.05 / 1.1);
        StdDraw.setYscale(height * .05 / 1.1, height * 1.05 / 1.1);
    }
}