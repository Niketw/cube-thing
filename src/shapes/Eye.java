package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Eye {
    public static Object3d createEye() {
        Object3d eye = new Object3d();

        Color eyeballColor = new Color(255, 255, 255);
        Color irisColor = new Color(0, 150,200);
        Color pupilColor = new Color(0, 0, 0);

        Point3d eyeballCenter = new Point3d(0f, 0f, 0f);
        float eyeballRadius = 1.0f;

        addSphere(eye, eyeballCenter, eyeballRadius, 32, eyeballColor);

        Point3d irisCenter = new Point3d(0f, 0f, eyeballRadius - 0.1f);
        float irisRadius = 0.4f;
        addSphere(eye, irisCenter, irisRadius, 16, irisColor);

        Point3d pupilCenter = new Point3d(0f, 0f, eyeballRadius - 0.1f);
        float pupilRadius = 0.2f;
        addSphere(eye, pupilCenter, pupilRadius, 8, pupilColor);

        return eye;
    }

    private static void addSphere(Object3d obj, Point3d center, float radius, int segments, Color color) {
        List<Point3d> vertices = new ArrayList<>();
        List<List<Integer>> faces = new ArrayList<>();

        for (int i = 0; i <= segments; i++) {
            float phi = (float)(Math.PI * i / segments);
            float sinPhi = (float)Math.sin(phi);
            float cosPhi = (float)Math.cos(phi);

            for (int j = 0; j < segments; j++) {
                float theta = (float)(2f * Math.PI * j / segments);
                float sinTheta = (float)Math.sin(theta);
                float cosTheta = (float)Math.cos(theta);

                float x = center.x + radius * sinPhi * cosTheta;
                float y = center.y + radius * sinPhi * sinTheta;
                float z = center.z + radius * cosPhi;
                vertices.add(new Point3d(x, y, z));
            }
        }

        for (int i = 0; i < segments; i++) {
            for (int j = 0; j < segments; j++) {
                int current = i * segments + j;
                int next = i * segments + (j + 1) % segments;
                int bottom = ((i + 1) % (segments + 1)) * segments + j;
                int bottomNext = ((i + 1) % (segments + 1)) * segments + (j + 1) % segments;

                obj.addPolygon(new Polygon3d(List.of(
                        vertices.get(current),
                        vertices.get(next),
                        vertices.get(bottomNext),
                        vertices.get(bottom)
                ), color));
            }
        }
    }
}
