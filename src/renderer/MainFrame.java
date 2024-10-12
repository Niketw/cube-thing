package renderer;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("cube thing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Renderer());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
