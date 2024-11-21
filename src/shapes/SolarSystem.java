package shapes;

import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;

import java.awt.Color;
import java.util.*;

public class SolarSystem {

    public static Object3d createSolarSystem() {
        Object3d solarSystem = new Object3d();

        // Sun
        Point3d sunCenter = new Point3d(0f, 0f, 0f);
        float sunRadius = 5f;
        addSphere(solarSystem, sunCenter, sunRadius, 64, new Color(255, 204, 0)); // Yellow sun

        // Planets and their orbits
        float[] planetRadii = {1f, 1.2f, 1.5f, 2.5f, 3.5f, 4.0f, 3.5f, 3.0f};
        float[] orbitRadii = {10f, 15f, 20f, 30f, 45f, 60f, 75f, 90f};
        Color[] planetColors = {
                new Color(139, 69, 19),   // Mercury - Brown
                new Color(220, 20, 60),  // Venus - Crimson
                new Color(0, 0, 255),    // Earth - Blue
                new Color(255, 69, 0),   // Mars - Red
                new Color(255, 215, 0),  // Jupiter - Golden
                new Color(135, 206, 250), // Saturn - Light Blue
                new Color(0, 255, 255),   // Uranus - Cyan
                new Color(72, 61, 139)    // Neptune - Dark Blue
        };

        for (int i = 0; i < planetRadii.length; i++) {
            // Calculate planet position along its orbit (default angle 0)
            float angle = (float)(Math.random() * 2 * Math.PI); // Random initial position
            float x = orbitRadii[i] * (float)Math.cos(angle);
            float y = orbitRadii[i] * (float)Math.sin(angle);
            float z = 0f; // Assume planets orbit in the X-Y plane

            Point3d planetCenter = new Point3d(x, y, z);

            // Add the planet
            addSphere(solarSystem, planetCenter, planetRadii[i], 32, planetColors[i]);

            // Add orbit (optional, visual guide)
            addOrbit(solarSystem, orbitRadii[i], 128, new Color(40, 40, 40)); // Light gray orbit
        }

        return solarSystem;
    }

    private static void addSphere(Object3d obj, Point3d center, float radius, int segments, Color color) {
        // Reuse the sphere generation logic from Water class
        List<Point3d> vertices = new ArrayList<>();

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

    private static void addOrbit(Object3d obj, float radius, int segments, Color color) {
        // Create a simple ring to represent the orbit
        List<Point3d> vertices = new ArrayList<>();
        for (int i = 0; i < segments; i++) {
            float theta = (float)(2 * Math.PI * i / segments);
            float x = radius * (float)Math.cos(theta);
            float y = radius * (float)Math.sin(theta);
            vertices.add(new Point3d(x, y, 0));
        }

        // Create faces connecting the ring
        for (int i = 0; i < segments; i++) {
            int next = (i + 1) % segments;
            obj.addPolygon(new Polygon3d(List.of(
                    vertices.get(i),
                    vertices.get(next),
                    new Point3d(0, 0, 0) // Center for visual effect
            ), color));
        }
    }
}
