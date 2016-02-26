public class Input {
    public static int getAxis(int positive, int negative) {
        return (StdDraw.isKeyPressed(positive) ? 1 : 0) - (StdDraw.isKeyPressed(negative) ? 1 : 0);
    }
}