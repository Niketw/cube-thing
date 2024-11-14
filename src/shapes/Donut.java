package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.util.ArrayList;
import java.util.List;
import utils.RandomColor;

public class Donut {
    public static Object3d createDonut(int radialSegments, int tubularSegments, float radius, float tubeRadius) {
        Object3d donut = new Object3d();
        List<Point3d> vertices = new ArrayList<>();

        // Generate vertices for the torus
        for (int i = 0; i < radialSegments; i++) {
            float theta = (float)(2 * Math.PI * i / radialSegments);  // Angle around main circle
            float cosTheta = (float)Math.cos(theta);
            float sinTheta = (float)Math.sin(theta);

            for (int j = 0; j < tubularSegments; j++) {
                float phi = (float)(2 * Math.PI * j / tubularSegments);  // Angle around tube circle
                float cosPhi = (float)Math.cos(phi);
                float sinPhi = (float)Math.sin(phi);

                float x = (radius + tubeRadius * cosPhi) * cosTheta;
                float y = (radius + tubeRadius * cosPhi) * sinTheta;
                float z = tubeRadius * sinPhi;

                vertices.add(new Point3d(x, y, z));
            }
        }

        // Connect vertices to create faces
        for (int i = 0; i < radialSegments; i++) {
            for (int j = 0; j < tubularSegments; j++) {
                int nextI = (i + 1) % radialSegments;
                int nextJ = (j + 1) % tubularSegments;

                // Create quads from four vertices around each segment
                Point3d v1 = vertices.get(i * tubularSegments + j);
                Point3d v2 = vertices.get(nextI * tubularSegments + j);
                Point3d v3 = vertices.get(nextI * tubularSegments + nextJ);
                Point3d v4 = vertices.get(i * tubularSegments + nextJ);

                // Add polygon to the donut
                donut.addPolygon(new Polygon3d(List.of(v1, v2, v3, v4), RandomColor.getRandomColor()));
            }
        }

        return donut;
    }
}
