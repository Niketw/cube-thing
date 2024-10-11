package objects;

import java.awt.*;
import java.util.List;

public class Polygon3d {
    private List<Point3d> vertices;
    private Color color;

    public Polygon3d(List<Point3d> vertices, Color color) {
        this.vertices = vertices;
        this.color = color;
    }

    public void draw(Graphics g, float[][] projectionMatrix) {
        int[] xPoints = new int[vertices.size()];
        int[] yPoints = new int[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            Point3d transformed = vertices.get(i).transform(projectionMatrix);
            xPoints[i] = (int) (transformed.x * 100 + 400); // Centering
            yPoints[i] = (int) (transformed.y * 100 + 300); // Centering
        }

        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, vertices.size());
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, vertices.size());
    }
}
