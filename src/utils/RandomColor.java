package utils;

import java.awt.Color;
import java.util.Random;

public class RandomColor {
    private static final Random random = new Random();

    public static Color getRandomColor() {
        int r = biasedRandomValue();
        int g = biasedRandomValue();
        int b = biasedRandomValue();

        return new Color(r, g, b);
    }

    private static int biasedRandomValue() {
        return 0 + random.nextInt(256);  // Range from 128 to 255
    }
}
