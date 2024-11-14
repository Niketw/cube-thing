package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Water {
    public static Object3d createWaterMolecule() {
        Object3d molecule = new Object3d();

        Color oxygenColor = new Color(255, 0, 0);
        Color hydrogenColor = new Color(255, 255, 255);
        Color bondColor = new Color(0, 0, 0);

        Point3d oxygenCenter = new Point3d(0f, 0f, 0f);
        Point3d hydrogen1 = new Point3d(1.2f, 1.2f, 0f);
        Point3d hydrogen2 = new Point3d(-1.2f, 1.2f, 0f);

        float oxygenRadius = 0.8f;
        float hydrogenRadius = 0.4f;

        Point3d bond1Start = getSphereIntersection(oxygenCenter, hydrogen1, oxygenRadius - 0.1f);
        Point3d bond1End = getSphereIntersection(hydrogen1, oxygenCenter, hydrogenRadius - 0.1f);
        Point3d bond2Start = getSphereIntersection(oxygenCenter, hydrogen2, oxygenRadius - 0.1f);
        Point3d bond2End = getSphereIntersection(hydrogen2, oxygenCenter, hydrogenRadius - 0.1f);

        addSphere(molecule, oxygenCenter, oxygenRadius, 32, oxygenColor);
        addSphere(molecule, hydrogen1, hydrogenRadius, 24, hydrogenColor);
        addSphere(molecule, hydrogen2, hydrogenRadius, 24, hydrogenColor);

        addCylinder(molecule, bond1Start, bond1End, 0.15f, 16, bondColor);
        addCylinder(molecule, bond2Start, bond2End, 0.15f, 16, bondColor);

        return molecule;
    }

    private static Point3d getSphereIntersection(Point3d sphereCenter, Point3d target, float radius) {
        float dx = target.x - sphereCenter.x;
        float dy = target.y - sphereCenter.y;
        float dz = target.z - sphereCenter.z;
        float distance = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);

        float ratio = radius / distance;
        return new Point3d(
                sphereCenter.x + dx * ratio,
                sphereCenter.y + dy * ratio,
                sphereCenter.z + dz * ratio
        );
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

    private static void addCylinder(Object3d obj, Point3d start, Point3d end, float radius, int segments, Color color) {
        float dx = end.x - start.x;
        float dy = end.y - start.y;
        float dz = end.z - start.z;
        float length = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);

        float[] perpX = new float[segments];
        float[] perpY = new float[segments];
        float[] perpZ = new float[segments];

        float ax = -dy;
        float ay = dx;
        float az = 0f;
        float perpLength = (float)Math.sqrt(ax * ax + ay * ay + az * az);
        if (perpLength < 0.0001f) {
            ax = 0f;
            ay = 1f;
            az = 0f;
        } else {
            ax /= perpLength;
            ay /= perpLength;
            az /= perpLength;
        }

        for (int i = 0; i < segments; i++) {
            float angle = (float)(2f * Math.PI * i / segments);
            float cos = (float)Math.cos(angle);
            float sin = (float)Math.sin(angle);

            perpX[i] = (float)(radius * (ax * cos + (dy * az - dz * ay) * sin / length));
            perpY[i] = (float)(radius * (ay * cos + (dz * ax - dx * az) * sin / length));
            perpZ[i] = (float)(radius * (az * cos + (dx * ay - dy * ax) * sin / length));
        }

        for (int i = 0; i < segments; i++) {
            int next = (i + 1) % segments;

            Point3d v1 = new Point3d(
                    start.x + perpX[i],
                    start.y + perpY[i],
                    start.z + perpZ[i]
            );
            Point3d v2 = new Point3d(
                    start.x + perpX[next],
                    start.y + perpY[next],
                    start.z + perpZ[next]
            );
            Point3d v3 = new Point3d(
                    end.x + perpX[next],
                    end.y + perpY[next],
                    end.z + perpZ[next]
            );
            Point3d v4 = new Point3d(
                    end.x + perpX[i],
                    end.y + perpY[i],
                    end.z + perpZ[i]
            );

            obj.addPolygon(new Polygon3d(List.of(v1, v2, v3, v4), color));

            if (i == 0) {
                List<Point3d> startCap = new ArrayList<>();
                List<Point3d> endCap = new ArrayList<>();
                for (int j = 0; j < segments; j++) {
                    startCap.add(new Point3d(
                            start.x + perpX[j],
                            start.y + perpY[j],
                            start.z + perpZ[j]
                    ));
                    endCap.add(new Point3d(
                            end.x + perpX[j],
                            end.y + perpY[j],
                            end.z + perpZ[j]
                    ));
                }
                obj.addPolygon(new Polygon3d(startCap, color));
                obj.addPolygon(new Polygon3d(endCap, color));
            }
        }
    }
}