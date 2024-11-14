package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.util.ArrayList;
import java.util.List;
import utils.RandomColor;

public class HyperbolicParaboloid {

    public static Object3d createHyperbolicParaboloid() {
        Object3d paraboloid = new Object3d();

        // Default parameters for the hyperbolic paraboloid
        int segments = 75;        // Number of grid divisions (higher = smoother)
        float size = 2.0f;        // Size of the paraboloid
        float a = 2.0f;           // Control the curvature along the x-axis
        float b = 2.0f;           // Control the curvature along the y-axis
        float step = size / segments;

        List<Point3d> points = new ArrayList<>();

        // Generate points for the hyperbolic paraboloid
        for (int i = 0; i < segments; i++) {
            for (int j = 0; j < segments; j++) {
                // Calculate the x and y coordinates
                float x = -size / 2 + i * step;
                float y = -size / 2 + j * step;

                // Calculate the z value based on the hyperbolic paraboloid equation
                float z = (x * x) / (a * a) - (y * y) / (b * b);

                // Add the point to the list of points
                points.add(new Point3d(x, y, z));
            }
        }

        // Create faces connecting the grid points
        for (int i = 0; i < segments - 1; i++) {
            for (int j = 0; j < segments - 1; j++) {
                // Get points for each grid square
                int idx = i * segments + j;

                Point3d p1 = points.get(idx);
                Point3d p2 = points.get(idx + 1);
                Point3d p3 = points.get(idx + segments);
                Point3d p4 = points.get(idx + segments + 1);

                // Create two triangles for each square
                paraboloid.addPolygon(new Polygon3d(List.of(p1, p2, p4), RandomColor.getRandomColor()));
                paraboloid.addPolygon(new Polygon3d(List.of(p1, p4, p3), RandomColor.getRandomColor()));
            }
        }

        return paraboloid;
    }
}
