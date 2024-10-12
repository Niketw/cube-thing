package renderer;

import javax.swing.JFrame;

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
