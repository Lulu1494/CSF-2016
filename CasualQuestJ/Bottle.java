public class Bottle extends Item {
    public Bottle() { 
        super();
        icon = "bottle";
    }
    
    public void pickedUp() {
        if(Game.player.getAura() < Game.player.getMaxAura()) {
            Game.player.setAura(Game.player.getMaxAura());
            destroy();
        }
    }
}