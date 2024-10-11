package objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Object3d {
    private List<Polygon3d> polygons;

    public Object3d() {
        polygons = new ArrayList<>();
    }

    public void addPolygon(Polygon3d polygon) {
        polygons.add(polygon);
    }

    public void draw(Graphics g, float[][] projectionMatrix) {
        for (Polygon3d polygon : polygons) {
            polygon.draw(g, projectionMatrix);
        }
    }
}
