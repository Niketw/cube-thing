package renderer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Cube Renderer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Renderer renderer = new Renderer();

        // Set main layout
        setLayout(new BorderLayout());
        add(renderer, BorderLayout.CENTER);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the main menu
        JMenu optionsMenu = new JMenu("Options");

        // Create the toggle wireframe menu item
        JMenuItem toggleWireframeItem = new JMenuItem("Toggle Wireframe");
        toggleWireframeItem.addActionListener(e -> renderer.toggleWireframeMode());

        // Add menu item to the main menu and main menu to the menu bar
        optionsMenu.add(toggleWireframeItem);
        menuBar.add(optionsMenu);

        // Set the menu bar in the main frame
        setJMenuBar(menuBar);

        // Set the window size and center the frame on the screen
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
