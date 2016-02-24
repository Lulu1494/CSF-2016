// Asteroids by Davidson Lu
// Created 2016 February 22
// Uses StdDraw modified to include isKeyPressed()

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class Asteroids {
    public static boolean running = false;
    public static boolean paused = false;
    
    public static void main(String[] args) {
        setupWindow(WIDTH, HEIGHT);
        startGame();
    }
    
    public static char
        KEY_UP = 'w',
        KEY_DOWN = 's',
        KEY_RIGHT = 'd',
        KEY_LEFT = 'a',
        KEY_SHOOT = ' ',
        KEY_PAUSE = 'p';
    
    public static Random rand = new Random();
    
    public static boolean DRAW_COLLIDERS = false;
    public static Color colliderColor = new Color(64, 64, 64);
    
    public static Color foregroundColor = StdDraw.WHITE;
    public static Color backgroundColor = new Color(0, 0, 0, 255);
    
    // window size
    public static int WIDTH = 640, HEIGHT = 640;
    
    // milliseconds between each redraw
    public static int TICK_LAG = 17;
    public static long time = 0;
    
    // === ships and their vars ===
    public static double 
        shipRadius = 8,
        shipX = WIDTH  * .5, shipY = HEIGHT * .5, 
        shipVx = 0, shipVy = 0,
        shipAngle = Math.PI * .25,
        shipTurnRate = Math.PI / 180.0 * 1.5,
        shipAcceleration = .02;
    
    public static boolean 
        shipIsVisible = true,
        shooting = false;
    
    
    // === shots and their vars ===
    public static final int MAX_SHOT_COUNT = 5;
    public static boolean[] enabledShots = new boolean[MAX_SHOT_COUNT];
    public static double[]
        shotX  = new double[MAX_SHOT_COUNT], shotY  = new double[MAX_SHOT_COUNT],
        shotVx = new double[MAX_SHOT_COUNT], shotVy = new double[MAX_SHOT_COUNT];
    public static double shotSpeed = 5;
    public static double shotRadius = 2;
    public static long[] shotStartTime = new long[MAX_SHOT_COUNT];
    public static long shotLifetime = 2000;
    public static double shotKickPower = .05; // backward ship acceleration per shot
    
    // === asteroids and their vars ===
    public static final int MAX_ASTEROID_COUNT = 100;
    public static boolean[] enabledAsteroids = new boolean[MAX_ASTEROID_COUNT];
    public static double[]
        asteroidX  = new double[MAX_ASTEROID_COUNT], asteroidY  = new double[MAX_ASTEROID_COUNT],
        asteroidVx = new double[MAX_ASTEROID_COUNT], asteroidVy = new double[MAX_ASTEROID_COUNT],
        asteroidRadius = new double[MAX_ASTEROID_COUNT],
        asteroidAngle = new double[MAX_ASTEROID_COUNT],
        asteroidAngVel = new double[MAX_ASTEROID_COUNT];
    public static double[][] 
        asteroidVertexX = new double[MAX_ASTEROID_COUNT][],
        asteroidVertexY = new double[MAX_ASTEROID_COUNT][];
    
    // ship polygon vertices
    public static double[] 
        shipVertexX = {12, -12, -9, -9, -12, 12},
        shipVertexY = { 0, -8, -4,  4,  8,  0};
        
    public static void setupWindow(int width, int height) {
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(width  * .05 / 1.1, width  * 1.05 / 1.1);
        StdDraw.setYscale(height * .05 / 1.1, height * 1.05 / 1.1);
    }
    
    public static void startGame() {
        if(!running) {
            running = true;
            
            // spawn asteroids randomly around the map
            for(int i = 0; i < 10; i++) {
                int a = getAsteroid();
                setAsteroidRadius(a, rand.nextDouble() * 24 + 16);
                setAsteroidPosition(a, 
                                    rand.nextDouble() * WIDTH, 
                                    rand.nextDouble() * HEIGHT);
                setAsteroidVelocity(a,
                                    rand.nextDouble() - .5,
                                    rand.nextDouble() - .5);
            }
            
            showTitleScreen();
            
            boolean pausing = false;
            while(running) {
                if(!StdDraw.isKeyPressed(KEY_PAUSE)) pausing = false; 
                else if(!pausing) {
                    pausing = true;
                    paused = !paused;
                    if(paused) {
                        StdDraw.clear(new Color(0, 0, 0, 128));
                        StdDraw.setFont(new Font("Consolas", Font.PLAIN, 50));
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.text(WIDTH*.5, HEIGHT*.5, "paused");
                    }
                }
                
                if(!paused) {
                    StdDraw.clear(backgroundColor);
                    
                    updateShip();
                    updateShots();
                    updateAsteroids();
                    
                    if(shipIsVisible) drawShip();
                    drawShots();
                    drawAsteroids();
                    
                    time += TICK_LAG;
                }
                StdDraw.show(TICK_LAG);
            }
        }
    }
    
    public static void showTitleScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Consolas", Font.PLAIN, 50));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH*.5, HEIGHT*.75, "Asteroids");
        StdDraw.setFont(new Font("Consolas", Font.PLAIN, 30));
        StdDraw.text(WIDTH*.5, HEIGHT*.75-48, "by Davidson Lu");
        StdDraw.setFont(new Font("Consolas", Font.PLAIN, 15));
        StdDraw.text(WIDTH*.5, HEIGHT*.4, "Press Space to Start");
        StdDraw.text(WIDTH*.5, HEIGHT*.4-24*1, "Space to Shoot");
        StdDraw.text(WIDTH*.5, HEIGHT*.4-24*2, "W/S to Move");
        StdDraw.text(WIDTH*.5, HEIGHT*.4-24*3, "D/A to Turn");
        StdDraw.text(WIDTH*.5, HEIGHT*.4-24*4, "P to Pause");
        while(!StdDraw.isKeyPressed(KEY_SHOOT));
    }
    
    // used to wrap positions around the map
    public static double wrapCoordinate(double x, double limit) {
        return (x % limit + limit) % limit;
    }
    
    // used to get directional key input
    // returns 1 if only positive is pressed
    // returns -1 if only negative is pressed
    // returns 0 if none or both are pressed
    public static int getAxis(char positive, char negative) {
        return (StdDraw.isKeyPressed(positive) ? 1 : -1)
             - (StdDraw.isKeyPressed(negative) ? 1 : -1);
    }
    
    public static void shipInput() {
        int turning = getAxis(KEY_RIGHT, KEY_LEFT);
        int accelerating = getAxis(KEY_UP, KEY_DOWN);
        
        if(turning != 0) shipAngle -= turning * shipTurnRate;
        
        if(accelerating != 0) {
            double a = accelerating * shipAcceleration;
            shipVx += a * Math.cos(shipAngle);
            shipVy += a * Math.sin(shipAngle);
        }
        
        if(!StdDraw.isKeyPressed(KEY_SHOOT)) shooting = false;
        else if(!shooting) {
            shooting = true;
            shoot();            
        }
    }
    
    public static void updateShip() {
        shipInput();
        shipX = wrapCoordinate(shipX + shipVx, WIDTH);
        shipY = wrapCoordinate(shipY + shipVy, HEIGHT);
        
        if(time - shipInvulnerabilityStartTime > shipInvulnerabilityDuration) {
            shipIsVisible = true;
            checkShipCollision();
        }
        else {
            shipIsVisible = (time - shipInvulnerabilityStartTime) / shipFlickerDuration % 2 == 0;
        }
    }
    
    public static void drawShip() {
        // translate and rotate the ship vertices
        double[] tx = new double[shipVertexX.length];
        double[] ty = new double[shipVertexY.length];
        double cos = Math.cos(shipAngle);
        double sin = Math.sin(shipAngle);
        for(int i = 0; i < tx.length; i++) {
            tx[i] = shipX + shipVertexX[i] * cos - shipVertexY[i] * sin;
            ty[i] = shipY + shipVertexX[i] * sin + shipVertexY[i] * cos;
        }
        
        if(DRAW_COLLIDERS) {
            StdDraw.setPenRadius(1 / (double) WIDTH);
            StdDraw.setPenColor(colliderColor);
            StdDraw.filledCircle(shipX, shipY, shipRadius);
        }
        
        StdDraw.setPenRadius(1.5 / (double) WIDTH);
        StdDraw.setPenColor(foregroundColor);
        StdDraw.polygon(tx, ty);
    }
    
    public static void shoot() {
        int shot = getShot();
        if(shot >= 0) {
            double cos = Math.cos(shipAngle);
            double sin = Math.sin(shipAngle);
            setShotPosition(shot, 
                            shipX + cos * 12, 
                            shipY + sin * 12);
            setShotVelocity(shot,
                            cos * shotSpeed,
                            sin * shotSpeed);
            shipVx -= cos * shotKickPower;
            shipVy -= sin * shotKickPower;
        }
    }
    
    public static void checkShipCollision() {
        for(int asteroid = 0; asteroid < MAX_ASTEROID_COUNT; asteroid++) if(enabledAsteroids[asteroid]) {
            double dx = asteroidX[asteroid] - shipX;
            double dy = asteroidY[asteroid] - shipY;
            double sqrDist = dx * dx + dy * dy;
            double minDist = shipRadius + asteroidRadius[asteroid];
            if(sqrDist <= minDist * minDist) {
                breakShip();
                break;
            }
        }
    }
    
    public static long shipFlickerDuration = 100;
    public static long shipFlickerStartTime;
    public static long shipInvulnerabilityStartTime;
    public static long shipInvulnerabilityDuration = 3000;
    
    public static void breakShip() {
        shipFlickerStartTime = time;
        shipInvulnerabilityStartTime = time;
        shipX = WIDTH * .5;
        shipY = HEIGHT * .5;
        shipVx = 0;
        shipVy = 0;
    }
    
    
    // === SHOTS ===
    // the stuff shot out by the ship
    
    public static int getShot() {
        for(int shot = 0; shot < MAX_SHOT_COUNT; shot++) {
            if(!enabledShots[shot]) {
                enabledShots[shot] = true;
                shotStartTime[shot] = time;
                return shot;
            }
        }
        return -1;
    }
    
    public static void deleteShot(int shot) {
        enabledShots[shot] = false;
    }
    
    public static void setShotPosition(int shot, double x, double y) {
        shotX[shot] = x;
        shotY[shot] = y;
    }
    
    public static void setShotVelocity(int shot, double vx, double vy) {
        shotVx[shot] = vx;
        shotVy[shot] = vy;
    }
    
    public static void updateShots() {
        for(int shot = 0; shot < MAX_SHOT_COUNT; shot++) {
            if(enabledShots[shot]) {
                if(time - shotStartTime[shot] >= shotLifetime) {
                    deleteShot(shot);
                }
                else {
                    shotX[shot] = wrapCoordinate(shotX[shot] + shotVx[shot], WIDTH);
                    shotY[shot] = wrapCoordinate(shotY[shot] + shotVy[shot], HEIGHT);
                    checkShotCollision(shot);
                }
            }
        }
    }
    
    public static void checkShotCollision(int shot) {
        for(int asteroid = 0; asteroid < MAX_ASTEROID_COUNT; asteroid++) if(enabledAsteroids[asteroid]) {
            double dx = asteroidX[asteroid] - shotX[shot];
            double dy = asteroidY[asteroid] - shotY[shot];
            double sqrDist = dx * dx + dy * dy;
            double minDist = shotRadius + asteroidRadius[asteroid];
            if(sqrDist <= minDist * minDist) {
                breakAsteroid(asteroid);
                deleteShot(shot);
                break;
            }
        }
    }
    
    public static void drawShots() {
        StdDraw.setPenRadius(1 / (double) WIDTH);
        StdDraw.setPenColor(foregroundColor);
        for(int s = 0; s < MAX_SHOT_COUNT; s++) if(enabledShots[s]) {
            StdDraw.filledCircle(shotX[s], shotY[s], shotRadius);
        }
    }
    
    
    
    // === ASTEROIDS ===
    // the stuff you shoot at
    
    public static int getAsteroid() {
        for(int a = 0; a < MAX_ASTEROID_COUNT; a++) {
            if(!enabledAsteroids[a]) {
                enabledAsteroids[a] = true;
                asteroidAngle[a] = rand.nextDouble() * Math.PI * 2;
                asteroidAngVel[a] = (rand.nextDouble() - .5) * Math.PI / 180.0;
                return a;
            }
        }
        return -1;
    }
    
    public static void deleteAsteroid(int asteroid) {
        enabledAsteroids[asteroid] = false;
    }
    
    public static double[][] generateAsteroid(double radius) {
        int vertexCount = rand.nextInt(20) + 5;
        double[][] vertices = new double[2][vertexCount];
        for(int i = 0; i < vertexCount; i++) {
            double angle = i * 2 * Math.PI / (double) vertexCount;
            double r = radius * (0.8 + rand.nextDouble() * .5);
            vertices[0][i] = r * Math.cos(angle);
            vertices[1][i] = r * Math.sin(angle);
        }
        return vertices;
    }
    
    public static void breakAsteroid(int asteroid) {
        if(asteroidRadius[asteroid] > 16) {
            int partCount = rand.nextInt(3) + 2;
            for(int i = 0; i < partCount; i++) {
                int part = getAsteroid();
                setAsteroidRadius(part, asteroidRadius[asteroid] * .5);
                setAsteroidPosition(part, asteroidX[asteroid], asteroidY[asteroid]);
                setAsteroidVelocity(part, rand.nextDouble()-.5, rand.nextDouble()-.5);
            }
        }
        deleteAsteroid(asteroid);
    }
    
    public static void setAsteroidPosition(int asteroid, double x, double y) {
        asteroidX[asteroid] = x;
        asteroidY[asteroid] = y;
    }
    
    public static void setAsteroidVelocity(int asteroid, double vx, double vy) {
        asteroidVx[asteroid] = vx;
        asteroidVy[asteroid] = vy;
    }
    
    public static void setAsteroidRadius(int asteroid, double radius) {
        asteroidRadius[asteroid] = radius;
        
        double[][] asteroidShape = generateAsteroid(radius);
        asteroidVertexX[asteroid] = asteroidShape[0];
        asteroidVertexY[asteroid] = asteroidShape[1];
    }
    
    public static void updateAsteroids() {
        for(int a = 0; a < MAX_ASTEROID_COUNT; a++) if(enabledAsteroids[a]) {
            asteroidX[a] = wrapCoordinate(asteroidX[a] + asteroidVx[a], WIDTH);
            asteroidY[a] = wrapCoordinate(asteroidY[a] + asteroidVy[a], HEIGHT);
            asteroidAngle[a] += asteroidAngVel[a];
        }
        
    }
    
    public static void drawAsteroids() {
        StdDraw.setPenRadius(1 / (double) WIDTH);
        if(DRAW_COLLIDERS) {
            StdDraw.setPenColor(colliderColor);
            for(int a = 0; a < MAX_ASTEROID_COUNT; a++) if(enabledAsteroids[a]) {
                StdDraw.filledCircle(asteroidX[a], asteroidY[a], asteroidRadius[a]);
            }
        }
        StdDraw.setPenColor(foregroundColor);
        for(int a = 0; a < MAX_ASTEROID_COUNT; a++) if(enabledAsteroids[a]) {
            // rotate asteroid vertices using rotation transformation
            double[] tx = new double[asteroidVertexX[a].length];
            double[] ty = new double[asteroidVertexY[a].length];
            double cos = Math.cos(asteroidAngle[a]);
            double sin = Math.sin(asteroidAngle[a]);
            for(int i = 0; i < tx.length; i++) {
                tx[i] = asteroidX[a] + asteroidVertexX[a][i] * cos - asteroidVertexY[a][i] * sin;
                ty[i] = asteroidY[a] + asteroidVertexX[a][i] * sin + asteroidVertexY[a][i] * cos;
            }
            
            if(DRAW_COLLIDERS) {
                StdDraw.setPenRadius(1 / (double) WIDTH);
                StdDraw.setPenColor(colliderColor);
                StdDraw.filledCircle(shipX, shipY, shipRadius);
            }
            
            StdDraw.setPenRadius(1.5 / (double) WIDTH);
            StdDraw.setPenColor(foregroundColor);
            StdDraw.polygon(tx, ty);
        }
    }
}