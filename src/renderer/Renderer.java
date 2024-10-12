package renderer;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;
import utils.MatrixUtils;

public class Renderer extends JPanel {
    private final Object3d cube;
    private float angleX = 0, angleY = 0;
    private boolean wireframeMode = false; // Wireframe mode flag

    public Renderer() {
        setBackground(Color.GRAY);
        cube = createCube();

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                angleX = (float) (e.getY() - getHeight() / 2) / ((float) getHeight() / 2);
                angleY = (float) -1 * (e.getX() - (float) getWidth() / 2) / ((float) getWidth() / 2);
                repaint();
            }
        });
    }

    public void toggleWireframeMode() {
        wireframeMode = !wireframeMode;
        repaint();
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
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)), Color.WHITE)); // Front face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(4), vertices.get(5), vertices.get(6), vertices.get(7)), Color.WHITE)); // Back face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(5), vertices.get(4)), Color.WHITE)); // Bottom face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(2), vertices.get(3), vertices.get(7), vertices.get(6)), Color.WHITE)); // Top face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(1), vertices.get(2), vertices.get(6), vertices.get(5)), Color.WHITE)); // Right face
        cube.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(3), vertices.get(7), vertices.get(4)), Color.WHITE)); // Left face

        return cube;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create the view matrix and apply rotations
        float[][] viewMatrix = MatrixUtils.createRotationX(angleX * (float) Math.PI);
        float[][] rotationY = MatrixUtils.createRotationY(angleY * (float) Math.PI);
        viewMatrix = multiplyMatrices(viewMatrix, rotationY);

        // Sort polygons by depth from farthest to nearest
        List<Polygon3d> polygons = cube.getPolygons();
        float[][] finalViewMatrix = viewMatrix;
        polygons.sort((p1, p2) -> Float.compare(p2.getAverageZ(finalViewMatrix), p1.getAverageZ(finalViewMatrix)));

        // Draw each polygon in sorted order
        for (Polygon3d polygon : polygons) {
            polygon.draw(g2d, viewMatrix, wireframeMode); // Pass wireframeMode to the draw method
        }
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
