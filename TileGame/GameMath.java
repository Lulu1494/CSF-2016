public class GameMath {
    private GameMath() { }
    public static double lerp(double a, double b, double t) { return a * (1 - t) + b * t; }
    public static double clamp(double n, double a, double b) { return Math.min(Math.max(n, a), b); }
}