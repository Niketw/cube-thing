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

    public static float[][] multiplyMatrices(float[][] matrix1, float[][] matrix2) {
        float[][] result = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }
}
