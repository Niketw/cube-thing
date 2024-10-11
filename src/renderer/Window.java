package renderer;

import javax.swing.JFrame;

public class Window extends JFrame {
    public Window() {
        super("Cube Thing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Renderer());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
