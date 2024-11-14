package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.util.ArrayList;
import java.util.List;
import utils.RandomColor;  // Import the Utils class for random color generation

public class Pyramid {

    public static Object3d createPyramid(float baseLength, float height) {
        Object3d pyramid = new Object3d();
        List<Point3d> vertices = new ArrayList<>();

        // Define the 4 vertices of the base (square)
        float halfBase = baseLength / 2;
        vertices.add(new Point3d(-halfBase, -halfBase, 0));  // Bottom-left
        vertices.add(new Point3d(halfBase, -halfBase, 0));   // Bottom-right
        vertices.add(new Point3d(halfBase, halfBase, 0));    // Top-right
        vertices.add(new Point3d(-halfBase, halfBase, 0));   // Top-left

        // Define the apex of the pyramid (above the center of the base)
        Point3d apex = new Point3d(0, 0, height);

        // Create the four triangles connecting the apex to each side of the base
        for (int i = 0; i < 4; i++) {
            int nextIndex = (i + 1) % 4;  // Loop back to the first vertex for the last triangle
            pyramid.addPolygon(new Polygon3d(
                    List.of(vertices.get(i), vertices.get(nextIndex), apex),
                    RandomColor.getRandomColor()
            ));
        }

        // Optionally, create the base of the pyramid as a polygon (square face)
        pyramid.addPolygon(new Polygon3d(
                List.of(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)),
                RandomColor.getRandomColor()
        ));

        return pyramid;
    }
}
