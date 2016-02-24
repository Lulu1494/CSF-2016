public class Knight extends Adventurer {
    void defaultStats() {
        super.defaultStats();
        icon = "knight";
        swordIcon = "metalSword";
        initializeHealth(8);
        attackDamage = 2;
    }
}