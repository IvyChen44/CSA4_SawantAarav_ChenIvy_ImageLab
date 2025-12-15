/**
 * Vector1by2 class represents a 2D vector with row and column components.
 * This class is used for pixel coordinates in image transformations.
 */
public class Vector1by2 {

    // ========================= Fields =========================
    private double row;
    private double col;

    // ========================= Constructors =========================

    /**
     * Constructor that creates a vector with given row and column values
     * @param row the row component
     * @param col the column component
     */
    public Vector1by2(double row, double col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Default constructor creates a zero vector
     */
    public Vector1by2() {
        this(0, 0);
    }

    /**
     * Copy constructor creates a copy of another vector
     * @param other the vector to copy
     */
    public Vector1by2(Vector1by2 other) {
        this(other.row, other.col);
    }

    // ========================= Getters =========================
    public double getRow() { return row; }
    public double getCol() { return col; }

    // ========================= Setters =========================
    public void setRow(double row) { this.row = row; }
    public void setCol(double col) { this.col = col; }

    // ========================= Vector Operations =========================

    /**
     * Adds another vector to this vector
     * @param other the vector to add
     * @return a new vector that is the sum
     */
    public Vector1by2 add(Vector1by2 other) {
        return new Vector1by2(this.row + other.row, this.col + other.col);
    }

    /**
     * Subtracts another vector from this vector
     * @param other the vector to subtract
     * @return a new vector that is the difference
     */
    public Vector1by2 subtract(Vector1by2 other) {
        return new Vector1by2(this.row - other.row, this.col - other.col);
    }

    /**
     * Multiplies this vector by a scalar
     * @param scalar the scalar to multiply by
     * @return a new scaled vector
     */
    public Vector1by2 multiply(double scalar) {
        return new Vector1by2(this.row * scalar, this.col * scalar);
    }

    /**
     * Calculates the dot product of this vector with another vector
     * @param other the other vector
     * @return the dot product as a double
     */
    public double dot(Vector1by2 other) {
        return this.row * other.row + this.col * other.col;
    }

    /**
     * Static method to calculate dot product of two vectors
     * @param v1 first vector
     * @param v2 second vector
     * @return the dot product
     */
    public static double dot(Vector1by2 v1, Vector1by2 v2) {
        return v1.dot(v2);
    }

    // ========================= Utility Methods =========================

    /**
     * Returns a string representation of the vector
     * @return string in format "(row, col)"
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    /**
     * Checks if this vector equals another object
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector1by2 other = (Vector1by2) obj;
        return Math.abs(row - other.row) < 0.0001 && Math.abs(col - other.col) < 0.0001;
    }
}
