/**
 * Test class for Vector1by2 and Matrix2by2 classes.
 * Contains unit tests for all public methods.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("=== Testing Vector1by2 Class ===");
        testVectorClass();

        System.out.println("\n=== Testing Matrix2by2 Class ===");
        testMatrixClass();

        System.out.println("\n=== Testing Dot Product Function ===");
        testDotProduct();

        System.out.println("\n=== Testing Matrix Multiplication Function ===");
        testMatrixMultiplication();

        System.out.println("All tests passed!");
    }

    // ========================= Vector1by2 Tests =========================
    public static void testVectorClass() {
        Vector1by2 v1 = new Vector1by2(3, 4);
        Vector1by2 v2 = new Vector1by2(1, 2);
        Vector1by2 v3 = new Vector1by2(v1);

        // Test getters
        assert v1.getRow() == 3 : "Getter test failed for row";
        assert v1.getCol() == 4 : "Getter test failed for col";

        // Test setters
        v1.setRow(5);
        v1.setCol(6);
        assert v1.getRow() == 5 : "Setter test failed for row";
        assert v1.getCol() == 6 : "Setter test failed for col";
        v1.setRow(3); // Reset
        v1.setCol(4); // Reset

        // Test copy constructor
        assert v3.getRow() == 3 && v3.getCol() == 4 : "Copy constructor failed";

        // Test add method
        Vector1by2 sum = v1.add(v2);
        assert sum.getRow() == 4 && sum.getCol() == 6 : "Add method failed";

        // Test subtract method
        Vector1by2 diff = v1.subtract(v2);
        assert diff.getRow() == 2 && diff.getCol() == 2 : "Subtract method failed";

        // Test scalar multiply
        Vector1by2 scaled = v1.multiply(2);
        assert scaled.getRow() == 6 && scaled.getCol() == 8 : "Scalar multiply failed";

        // Test toString
        String str = v1.toString();
        assert str.equals("(3.0, 4.0)") : "toString failed: " + str;

        System.out.println("✓ All Vector1by2 tests passed");
    }

    // ========================= Matrix2by2 Tests =========================
    public static void testMatrixClass() {
        Matrix2by2 m1 = new Matrix2by2(1, 2, 3, 4);

        // Test getters
        assert m1.getA00() == 1 : "Getter test failed for A00";
        assert m1.getA01() == 2 : "Getter test failed for A01";
        assert m1.getA10() == 3 : "Getter test failed for A10";
        assert m1.getA11() == 4 : "Getter test failed for A11";

        // Test setters
        m1.setA00(5);
        m1.setA01(6);
        m1.setA10(7);
        m1.setA11(8);
        assert m1.getA00() == 5 : "Setter test failed for A00";
        assert m1.getA11() == 8 : "Setter test failed for A11";

        // Reset for further tests
        m1.setMatrix(1, 2, 3, 4);

        // Test rotation matrix (90 degrees)
        Matrix2by2 rot90 = new Matrix2by2();
        rot90.setRotationMatrix(90);
        assert Math.abs(rot90.getA00()) < 0.0001 : "cos(90) should be 0";
        assert Math.abs(rot90.getA01() + 1) < 0.0001 : "-sin(90) should be -1";
        assert Math.abs(rot90.getA10() - 1) < 0.0001 : "sin(90) should be 1";
        assert Math.abs(rot90.getA11()) < 0.0001 : "cos(90) should be 0";

        // Test matrix multiplication
        Matrix2by2 m2 = new Matrix2by2(2, 0, 1, 2);
        Matrix2by2 product = m1.multiply(m2);
        assert product.getA00() == 4 : "Matrix multiplication failed";
        assert product.getA01() == 4 : "Matrix multiplication failed";
        assert product.getA10() == 10 : "Matrix multiplication failed";
        assert product.getA11() == 8 : "Matrix multiplication failed";

        // Test scalar multiplication
        Matrix2by2 scaled = m1.multiply(2);
        assert scaled.getA00() == 2 : "Scalar multiplication failed";
        assert scaled.getA11() == 8 : "Scalar multiplication failed";

        System.out.println("✓ All Matrix2by2 tests passed");
    }

    // ========================= Dot Product Tests =========================
    public static void testDotProduct() {
        Vector1by2 v1 = new Vector1by2(3, 4);
        Vector1by2 v2 = new Vector1by2(1, 2);

        double dot1 = v1.dot(v2);                 // Instance method
        assert dot1 == 11 : "Instance dot product failed: " + dot1;

        double dot2 = Vector1by2.dot(v1, v2);     // Static method
        assert dot2 == 11 : "Static dot product failed: " + dot2;

        Vector1by2 v3 = new Vector1by2(1, 0);
        Vector1by2 v4 = new Vector1by2(0, 1);
        double dot3 = v3.dot(v4);                 // Perpendicular
        assert dot3 == 0 : "Perpendicular vectors dot product should be 0";

        System.out.println("✓ Dot product tests passed");
    }

    // ========================= Matrix-Vector Multiplication Tests =========================
    public static void testMatrixMultiplication() {
        Matrix2by2 rot45 = new Matrix2by2();
        rot45.setRotationMatrix(45);

        Vector1by2 v = new Vector1by2(1, 0);

        // Instance method
        Vector1by2 result1 = rot45.multiply(v);
        double expected = Math.sqrt(2)/2;
        assert Math.abs(result1.getRow() - expected) < 0.0001 : "Instance multiply row failed";
        assert Math.abs(result1.getCol() - expected) < 0.0001 : "Instance multiply col failed";

        // Static method
        Vector1by2 result2 = Matrix2by2.multiply(v, rot45);
        assert Math.abs(result2.getRow() - expected) < 0.0001 : "Static multiply row failed";
        assert Math.abs(result2.getCol() - expected) < 0.0001 : "Static multiply col failed";

        // Identity matrix
        Matrix2by2 identity = new Matrix2by2();
        Vector1by2 result3 = identity.multiply(v);
        assert result3.getRow() == 1 && result3.getCol() == 0 : "Identity multiplication failed";

        System.out.println("✓ Matrix multiplication tests passed");
    }
}
