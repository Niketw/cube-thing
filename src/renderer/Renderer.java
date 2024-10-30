package renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import input.KeyboardInput;
import input.MouseInput;
import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;
import utils.MatrixUtils;

public class Renderer extends JPanel {
    private final Object3d vertexArray;
    private final BufferedImage offscreenImage;// Off-screen buffer

    public float angleX = 0, angleY = 0;
    public float translationX = 0.0f;
    public float translationY = 0.0f;
    public float scale = 1.0f; // Initial scale factor for zoom

    private boolean wireframeMode = false; // Wireframe mode flag

    public Renderer() {
        setBackground(Color.GRAY);
        vertexArray = createCube();
        offscreenImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB); // Initialize off-screen buffer

        Timer timer = new Timer(16, e -> repaint()); // Approximately 60 FPS
        timer.start();

        MouseInput mouseInput = new MouseInput(this);

        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);
        addKeyListener(new KeyboardInput(this));

        setFocusable(true); // Ensure the Renderer panel can receive key events
    }

    public void toggleWireframeMode() {
        wireframeMode = !wireframeMode;
        repaint();
    }

    private Object3d createCube() {
        Object3d vertexArray = new Object3d();
        List<Point3d> vertices = List.of(
                new Point3d(-1, -1, -1), new Point3d(1, -1, -1),
                new Point3d(1, 1, -1), new Point3d(-1, 1, -1),
                new Point3d(-1, -1, 1), new Point3d(1, -1, 1),
                new Point3d(1, 1, 1), new Point3d(-1, 1, 1)
        );

        // Define the six faces of the vertexArray
        vertexArray.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)), Color.WHITE)); // Front face
        vertexArray.addPolygon(new Polygon3d(List.of(vertices.get(4), vertices.get(5), vertices.get(6), vertices.get(7)), Color.WHITE)); // Back face
        vertexArray.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(1), vertices.get(5), vertices.get(4)), Color.WHITE)); // Bottom face
        vertexArray.addPolygon(new Polygon3d(List.of(vertices.get(2), vertices.get(3), vertices.get(7), vertices.get(6)), Color.WHITE)); // Top face
        vertexArray.addPolygon(new Polygon3d(List.of(vertices.get(1), vertices.get(2), vertices.get(6), vertices.get(5)), Color.WHITE)); // Right face
        vertexArray.addPolygon(new Polygon3d(List.of(vertices.get(0), vertices.get(3), vertices.get(7), vertices.get(4)), Color.WHITE)); // Left face

        return vertexArray;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Initialize graphics for offscreen image
        Graphics2D g2d = offscreenImage.createGraphics();

        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, offscreenImage.getWidth(), offscreenImage.getHeight()); // Clear the image

        // Create transformation matrices
        float[][] scaleMatrix = MatrixUtils.createScale(scale);
        float[][] translationMatrix = MatrixUtils.createTranslation(translationX, translationY);
        float[][] rotationX = MatrixUtils.createRotationX(angleX * (float) Math.PI);
        float[][] rotationY = MatrixUtils.createRotationY(angleY * (float) Math.PI);

        // Combine the matrices: translation -> scaling -> rotations
        float[][] viewMatrix = multiplyMatrices(translationMatrix, scaleMatrix);
        viewMatrix = multiplyMatrices(viewMatrix, rotationX);
        viewMatrix = multiplyMatrices(viewMatrix, rotationY);

        // Sort polygons by depth from farthest to nearest
        List<Polygon3d> polygons = vertexArray.getPolygons();
        float[][] finalViewMatrix = viewMatrix;
        polygons.sort((p1, p2) -> Float.compare(p2.getAverageZ(finalViewMatrix), p1.getAverageZ(finalViewMatrix)));

        // Draw each polygon in sorted order
        for (Polygon3d polygon : polygons) {
            polygon.draw(g2d, viewMatrix, wireframeMode);
        }

        // Draw the offscreen image to the panel
        g.drawImage(offscreenImage, 0, 0, this);

        // Dispose of the graphics context for offscreen image
        g2d.dispose();
    }

    public void setShape(Polygon3d newPolygon) {
        vertexArray.getPolygons().clear(); // Clear existing polygons
        vertexArray.addPolygon(newPolygon); // Add the new polygon
        repaint(); // Repaint the renderer to show the new shape
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
