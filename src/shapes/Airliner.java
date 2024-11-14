package shapes;
import objects.Object3d;
import objects.Point3d;
import objects.Polygon3d;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import utils.RandomColor;

public class Airliner {
    public static Object3d createAirliner() {
        Object3d airliner = new Object3d();

        // Basic measurements
        float length = 8.0f;        // Fuselage length
        float diameter = 1.0f;      // Fuselage diameter
        float wingspan = 7.0f;      // Wing span
        float tailHeight = 1.5f;    // Vertical stabilizer height

        // Colors
        Color fuselageColor = Color.WHITE;
        Color wingColor = new Color(220, 220, 220);  // Light gray
        Color engineColor = new Color(150, 150, 150); // Medium gray
        Color windowColor = new Color(100, 149, 237); // Cornflower blue

        // Create fuselage sections (using multiple circular cross-sections)
        int sections = 16;  // Number of sections for the fuselage
        int segments = 12;  // Number of segments in each circular cross-section

        List<List<Point3d>> fuselageSections = new ArrayList<>();

        // Generate points for each fuselage section with varying diameter
        for (int i = 0; i <= sections; i++) {
            float z = -length/2 + (length * i/sections);
            float sectionDiameter = diameter;

            // Nose taper
            if (i < sections/8) {
                sectionDiameter *= (float)Math.pow(i/(sections/8.0), 0.5);
            }
            // Tail taper
            if (i > sections*6/8) {
                sectionDiameter *= (float)(1.0 - Math.pow((i-sections*6/8.0)/(sections-sections*6/8.0), 0.5) * 0.7);
            }

            List<Point3d> sectionPoints = new ArrayList<>();
            for (int j = 0; j < segments; j++) {
                float angle = (float)(2 * Math.PI * j / segments);
                sectionPoints.add(new Point3d(
                        z,
                        sectionDiameter/2 * (float)Math.cos(angle),
                        sectionDiameter/2 * (float)Math.sin(angle)
                ));
            }
            fuselageSections.add(sectionPoints);
        }

        // Create fuselage polygons
        for (int i = 0; i < sections; i++) {
            for (int j = 0; j < segments; j++) {
                int nextJ = (j + 1) % segments;
                airliner.addPolygon(new Polygon3d(List.of(
                        fuselageSections.get(i).get(j),
                        fuselageSections.get(i).get(nextJ),
                        fuselageSections.get(i + 1).get(nextJ),
                        fuselageSections.get(i + 1).get(j)
                ), fuselageColor));
            }
        }

        // Windows (passenger windows along fuselage)
        float windowSpacing = 0.4f;
        int windowRows = 2;
        for (int row = 0; row < windowRows; row++) {
            for (float z = -length/3; z < length/3; z += windowSpacing) {
                float windowHeight = diameter/2 - 0.1f - row * 0.2f;
                float windowWidth = 0.15f;
                float windowTall = 0.15f;

                // Left side windows
                List<Point3d> windowPoints = List.of(
                        new Point3d(z, windowHeight, diameter/3),
                        new Point3d(z + windowWidth, windowHeight, diameter/3),
                        new Point3d(z + windowWidth, windowHeight - windowTall, diameter/3),
                        new Point3d(z, windowHeight - windowTall, diameter/3)
                );
                airliner.addPolygon(new Polygon3d(windowPoints, windowColor));

                // Right side windows (mirrored)
                List<Point3d> windowPointsRight = List.of(
                        new Point3d(z, windowHeight, -diameter/3),
                        new Point3d(z + windowWidth, windowHeight, -diameter/3),
                        new Point3d(z + windowWidth, windowHeight - windowTall, -diameter/3),
                        new Point3d(z, windowHeight - windowTall, -diameter/3)
                );
                airliner.addPolygon(new Polygon3d(windowPointsRight, windowColor));
            }
        }

        // Main Wings
        float wingRootChord = 2.0f;     // Wing width at fuselage
        float wingTipChord = 1.0f;      // Wing width at tip
        float wingAngle = 0.2f;         // Wing sweep angle

        List<Point3d> leftWing = List.of(
                new Point3d(0, diameter/2, 0),                    // Root leading edge
                new Point3d(0 + wingRootChord, diameter/2, 0),    // Root trailing edge
                new Point3d(wingspan/2 * wingAngle, diameter/2, -wingspan/2),  // Tip leading edge
                new Point3d(wingspan/2 * wingAngle + wingTipChord, diameter/2, -wingspan/2) // Tip trailing edge
        );

        // Mirror for right wing
        List<Point3d> rightWing = List.of(
                new Point3d(0, diameter/2, 0),                    // Root leading edge
                new Point3d(0 + wingRootChord, diameter/2, 0),    // Root trailing edge
                new Point3d(wingspan/2 * wingAngle, diameter/2, wingspan/2),   // Tip leading edge
                new Point3d(wingspan/2 * wingAngle + wingTipChord, diameter/2, wingspan/2)  // Tip trailing edge
        );

        // Add wings
        airliner.addPolygon(new Polygon3d(leftWing, wingColor));
        airliner.addPolygon(new Polygon3d(rightWing, wingColor));

        // Vertical Stabilizer (Tail)
        List<Point3d> verticalStab = List.of(
                new Point3d(length/2 - 1.0f, 0, 0),              // Base front
                new Point3d(length/2, 0, 0),                     // Base rear
                new Point3d(length/2 - 0.5f, tailHeight, 0),     // Top rear
                new Point3d(length/2 - 1.5f, tailHeight/2, 0)    // Top front
        );
        airliner.addPolygon(new Polygon3d(verticalStab, wingColor));

        // Horizontal Stabilizers
        float stabSpan = 2.0f;
        float stabChord = 1.0f;

        List<Point3d> leftStab = List.of(
                new Point3d(length/2 - 1.0f, tailHeight/4, 0),
                new Point3d(length/2 - 0.5f, tailHeight/4, 0),
                new Point3d(length/2 - 0.7f, tailHeight/4, -stabSpan),
                new Point3d(length/2 - 1.2f, tailHeight/4, -stabSpan)
        );

        List<Point3d> rightStab = List.of(
                new Point3d(length/2 - 1.0f, tailHeight/4, 0),
                new Point3d(length/2 - 0.5f, tailHeight/4, 0),
                new Point3d(length/2 - 0.7f, tailHeight/4, stabSpan),
                new Point3d(length/2 - 1.2f, tailHeight/4, stabSpan)
        );

        airliner.addPolygon(new Polygon3d(leftStab, wingColor));
        airliner.addPolygon(new Polygon3d(rightStab, wingColor));

        // Engines
        float engineLength = 1.5f;
        float engineDiameter = 0.4f;
        int engineSegments = 8;

        // Add engines under each wing
        addEngine(airliner, -0.5f, 0, -wingspan/4, engineLength, engineDiameter, engineSegments, engineColor);
        addEngine(airliner, -0.5f, 0, wingspan/4, engineLength, engineDiameter, engineSegments, engineColor);

        return airliner;
    }

