public class Matrix {
    private double[][] data;

    public Matrix(double[][] data) {
        if(data.length != 3 || data[0].length != 3)
            throw new IllegalArgumentException("Matrix must be 3x3");
        this.data = data;
    }

    public Vector multiply(Vector v) {
        double newX = data[0][0]*v.getX() + data[0][1]*v.getY() + data[0][2]*v.getZ();
        double newY = data[1][0]*v.getX() + data[1][1]*v.getY() + data[1][2]*v.getZ();
        double newZ = data[2][0]*v.getX() + data[2][1]*v.getY() + data[2][2]*v.getZ();
        return new Vector(newX, newY, newZ);
    }

    public static Matrix rotationZ(double theta) {
        double[][] rot = {
            { Math.cos(theta), -Math.sin(theta), 0 },
            { Math.sin(theta),  Math.cos(theta), 0 },
            { 0, 0, 1 }
        };
        return new Matrix(rot);
    }

    public static Matrix rotationX(double theta) {
        double[][] rot = {
            {1, 0, 0},
            {0, Math.cos(theta), -Math.sin(theta)},
            {0, Math.sin(theta), Math.cos(theta)}
        };
        return new Matrix(rot);
    }

    public static Matrix rotationY(double theta) {
        double[][] rot = {
            {Math.cos(theta), 0, Math.sin(theta)},
            {0, 1, 0},
            {-Math.sin(theta), 0, Math.cos(theta)}
        };
        return new Matrix(rot);
    }
}
