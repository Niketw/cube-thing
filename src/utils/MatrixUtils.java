package utils;

public class MatrixUtils {
    public static float[][] createRotationX(float angle) {
        return new float[][] {
                {1, 0, 0, 0},
                {0, (float) Math.cos(angle), (float) -Math.sin(angle), 0},
                {0, (float) Math.sin(angle), (float) Math.cos(angle), 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] createRotationY(float angle) {
        return new float[][] {
                {(float) Math.cos(angle), 0, (float) Math.sin(angle), 0},
                {0, 1, 0, 0},
                {(float) -Math.sin(angle), 0, (float) Math.cos(angle), 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] createScale(float scaleFactor) {
        return new float[][] {
                {scaleFactor, 0, 0, 0},
                {0, scaleFactor, 0, 0},
                {0, 0, scaleFactor, 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] createTranslation(float tx, float ty) {
        return new float[][] {
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }
}
