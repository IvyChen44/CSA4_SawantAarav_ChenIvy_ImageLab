/**
 * Matrix2by2 represents a 2x2 transformation matrix
 * used for 2D image transformations like rotation.
 */
public class Matrix2by2 {

    private double[][] m; // Internal 2x2 array

    // ========================= Constructors =========================

    /** Constructs a matrix with specified elements */
    public Matrix2by2(double a00, double a01, double a10, double a11) {
        m = new double[][] { {a00, a01}, {a10, a11} };
    }

    /** Default constructor creates an identity matrix */
    public Matrix2by2() {
        this(1, 0, 0, 1);
    }

    /** Copy constructor */
    public Matrix2by2(Matrix2by2 other) {
        this(other.m[0][0], other.m[0][1], other.m[1][0], other.m[1][1]);
    }

    // ========================= Getters & Setters =========================

    public double getA00() { return m[0][0]; }
    public double getA01() { return m[0][1]; }
    public double getA10() { return m[1][0]; }
    public double getA11() { return m[1][1]; }

    public void setA00(double val) { m[0][0] = val; }
    public void setA01(double val) { m[0][1] = val; }
    public void setA10(double val) { m[1][0] = val; }
    public void setA11(double val) { m[1][1] = val; }

    /** Sets all elements at once */
    public void setMatrix(double a00, double a01, double a10, double a11) {
        m[0][0] = a00; m[0][1] = a01;
        m[1][0] = a10; m[1][1] = a11;
    }

    // ========================= Matrix Operations =========================

    /** Sets the matrix as a rotation matrix (angle in degrees) */
    public void setRotationMatrix(double angleDegrees) {
        double rad = Math.toRadians(angleDegrees);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        m[0][0] = cos;  m[0][1] = -sin;
        m[1][0] = sin;  m[1][1] = cos;
    }

    /** Multiply by scalar */
    public Matrix2by2 multiply(double scalar) {
        return new Matrix2by2(
            m[0][0] * scalar, m[0][1] * scalar,
            m[1][0] * scalar, m[1][1] * scalar
        );
    }

    /** Multiply by another matrix */
    public Matrix2by2 multiply(Matrix2by2 other) {
        return new Matrix2by2(
            m[0][0]*other.m[0][0] + m[0][1]*other.m[1][0],
            m[0][0]*other.m[0][1] + m[0][1]*other.m[1][1],
            m[1][0]*other.m[0][0] + m[1][1]*other.m[1][0],
            m[1][0]*other.m[0][1] + m[1][1]*other.m[1][1]
        );
    }

    /** Multiply by a vector */
    public Vector1by2 multiply(Vector1by2 v) {
        return new Vector1by2(
            m[0][0]*v.getRow() + m[0][1]*v.getCol(),
            m[1][0]*v.getRow() + m[1][1]*v.getCol()
        );
    }

    /** Static helper to multiply a vector by a matrix */
    public static Vector1by2 multiply(Vector1by2 v, Matrix2by2 mat) {
        return mat.multiply(v);
    }

    // ========================= Overrides =========================

    @Override
    public String toString() {
        return String.format("[[%f, %f], [%f, %f]]", m[0][0], m[0][1], m[1][0], m[1][1]);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Matrix2by2)) return false;
        Matrix2by2 o = (Matrix2by2) obj;
        double eps = 1e-4;
        return Math.abs(m[0][0]-o.m[0][0]) < eps &&
               Math.abs(m[0][1]-o.m[0][1]) < eps &&
               Math.abs(m[1][0]-o.m[1][0]) < eps &&
               Math.abs(m[1][1]-o.m[1][1]) < eps;
    }
}