    private static void addEngine(Object3d airliner, float x, float y, float z,
                                  float length, float diameter, int segments, Color color) {
        List<Point3d> frontPoints = new ArrayList<>();
        List<Point3d> backPoints = new ArrayList<>();

        // Create engine cylinder
        for (int i = 0; i < segments; i++) {
            float angle = (float)(2 * Math.PI * i / segments);
            frontPoints.add(new Point3d(
                    x,
                    y + diameter/2 * (float)Math.cos(angle),
                    z + diameter/2 * (float)Math.sin(angle)
            ));
            backPoints.add(new Point3d(
                    x + length,
                    y + diameter/2 * (float)Math.cos(angle),
                    z + diameter/2 * (float)Math.sin(angle)
            ));
        }

        // Create engine faces
        for (int i = 0; i < segments; i++) {
            int next = (i + 1) % segments;
            airliner.addPolygon(new Polygon3d(List.of(
                    frontPoints.get(i),
                    frontPoints.get(next),
                    backPoints.get(next),
                    backPoints.get(i)
            ), color));
        }

        // Add engine caps
        airliner.addPolygon(new Polygon3d(frontPoints, color.darker()));
        airliner.addPolygon(new Polygon3d(backPoints, color.darker()));

        // Add engine intake rim
        float rimThickness = 0.05f;
        List<Point3d> rimPoints = new ArrayList<>();
        for (int i = 0; i < segments; i++) {
            float angle = (float)(2 * Math.PI * i / segments);
            rimPoints.add(new Point3d(
                    x - rimThickness,
                    y + (diameter/2 + rimThickness) * (float)Math.cos(angle),
                    z + (diameter/2 + rimThickness) * (float)Math.sin(angle)
            ));
        }

        for (int i = 0; i < segments; i++) {
            int next = (i + 1) % segments;
            airliner.addPolygon(new Polygon3d(List.of(
                    rimPoints.get(i),
                    rimPoints.get(next),
                    frontPoints.get(next),
                    frontPoints.get(i)
            ), color.darker()));
        }
    }
}