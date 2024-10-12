package renderer;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Cycles 3D Renderer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Renderer());
        setSize(800, 600);

        try {
            Image icon = ImageIO.read(new File("./src/assets/icon.png"));
            setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
