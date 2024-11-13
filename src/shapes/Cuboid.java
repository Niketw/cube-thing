package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.util.List;
import utils.RandomColor;  // Import the Utils class for random color generation

public class Cuboid {
    public static Object3d createCuboid(float length, float width, float height) {
        Object3d cuboid = new Object3d();
        List<Point3d> vertices = List.of(
                new Point3d(-length / 2, -width / 2, -height / 2),
                new Point3d(length / 2, -width / 2, -height / 2),
                new Point3d(length / 2, width / 2, -height / 2),
                new Point3d(-length / 2, width / 2, -height / 2),
                new Point3d(-length / 2, -width / 2, height / 2),
                new Point3d(length / 2, -width / 2, height / 2),
                new Point3d(length / 2, width / 2, height / 2),
                new Point3d(-length / 2, width / 2, height / 2)
        );

        // Define the six faces of the cuboid with random colors
        cuboid.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)), RandomColor.getRandomColor())); // Front face
        cuboid.addPolygon(new Polygon3d(List.of(vertices.get(4), vertices.get(5), vertices.get(6), vertices.get(7)), RandomColor.getRandomColor())); // Back face
        cuboid.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(5), vertices.get(4)), RandomColor.getRandomColor())); // Bottom face
        cuboid.addPolygon(new Polygon3d(List.of(vertices.get(2), vertices.get(3), vertices.get(7), vertices.get(6)), RandomColor.getRandomColor())); // Top face
        cuboid.addPolygon(new Polygon3d(List.of(vertices.get(1), vertices.get(2), vertices.get(6), vertices.get(5)), RandomColor.getRandomColor())); // Right face
        cuboid.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(3), vertices.get(7), vertices.get(4)), RandomColor.getRandomColor())); // Left face

        return cuboid;
    }
}
