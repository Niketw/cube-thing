package input;

import renderer.Renderer;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput implements MouseMotionListener, MouseWheelListener {
    private final Renderer renderer;

    public MouseInput(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        renderer.angleX = (float) (e.getY() - renderer.getHeight() / 2) / ((float) renderer.getHeight() / 2);
        renderer.angleY = (float) -1 * (e.getX() - (float) renderer.getWidth() / 2) / ((float) renderer.getWidth() / 2);
        renderer.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        renderer.scale += (float) (e.getPreciseWheelRotation() * -0.1f); // Adjust scale based on wheel rotation
        renderer.scale = Math.max(0.1f, renderer.scale); // Prevent zooming out too much
        renderer.repaint();
    }
}
