package objects;

public class Point3d {
    public float x, y, z;

    public Point3d(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3d transform(float[][] matrix) {
        float[] result = new float[4];
        float[] point = {x, y, z, 1.0f};

        for (int i = 0; i < 4; i++) {
            result[i] = 0;
            for (int j = 0; j < 4; j++) {
                result[i] += matrix[i][j] * point[j];
            }
        }
        return new Point3d(result[0] / result[3], result[1] / result[3], result[2] / result[3]);
    }

    public float getX() {return x;}
    public float getY() {return y;}
    public float getZ() {return z;}

    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void setZ(float z) {this.z = z;}
}
