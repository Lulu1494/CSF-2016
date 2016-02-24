public class Rectangle {
    public double left, bottom, right, top;
    
    public Rectangle(double Left, double Bottom, double Right, double Top) {
        left = Left;
        bottom = Bottom;
        right = Right;
        top = Top;
    }
    
    public boolean containsPoint(double x, double y) {
        return x >= left && x <= right && y >= bottom && y <= top;
    }
    
    public boolean intersects(Rectangle other) {
        return right >= other.left && left <= other.right 
            && top >= other.bottom && bottom <= other.top;
    }
}