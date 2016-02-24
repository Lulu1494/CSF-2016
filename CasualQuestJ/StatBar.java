import java.awt.Color;

public class StatBar {
    public double percent;
    public double left, bottom;
    public double width, height;
    public Color color;
    
    public void draw() {
        double right = left + width * percent, top = bottom + height;
        Game.setPenRadius(1);
        StdDraw.setPenColor(color);
        StdDraw.filledPolygon(new double[] {left, right, right, left},
                            new double[] {bottom, bottom, top, top});
    }
}