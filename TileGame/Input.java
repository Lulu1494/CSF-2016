import java.awt.event.KeyEvent;
public class Input {
    private static java.util.Set<Integer> keysDown = new java.util.HashSet<Integer>();
    
    public enum Axis { Horizontal, Vertical }
    
    public static int
        PLAYER_UP = KeyEvent.VK_UP,
        PLAYER_DOWN = KeyEvent.VK_DOWN,
        PLAYER_RIGHT = KeyEvent.VK_RIGHT,
        PLAYER_LEFT = KeyEvent.VK_LEFT,
        CAMERA_UP = KeyEvent.VK_W,
        CAMERA_DOWN = KeyEvent.VK_S,
        CAMERA_RIGHT = KeyEvent.VK_D,
        CAMERA_LEFT = KeyEvent.VK_A,
        RESET = KeyEvent.VK_R,
        RECENTER = KeyEvent.VK_C,
        ATTACK = KeyEvent.VK_SPACE,
        SKILL_1 = KeyEvent.VK_Z,
        SKILL_2 = KeyEvent.VK_X,
        SKILL_3 = KeyEvent.VK_C,
        TOGGLE_HUD = KeyEvent.VK_H,
        TOGGLE_CONTROLS = KeyEvent.VK_V;
    
    public static int getAxis(int positive, int negative) {
        return (getKey(positive) ? 1 : 0) - (getKey(negative) ? 1 : 0);
    }
    
    public static int getAxis(Axis axis) { 
        switch(axis) {
            case Horizontal: return getAxis(PLAYER_RIGHT, PLAYER_LEFT);
            case Vertical:   return getAxis(PLAYER_UP,    PLAYER_DOWN);
            default: return 0;
        }
    }
    
    // returns the current state of the key (down => true)
    public static boolean getKey(int keyCode) {
        return StdDraw.isKeyPressed(keyCode);
    }
    
    // returns true on the first frame the key is pressed
    public static boolean getKeyDown(int keyCode) {
        if(!getKey(keyCode)) keysDown.remove(keyCode);
        else if(!keysDown.contains(keyCode)) {
            keysDown.add(keyCode);
            return true;
        }
        return false;
    }
        
}