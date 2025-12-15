import java.awt.Color;

/**
 * Pixel represents a single picture element in a DigitalPicture.
 */
public class Pixel {

    private DigitalPicture picture;
    private int x;
    private int y;

    public Pixel(DigitalPicture picture, int x, int y) {
        this.picture = picture;
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getRow() { return y; }
    public int getCol() { return x; }

    public int getRed() {
        int value = picture.getBasicPixel(x, y);
        return (value >> 16) & 0xff;
    }

    public int getGreen() {
        int value = picture.getBasicPixel(x, y);
        return (value >> 8) & 0xff;
    }

    public int getBlue() {
        int value = picture.getBasicPixel(x, y);
        return value & 0xff;
    }

    public Color getColor() { return new Color(getRed(), getGreen(), getBlue()); }

    public void setColor(Color newColor) { updatePicture(newColor.getRed(), newColor.getGreen(), newColor.getBlue()); }

    public void setRed(int value) { updatePicture(value, getGreen(), getBlue()); }
    public void setGreen(int value) { updatePicture(getRed(), value, getBlue()); }
    public void setBlue(int value) { updatePicture(getRed(), getGreen(), value); }

    private void updatePicture(int red, int green, int blue) {
        int rgb = (red << 16) | (green << 8) | blue;
        picture.setBasicPixel(x, y, rgb);
    }

    public double colorDistance(Color testColor) {
        double rDiff = getRed() - testColor.getRed();
        double gDiff = getGreen() - testColor.getGreen();
        double bDiff = getBlue() - testColor.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }

    public double getAverage() { return (getRed() + getGreen() + getBlue()) / 3.0; }

    @Override
    public String toString() {
        return String.format("Pixel row=%d col=%d red=%d green=%d blue=%d",
                getRow(), getCol(), getRed(), getGreen(), getBlue());
    }
}
