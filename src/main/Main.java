package main;

import javax.swing.*;
import renderer.Renderer;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cube Thing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Renderer());
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}

