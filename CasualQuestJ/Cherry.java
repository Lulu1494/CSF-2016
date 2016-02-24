public class Cherry extends Item {
    public Cherry() {
        super();
        icon = "cherry";
    }
    
    public void pickedUp() {
        if(Game.player.getHealth() < Game.player.getMaxHealth()) {
            Game.player.addHealth(1);
            destroy();
        }
    }
}