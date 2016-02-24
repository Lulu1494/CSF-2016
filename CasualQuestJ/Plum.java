public class Plum extends Item {
    public Plum() {
        super();
        icon = "plum";
    }
    
    public void pickedUp() {
        if(Game.player.getHealth() < Game.player.getMaxHealth()) {
            Game.player.setHealth(Game.player.getMaxHealth());
            destroy();
        }
    }
}