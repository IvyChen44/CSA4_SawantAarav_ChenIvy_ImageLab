/**
 * Represents a 3D vector with x, y, z components
 */
public class Vector {
    private double x;
    private double y;
    private double z;

    // Constructor
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Accessors
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    // Setters
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setZ(double z) { this.z = z; }

    // Vector addition
    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    // Dot product
    public double dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    // Scalar multiplication
    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    // String representation
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
