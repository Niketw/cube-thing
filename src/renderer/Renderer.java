package renderer;

import objects.Object3d;
import objects.Polygon3d;
import shapes.Cube;
import shapes.Cuboid;
import shapes.Dodecahedron;
import shapes.Icosahedron;
import utils.MatrixUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer extends JPanel {
    private Object3d currentShape; // This will hold the current shape to render
    private BufferedImage offscreenImage;
    private float angleX = 0, angleY = 0;
    private float translationX = 0.0f, translationY = 0.0f;
    private float scale = 1.0f; // Scale factor for zoom
    private boolean wireframeMode = false; // Wireframe mode flag
    private JComboBox<String> shapeSelectorComboBox;  // Combo box for shape selection
    private JButton toggleWireframeButton;  // Button to toggle wireframe mode

    public Renderer() {
        setBackground(new Color(40, 40, 40));
        offscreenImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB); // Offscreen buffer
        Timer timer = new Timer(16, e -> repaint()); // 60 FPS
            timer.start();
        setFocusable(true); // Ensure that key events are captured

        // Initialize shape selector combo box
        String[] shapes = {"Dodecahedron", "Icosahedron", "Cube", "Cuboid"};
        shapeSelectorComboBox = new JComboBox<>(shapes);
        shapeSelectorComboBox.setSelectedIndex(0); // Default to Dodecahedron
        shapeSelectorComboBox.setFocusable(false); // Prevent combo box from intercepting key events
        shapeSelectorComboBox.addActionListener(e -> {
            // Change the shape based on selection
            String selectedShape = (String) shapeSelectorComboBox.getSelectedItem();
            switch (selectedShape) {
                case "Dodecahedron":
                    setShape(Dodecahedron.createDodecahedron());
                    break;
                case "Icosahedron":
                    setShape(Icosahedron.createIcosahedron());
                    break;
                case "Cube":
                    setShape(Cube.createCube());
                    break;
                case "Cuboid":
                    setShape(Cuboid.createCuboid(2, 1, 1));
                    break;
            }
        });

        // Mouse and Keyboard listeners for interactive controls
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                angleX = (float) (e.getY() - getHeight() / 2) / ((float) getHeight() / 2);
                angleY = (float) -1 * (e.getX() - (float) getWidth() / 2) / ((float) getWidth() / 2);
                repaint();
            }
        });

        addMouseWheelListener(e -> {
            scale += (float) (e.getPreciseWheelRotation() * -0.1f);
            scale = Math.max(0.1f, scale); // Prevent excessive zoom out
            repaint();
        });

        // Key listener to handle movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Translate using WASD for X and Y, Arrow keys for Z
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:  // Move up along the Y-axis
                        translationY -= 0.1f;
                        break;
                    case KeyEvent.VK_S:  // Move down along the Y-axis
                        translationY += 0.1f;
                        break;
                    case KeyEvent.VK_A:  // Move left along the X-axis
                        translationX -= 0.1f;
                        break;
                    case KeyEvent.VK_D:  // Move right along the X-axis
                        translationX += 0.1f;
                        break;
                }
                repaint();  // Redraw the shape with the new translation
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Only resize if the component has a valid size
                if (getWidth() > 0 && getHeight() > 0) {
                    offscreenImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                }
            }
        });

        // Create the "Toggle Wireframe" button only once
        toggleWireframeButton = new JButton("Toggle Wireframe");
        toggleWireframeButton.setFocusable(false); // Prevent button from intercepting key events
        toggleWireframeButton.setOpaque(false);    // Make button transparent
        toggleWireframeButton.addActionListener(e -> {
            wireframeMode = !wireframeMode;
            repaint();
        });

        // Add the button to the panel
        add(toggleWireframeButton); // Make sure the button doesn't interfere with key events

        // Add the shape selector combo box to the panel at the top
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(59, 59, 59));
        controlPanel.add(shapeSelectorComboBox);
        controlPanel.add(toggleWireframeButton);
        add(controlPanel, BorderLayout.NORTH);
    }

    // Set the shape to render
    public void setShape(Object3d shape) {
        this.currentShape = shape;
        repaint();  // Redraw the shape
    }

    // Toggle wireframe mode
    public void toggleWireframeMode() {
        wireframeMode = !wireframeMode;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Initialize off-screen image for double buffering
        Graphics2D g2d = offscreenImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, offscreenImage.getWidth(), offscreenImage.getHeight()); // Clear buffer

        if (currentShape != null) {
            // Create transformation matrices
            float[][] scaleMatrix = MatrixUtils.createScale(scale);
            float[][] translationMatrix = MatrixUtils.createTranslation(translationX, translationY);  // Include Z translation here
            float[][] rotationXMatrix = MatrixUtils.createRotationX(angleX * (float) Math.PI);
            float[][] rotationYMatrix = MatrixUtils.createRotationY(angleY * (float) Math.PI);

            // Combine matrices: translation -> scaling -> rotation (X -> Y)
            float[][] viewMatrix = MatrixUtils.multiplyMatrices(translationMatrix, scaleMatrix);
            viewMatrix = MatrixUtils.multiplyMatrices(viewMatrix, rotationXMatrix);
            viewMatrix = MatrixUtils.multiplyMatrices(viewMatrix, rotationYMatrix);

            // Sort polygons by depth (Z-axis) from farthest to nearest for proper rendering
            List<Polygon3d> polygons = currentShape.getPolygons();
            float[][] finalViewMatrix = viewMatrix;
            polygons.sort((p1, p2) -> Float.compare(p2.getAverageZ(finalViewMatrix), p1.getAverageZ(finalViewMatrix)));

            // Draw polygons
            for (Polygon3d polygon : polygons) {
                polygon.draw(g2d, viewMatrix, wireframeMode); // Draw in wireframe or solid mode
            }
        }

        // Draw the off-screen image to the panel
        g.drawImage(offscreenImage, 0, 0, this);

        // Dispose the graphics context
        g2d.dispose();
    }
}
