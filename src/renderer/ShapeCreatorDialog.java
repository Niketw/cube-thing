package renderer;

import objects.Point3d;
import objects.Polygon3d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShapeCreatorDialog extends JDialog {
    private final Renderer renderer;
    private final JTextField vertexInputField;
    private final JButton createButton;

    public ShapeCreatorDialog(Renderer renderer) {
        this.renderer = renderer;

        setTitle("Create New Shape");
        setModal(true); // This makes the dialog modal
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Vertices (x1,y1,z1; x2,y2,z2; ...):"));
        vertexInputField = new JTextField(30);
        inputPanel.add(vertexInputField);

        // Create button
        createButton = new JButton("Create Shape");
        inputPanel.add(createButton);

        add(inputPanel, BorderLayout.CENTER);

        // Button action listener
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createShape();
            }
        });

        pack(); // Sizes the dialog to fit its contents
        setLocationRelativeTo(renderer); // Centers the dialog relative to the renderer
    }

    private void createShape() {
        String input = vertexInputField.getText();
        List<Point3d> vertices = new ArrayList<>();

        // Parse input into vertices
        String[] vertexStrings = input.split(";");
        for (String vertexString : vertexStrings) {
            String[] coords = vertexString.trim().split(",");
            if (coords.length == 3) {
                try {
                    float x = Float.parseFloat(coords[0]);
                    float y = Float.parseFloat(coords[1]);
                    float z = Float.parseFloat(coords[2]);
                    vertices.add(new Point3d(x, y, z));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        // Create and add the new shape
        if (!vertices.isEmpty()) {
            Color color = Color.WHITE; // Default color
            Polygon3d newPolygon = new Polygon3d(vertices, color);
            renderer.setShape(newPolygon); // Set the new shape in the renderer
        } else {
            JOptionPane.showMessageDialog(this, "No valid vertices provided!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        dispose(); // Close the dialog
    }
}
