package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.util.ArrayList;
import java.util.List;
import utils.RandomColor;  // Import the Utils class for random color generation

public class Dodecahedron {

    public static Object3d createDodecahedron() {
        List<Point3d> vertices = new ArrayList<>();

        // Vertices of the Equilateral Tetrahedron
        float size = 1.0f;  // Scale factor for the size of the tetrahedron

        // Define the four vertices of the equilateral tetrahedron
        vertices.add(new Point3d(size, size, size));    // Vertex 1 (1, 1, 1)
        vertices.add(new Point3d(-size, -size, size));  // Vertex 2 (-1, -1, 1)
        vertices.add(new Point3d(-size, size, -size));  // Vertex 3 (-1, 1, -1)
        vertices.add(new Point3d(size, -size, -size));  // Vertex 4 (1, -1, -1)

        // Now define the faces (4 faces, each with 3 vertices forming a triangle)
        List<Polygon3d> polygons = new ArrayList<>();

        // Define the faces of the Tetrahedron (4 faces, each face is a triangle)
        int[][] faceIndices = {
                {0, 1, 2},  // Face 1: vertices 0, 1, 2
                {0, 1, 3},  // Face 2: vertices 0, 1, 3
                {0, 2, 3},  // Face 3: vertices 0, 2, 3
                {1, 2, 3}   // Face 4: vertices 1, 2, 3
        };

        // Create polygons based on the face indices and assign random colors to each face
        for (int[] face : faceIndices) {
            List<Point3d> faceVertices = new ArrayList<>();
            for (int i : face) {
                faceVertices.add(vertices.get(i));
            }
            polygons.add(new Polygon3d(faceVertices, RandomColor.getRandomColor())); // Set random color for faces
        }

        // Create the Object3d for Tetrahedron
        Object3d object3d = new Object3d();
        for (Polygon3d polygon : polygons) {
            object3d.addPolygon(polygon);
        }

        return object3d;
    }
}
