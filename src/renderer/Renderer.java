package renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;
import utils.MatrixUtils;

public class Renderer extends JPanel {
    private Object3d cube;
    private float angleX = 0, angleY = 0;
    private float[][] viewMatrix;

    public Renderer() {
        cube = createCube();
        viewMatrix = new float[4][4];

        // Add mouse listener for rotation
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                angleX += e.getY() * 0.0001;
                angleY += e.getX() * 0.0001;
                repaint();
            }
        });
    }

    private Object3d createCube() {
        Object3d cube = new Object3d();
        List<Point3d> vertices = List.of(
                new Point3d(-1, -1, -1), new Point3d(1, -1, -1),
                new Point3d(1, 1, -1), new Point3d(-1, 1, -1),
                new Point3d(-1, -1, 1), new Point3d(1, -1, 1),
                new Point3d(1, 1, 1), new Point3d(-1, 1, 1)
        );

        // Define the six faces of the cube
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)), Color.GRAY)); // Front face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(4), vertices.get(5), vertices.get(6), vertices.get(7)), Color.GRAY)); // Back face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(5), vertices.get(4)), Color.GRAY)); // Bottom face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(2), vertices.get(3), vertices.get(7), vertices.get(6)), Color.GRAY)); // Top face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(1), vertices.get(2), vertices.get(6), vertices.get(5)), Color.GRAY)); // Right face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(3), vertices.get(7), vertices.get(4)), Color.GRAY)); // Left face

        return cube;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Reset view matrix and apply rotations
        viewMatrix = MatrixUtils.createRotationX(angleX);
        float[][] rotationY = MatrixUtils.createRotationY(angleY);
        viewMatrix = multiplyMatrices(viewMatrix, rotationY);

        // Draw the cube
        cube.draw(g2d, viewMatrix);
    }

    private float[][] multiplyMatrices(float[][] a, float[][] b) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
}
