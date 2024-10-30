package input;

import renderer.Renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    private final Renderer renderer;

    public KeyboardInput(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                renderer.translationX -= 0.1f;
                break;
            case KeyEvent.VK_D:
                renderer.translationX += 0.1f;
                break;
            case KeyEvent.VK_W:
                renderer.translationY -= 0.1f;
                break;
            case KeyEvent.VK_S:
                renderer.translationY += 0.1f;
                break;
        }
        renderer.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
