import java.awt.event.KeyEvent;

public abstract class Player extends LivingEntity {
    public int dir = Game.SOUTH;
    public double radius = 8;
    public String icon;
    
    public double moveSpeed; // pixels per second
    public double attackDamage; // damage per hit with the primary attack
    public double auraRegenRate; // aura regeneration per second
    public double healthRegenRate; // health regeneration per second
    
    public boolean canMove = true;
    public boolean attacking = false;
    
    public boolean isInvincible() { return invincibleUntil > Game.time; }
    private long invincibleUntil = 0;
    public void invincibility(int duration) { invincibleUntil = Game.time + duration; }
    
    public void takeDamage(double damage) {
        super.takeDamage(damage);
        invincibility(1000);
    }
    
    private static int 
        KEY_UP = KeyEvent.VK_UP,
        KEY_DOWN = KeyEvent.VK_DOWN,
        KEY_RIGHT = KeyEvent.VK_RIGHT,
        KEY_LEFT = KeyEvent.VK_LEFT,
        KEY_ATTACK = KeyEvent.VK_SPACE,
        KEY_SKILL1 = KeyEvent.VK_Z,
        KEY_SKILL2 = KeyEvent.VK_X,
        KEY_SKILL3 = KeyEvent.VK_C;
    
    private static final int[] keysTracked = {
        KEY_UP, KEY_DOWN, KEY_RIGHT, KEY_LEFT, 
        KEY_ATTACK, KEY_SKILL1, KEY_SKILL2, KEY_SKILL3, 
        KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3
    };
    
    private boolean[] isKeyHeld = new boolean[keysTracked.length];
    
    public void update() {
        if(auraRegenRate != 0 && getAura() < getMaxAura()) addAura(auraRegenRate * Game.PER_TICK);
        if(healthRegenRate != 0 && getHealth() < getMaxHealth()) addHealth(healthRegenRate * Game.PER_TICK);
        
        handleInput();
        if(attacking) attackUpdate(); 
        
        super.update();
        
        // clamp position to within map boundaries
        x = Game.clamp(x, radius, Game.WIDTH  - radius);
        y = Game.clamp(y, radius, Game.HEIGHT - radius);
    }
    
    public void draw() {
        if(isInvincible() && Game.time / 10 % 2 == 0) return;
        
        String fullIcon;
        if(attacking) {
            fullIcon = "rsc/" + icon + "/attack " + Game.dirToString(dir) + ".png";
        }
        else {
            fullIcon = "rsc/" + icon + "/" 
                + Game.dirToString(dir) 
                + (1 + Game.time / 800 % 2) 
                + ".png";
        }
        StdDraw.picture(x, y, fullIcon);
        
//        StdDraw.textLeft(0, 32, "" + Game.time + "   " + getAura());
        super.draw();
    }
    
    private void handleInput() {
        int inputX = canMove ? getAxis(KEY_RIGHT, KEY_LEFT) : 0;
        int inputY = canMove ? getAxis(KEY_UP, KEY_DOWN) : 0;
        
        if(inputX != 0 || inputY != 0) {
            dir = 0;
            if(inputX != 0) {
                if(inputX > 0) dir |= Game.EAST;
                else dir |= Game.WEST;
            }
            else if(inputY != 0) {
                if(inputY > 0) dir |= Game.NORTH;
                else dir |= Game.SOUTH;
            }
        }
        
        vx = inputX * moveSpeed;
        vy = inputY * moveSpeed;
        
        for(int i = 0; i < keysTracked.length; i++) {
            int key = keysTracked[i];
            if(!StdDraw.isKeyPressed(key)) isKeyHeld[i] = false;
            else if(!isKeyHeld[i]) {
                isKeyHeld[i] = true;
                if(!attacking && canMove) {
                    if(key == KEY_ATTACK) attack();
                    else if(key == KEY_SKILL1) skill1();
                    else if(key == KEY_SKILL2) skill2();
                    else if(key == KEY_SKILL3) skill3();
                }
                if(key == KeyEvent.VK_1) changeClass(new Adventurer());
                else if(key == KeyEvent.VK_2) changeClass(new Knight());
                else if(key == KeyEvent.VK_3) changeClass(new Mage());
            }
        }
    }
    
    double attackFrame;
    
    void attack() { 
        attackFrame = 0;
        attacking = true; 
        canMove = false;
    }
    
    void attackUpdate() {
        attackFrame += 60 / Game.FRAMES_PER_SECOND;
    }
    
    void attackEnd() {
        attacking = false;
        canMove = true;
    }
    
    void changeClass(Player newPlayer) {
        Game.player = newPlayer;
        Game.player.moveTo(x, y);
        destroy();
    }
    
    void skill1() { }
    void skill2() { }
    void skill3() { }
    
    private int getAxis(int positive, int negative) {
        return (StdDraw.isKeyPressed(positive) ? 1 : 0) - (StdDraw.isKeyPressed(negative) ? 1 : 0);
    }
}