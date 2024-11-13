package utils;

import java.awt.Color;
import java.util.Random;

public class RandomColor {
    private static final Random random = new Random();

    // Method to generate a random color biased towards lighter colors
    public static Color getRandomColor() {
        // Bias the color values to be in the upper range (towards lighter shades)
        int r = biasedRandomValue();
        int g = biasedRandomValue();
        int b = biasedRandomValue();

        return new Color(r, g, b);
    }

    // Helper method to generate a random value with a bias towards higher values
    private static int biasedRandomValue() {
        // The bias here is towards higher values. The random value will be between 128 and 255.
        return 128 + random.nextInt(128);  // Range from 128 to 255
    }
}
