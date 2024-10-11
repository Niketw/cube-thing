package objects;

import java.util.ArrayList;
import java.util.List;

public class Object3d {
    private final List<Polygon3d> polygons;

    public Object3d() {
        polygons = new ArrayList<>();
    }

    public void addPolygon(Polygon3d polygon) {
        polygons.add(polygon);
    }

    public List<Polygon3d> getPolygons() {
        return polygons;
    }
}
