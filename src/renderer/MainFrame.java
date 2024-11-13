package renderer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Show information popup before rendering anything
        showInfoPopup();

        setTitle("3D Shape Renderer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and initialize the renderer
        Renderer renderer = new Renderer();
        renderer.setFocusable(true);  // Make sure it can capture key events

        // Add the renderer to the center
        add(renderer, BorderLayout.CENTER);

        setLocationRelativeTo(null);  // Center the window
    }

    // Method to display the info popup
    private void showInfoPopup() {
        String message = """
                Welcome to the 3D Shape Renderer!
                
                This application allows you to view and interact with various 3D shapes.
                
                Click 'OK' to continue.""";
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Create and show the main frame
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
