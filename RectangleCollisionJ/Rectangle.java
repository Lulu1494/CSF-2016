public class Rectangle {
    double left, bottom, right, top;
    
    public Rectangle(double left, double bottom, double right, double top) {
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }
    
    public boolean containsPoint(double x, double y) {
        return x >= left && x <= right && y >= bottom && y <= top;
    }
    
    public boolean intersects(Rectangle other) {
        return left < other.right && right > other.left 
            && bottom < other.top && top > other.bottom;
    }
    
    public void draw() {
        StdDraw.filledPolygon(new double[] {left, right, right, left},
                              new double[] {bottom, bottom, top, top});
    }
    
    public void translate(double tx, double ty) {
        left += tx;
        bottom += ty;
        right += tx;
        top += ty;
    }
}