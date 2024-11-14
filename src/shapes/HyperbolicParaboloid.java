package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class HyperbolicParaboloid {

    public static Object3d createHyperbolicParaboloid() {
        Object3d paraboloid = new Object3d();

        // Default parameters for the hyperbolic paraboloid
        int segments = 30;        // Number of grid divisions (higher = smoother)
        float size = 4.0f;        // Size of the paraboloid
        float a = 1.2f;           // Semi-major axis for the oval (x-direction)
        float b = 1f;           // Semi-minor axis for the oval (y-direction)
        float curvatureStrength = 0.5f; // Strength of the curvature effect (0 to 1)
        float step = size / segments;

        List<Point3d> points = new ArrayList<>();

        // Generate points for the oval shape and apply hyperbolic curvature
        for (int i = 0; i < segments; i++) {
            for (int j = 0; j < segments; j++) {
                // Parametric equation for the oval
                float thetaX = 2 * (float) Math.PI * i / (segments - 1);  // X-axis angle
                float thetaY = 2 * (float) Math.PI * j / (segments - 1);  // Y-axis angle

                float x = a * (float) Math.cos(thetaX);
                float y = b * (float) Math.sin(thetaY);

                // Apply hyperbolic curvature (paraboloid-like effect)
                float z = (x * x) / (a * a) - (y * y) / (b * b);  // Standard hyperbolic paraboloid equation

                // Apply curvature strength to the z value to modify the amount of curvature
                float distanceFromCenter = (float) Math.sqrt(x * x + y * y);
                float maxDistance = (float) Math.sqrt(a * a + b * b);  // Maximum distance from center (ellipse size)
                float curvatureFactor = (float) Math.exp(-distanceFromCenter * curvatureStrength / maxDistance); // Apply smooth exponential decay

                z *= curvatureFactor; // Apply the curvature effect, rounding the corners more as we move outward

                // Add the point to the list of points
                points.add(new Point3d(x, y, z));
            }
        }

        // Create a consistent color for the Pringle-like effect
        // RGB color similar to a Pringle (golden-brown)
        Color pringleColor = new Color(217, 155, 57); // RGB (217, 155, 57)

        // Create faces connecting the grid points
        for (int i = 0; i < segments - 1; i++) {
            for (int j = 0; j < segments - 1; j++) {
                // Get points for each grid square
                int idx = i * segments + j;

                Point3d p1 = points.get(idx);
                Point3d p2 = points.get(idx + 1);
                Point3d p3 = points.get(idx + segments);
                Point3d p4 = points.get(idx + segments + 1);

                // Create two triangles for each square with the consistent color
                paraboloid.addPolygon(new Polygon3d(List.of(p1, p2, p4), pringleColor));
                paraboloid.addPolygon(new Polygon3d(List.of(p1, p4, p3), pringleColor));
            }
        }

        return paraboloid;
    }
}
