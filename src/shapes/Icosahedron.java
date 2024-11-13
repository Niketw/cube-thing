package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.util.List;
import java.util.ArrayList;
import utils.RandomColor;  // Import the Utils class for random color generation

public class Icosahedron {

    public static Object3d createIcosahedron() {
        List<Point3d> vertices = new ArrayList<>();

        // Phi (golden ratio)
        float phi = (1 + (float) Math.sqrt(5)) / 2;

        // 12 vertices for the Icosahedron
        vertices.add(new Point3d(-1, phi, 0));
        vertices.add(new Point3d(1, phi, 0));
        vertices.add(new Point3d(-1, -phi, 0));
        vertices.add(new Point3d(1, -phi, 0));

        vertices.add(new Point3d(0, -1, phi));
        vertices.add(new Point3d(0, 1, phi));
        vertices.add(new Point3d(0, -1, -phi));
        vertices.add(new Point3d(0, 1, -phi));

        vertices.add(new Point3d(phi, 0, -1));
        vertices.add(new Point3d(phi, 0, 1));
        vertices.add(new Point3d(-phi, 0, -1));
        vertices.add(new Point3d(-phi, 0, 1));

        // Create polygons for the Icosahedron
        List<Polygon3d> polygons = new ArrayList<>();

        // Define faces using the indices
        int[][] faceIndices = {
                {0, 11, 5}, {0, 5, 1}, {0, 1, 7}, {0, 7, 10}, {0, 10, 11},
                {1, 5, 9}, {5, 11, 4}, {11, 10, 2}, {10, 7, 6}, {7, 1, 8},
                {3, 9, 4}, {3, 4, 2}, {3, 2, 6}, {3, 6, 8}, {3, 8, 9},
                {4, 9, 5}, {2, 4, 11}, {6, 2, 10}, {8, 6, 7}, {9, 8, 1}
        };

        // Create polygons with random colors
        for (int[] face : faceIndices) {
            List<Point3d> faceVertices = new ArrayList<>();
            for (int i : face) {
                faceVertices.add(vertices.get(i));
            }
            // Set random color for each face
            polygons.add(new Polygon3d(faceVertices, RandomColor.getRandomColor()));
        }

        // Create and return the Object3d for Icosahedron
        Object3d object3d = new Object3d();
        for (Polygon3d polygon : polygons) {
            object3d.addPolygon(polygon);
        }

        return object3d;
    }
}
