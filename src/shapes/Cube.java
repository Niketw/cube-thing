package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.util.List;
import utils.RandomColor;  // Import the Utils class for random color generation

public class Cube {
    public static Object3d createCube() {
        Object3d cube = new Object3d();
        List<Point3d> vertices = List.of(
                new Point3d(-1, -1, -1), new Point3d(1, -1, -1),
                new Point3d(1, 1, -1), new Point3d(-1, 1, -1),
                new Point3d(-1, -1, 1), new Point3d(1, -1, 1),
                new Point3d(1, 1, 1), new Point3d(-1, 1, 1)
        );

        // Define the six faces of the cube, each with a random color
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)), RandomColor.getRandomColor())); // Front face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(4), vertices.get(5), vertices.get(6), vertices.get(7)), RandomColor.getRandomColor())); // Back face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(5), vertices.get(4)), RandomColor.getRandomColor())); // Bottom face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(2), vertices.get(3), vertices.get(7), vertices.get(6)), RandomColor.getRandomColor())); // Top face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(1), vertices.get(2), vertices.get(6), vertices.get(5)), RandomColor.getRandomColor())); // Right face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(3), vertices.get(7), vertices.get(4)), RandomColor.getRandomColor())); // Left face

        return cube;
    }
}
