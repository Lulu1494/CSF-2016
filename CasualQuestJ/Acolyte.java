public class Acolyte extends Adventurer {
    void defaultStats() {
        icon = "acolyte";
        swordIcon = "sword";
        moveSpeed = 32;
        attackDamage = 1;
        initializeHealth(4);
        initializeAura(4);
        auraRegenRate = .25;
    }
    
    void skill1() {
        if(spendAura(1)) {
            cast(400);
            for(int angle : new int[] { 45, -45, 135, -135 }) {
                Projectile h = launchProjectile(new Heal(), 0, 0, Math.toRadians(angle));
                h.angle = 0;
            }
            addHealth(1);
        }
    }
    
    public class Heal extends Projectile {
        void defaultStats() {
            icon = "heal";
            iconFrames = 4;
            iconDelay = 100;
            moveSpeed = 128;
            lifetime = 400;
            hasCollision = false;
        }
    }
}