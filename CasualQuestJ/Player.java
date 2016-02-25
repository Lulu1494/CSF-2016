import java.awt.event.KeyEvent;

public abstract class Player extends LivingEntity {
    public int dir = Game.SOUTH;
    public double radius = 8;
    
    public String icon;
    public String attackIcon = "attack";
    public String castIcon = "cast";
    
    public double moveSpeed; // pixels per second
    public double attackDamage; // damage per hit with the primary attack
    public double auraRegenRate; // aura regeneration per second
    public double healthRegenRate; // health regeneration per second
    
    public boolean moveLocked = false;
    boolean attacking = false;
    boolean casting = false;
    
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
        KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5
    };
    
    private boolean[] isKeyHeld = new boolean[keysTracked.length];
    
    double attackFrame;
    
    private long castStartTime;
    private long castDuration;
    public long castTime() { return casting ? Game.time - castStartTime : 0; }
    
    public void update() {
        if(auraRegenRate != 0 && getAura() < getMaxAura()) addAura(auraRegenRate * Game.PER_TICK);
        if(healthRegenRate != 0 && getHealth() < getMaxHealth()) addHealth(healthRegenRate * Game.PER_TICK);
        if(casting && castTime() > castDuration) casting = false;
        
        handleInput();
        if(attacking) attackUpdate(); 
        
        super.update();
        
        // clamp position to within map boundaries
        x = Game.clamp(x, radius, Game.WIDTH  - radius);
        y = Game.clamp(y, radius, Game.HEIGHT - radius);
    }
    
    public void draw() {
        if(isInvincible() && Game.time / 50 % 2 == 0) return;
        StdDraw.picture(x, y, getFullIcon());
        super.draw();
    }
    
    public String getFullIcon() {
        if(attacking && attackIcon != null) return "rsc/classes/" + icon + "/" + attackIcon + " " + Game.dirToString(dir) + ".png";
        else if(casting && castIcon != null) return "rsc/classes/" + icon + "/" + castIcon + " " + Game.dirToString(dir) + ".png";
        else return "rsc/classes/" + icon + "/" + Game.dirToString(dir) + (1 + Game.time / 800 % 2) + ".png";
    }
    
    public boolean canMove() {
        return !(moveLocked || attacking || casting);
    }
    
    private void handleInput() {
        boolean canMove = canMove();
        
        if(canMove) {
            int inputX = getAxis(KEY_RIGHT, KEY_LEFT);
            int inputY = getAxis(KEY_UP, KEY_DOWN);
            
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
        }
        else {
            vx = 0;
            vy = 0;
        }
        
        for(int i = 0; i < keysTracked.length; i++) {
            int key = keysTracked[i];
            if(!StdDraw.isKeyPressed(key)) isKeyHeld[i] = false;
            else if(!isKeyHeld[i]) {
                isKeyHeld[i] = true;
                if(canMove) {
                    if(key == KEY_ATTACK) attack();
                    else if(key == KEY_SKILL1) skill1();
                    else if(key == KEY_SKILL2) skill2();
                    else if(key == KEY_SKILL3) skill3();
                }
                if(key == KeyEvent.VK_1) changeClass(new Adventurer());
                else if(key == KeyEvent.VK_2) changeClass(new Knight());
                else if(key == KeyEvent.VK_3) changeClass(new Mage());
                else if(key == KeyEvent.VK_4) changeClass(new Archer());
                else if(key == KeyEvent.VK_5) changeClass(new Acolyte());
            }
        }
    }
    
    void attack() {  
        attackFrame = 0;
        attacking = true; 
    }
    
    void attackUpdate() { attackFrame += 60 / Game.FRAMES_PER_SECOND; }
    
    void attackEnd() { attacking = false; }
    
    void changeClass(Player newPlayer) {
        destroy();
        Game.player = newPlayer;
        Game.player.moveTo(x, y);
        Game.player.dir = dir;
    }
    
    public void cast(int time) {
        casting = true;
        castStartTime = Game.time;
        castDuration = time;
    }
    
    Projectile launchProjectile(Projectile p) {
        double angle = Game.dirToAngle(dir);
        return launchProjectile(p, 11*Math.cos(angle), 11*Math.sin(angle), angle);
    }
    
    Projectile launchProjectile(Projectile p, double offsetX, double offsetY, double angle) {
        p.owner = this;
        p.moveTo(x + offsetX, y + offsetY);
        p.setAngle(angle);
        return p;
    }
    
    void skill1() { }
    void skill2() { }
    void skill3() { }
    
    private int getAxis(int positive, int negative) {
        return (StdDraw.isKeyPressed(positive) ? 1 : 0) - (StdDraw.isKeyPressed(negative) ? 1 : 0);
    }
}