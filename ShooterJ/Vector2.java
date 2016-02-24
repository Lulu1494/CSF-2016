public class Vector2 {
    private double x = 0, y = 0;
    
    public Vector2() { }
    
    public Vector2(double X, double Y) { x = X; y = Y; }
    
    public static Vector2 fromPolar(double magnitude, double angle) {
        return new Vector2(magnitude * Math.cos(angle),
                           magnitude * Math.sin(angle));
    }
    
    public static Vector2 lerp(Vector2 a, Vector2 b, double t) {
        return new Vector2(a.x() * (1 - t) + b.x() * t,
                           a.y() * (1 - t) + b.y() * t);
    }
    
    public double x() { return x; }
    public double y() { return y; }
    
    public double dot(Vector2 v) { return x * v.x() + y * v.y(); }
    public double magnitude() { return Math.sqrt(x * x + y * y); }
    public double sqrMagnitude() { return x * x + y * y; }
    public double angle() { return Math.atan2(y, x); }
    
    public Vector2 add(Vector2 v) {  return new Vector2(x + v.x(), y + v.y());  }
    public Vector2 subtract(Vector2 v) { return new Vector2(x - v.x(), y - v.y()); }
    public Vector2 negate() { return new Vector2(-x, -y); }
    public Vector2 multiply(double s) { return new Vector2(x * s, y * s); }
    public Vector2 scale(double s) { return multiply(s); }
    public Vector2 divide(double s) { return multiply(1 / s); }
    public Vector2 normalized() { return divide(magnitude()); }
}