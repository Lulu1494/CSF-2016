import java.awt.Color;

public abstract class LivingEntity extends Entity {
    private double health;
    private double maxHealth;
    
    private double aura;
    private double maxAura;
    
    private StatBar healthBG, healthFG, auraBG, auraFG;
    private StatBar[] statBars;
    
    void defaultStats() { }
    
    public LivingEntity() {
        super();
        defaultStats();
    }
    
    public void initializeHealth(double h) { maxHealth = h; setHealth(h); }
    public void initializeAura(double a) { maxAura = a; setAura(a); }
    
    public double getMaxHealth() { return maxHealth; }
    public double getHealth() { return health; }
    public void setHealth(double newHealth) { health = Game.clamp(newHealth, 0, maxHealth); }
    public void addHealth(double n) { setHealth(getHealth() + n); }
    public boolean hasHealth(double min) { return getHealth() >= min; }
    public boolean hasHealth() { return getHealth() > 0; }

    public double getMaxAura() { return maxAura; }
    public double getAura() { return aura; }
    public void setAura(double newAura) { aura = Game.clamp(newAura, 0, maxAura); }
    public void addAura(double n) { setAura(getAura() + n); }
    public boolean hasAura(double min) { return getAura() >= min; }
    
    public boolean spendAura(double n) { 
        if(!hasAura(n)) { return false; }
        else {
            addAura(-n); 
            return true; 
        } 
    }
    
    public void takeDamage(double damage) {
        addHealth(-damage);
        
        if(!hasHealth()) { die(); }
    }
    
    public void knockback(Entity source, double distance) {
        double dx = x - source.x;
        double dy = y - source.y;
        double s = distance / distanceTo(source);
        move(dx * s, dy * s);
    }
    
    public void die() { destroy(); }
    
    public void draw() { drawBars(); }
    
    public void drawBars() {
        if(healthBG == null) {
            healthBG = new StatBar();
            healthBG.color = Color.GRAY;
            healthBG.percent = 1;
            
            healthFG = new StatBar();
            healthFG.color = Color.RED;
                
            auraBG = new StatBar();
            auraBG.color = Color.GRAY;
            auraBG.percent = 1;
            
            auraFG = new StatBar();
            auraFG.color = Color.BLUE;
            
            statBars = new StatBar[] { healthBG, healthFG, auraBG, auraFG };
            
            for(StatBar s : statBars) { s.width = 16; s.height = 2; }
        }
        
        for(StatBar s : statBars) { s.left = x - 8; s.bottom = y + 9; }
        healthBG.bottom += 2; healthFG.bottom += 2;
        
        if(getMaxHealth() > 0 && getHealth() < getMaxHealth()) {
            healthFG.percent = getHealth() / getMaxHealth();
            healthBG.draw(); 
            healthFG.draw(); 
        }
        
        if(getMaxAura() > 0 && getAura() < getMaxAura()) {
            auraFG.percent = getAura() / getMaxAura();
            auraBG.draw();
            auraFG.draw();
        }
    }
}