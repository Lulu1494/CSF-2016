public class Shield extends Item {
    public Shield() {
        super();
        icon = "shield";
    }
    
    public void pickedUp() {
        Game.player.invincibility(5000);
        destroy();
    }
}