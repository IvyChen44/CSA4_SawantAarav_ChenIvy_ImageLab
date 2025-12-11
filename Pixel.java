import java.awt.Color;

/**
 * Class that references a pixel in a picture. Pixel 
 * stands for picture element where picture is 
 * abbreviated pix.  A pixel has a column (x) and 
 * row (y) location in a picture.  A pixel knows how 
 * to get and set the red, green, blue, and alpha 
 * values in the picture.  A pixel also knows how to get 
 * and set the color using a Color object.
 * 
 * @author Barb Ericson ericson@cc.gatech.edu
 */
public class Pixel
{
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

  public int getAlpha() {
    int value = picture.getBasicPixel(x,y);
    return (value >> 24) & 0xff;
  }

  public int getRed() { 
    int value = picture.getBasicPixel(x,y);
    return (value >> 16) & 0xff;
  }

  public static int getRed(int value) { return (value >> 16) & 0xff; }

  public int getGreen() { 
    int value = picture.getBasicPixel(x,y);
    return (value >> 8) & 0xff;
  }

  public static int getGreen(int value) { return (value >> 8) & 0xff; }

  public int getBlue() { 
    int value = picture.getBasicPixel(x,y);
    return value & 0xff;
  }

  public static int getBlue(int value) { return value & 0xff; }

  public Color getColor() { 
    int value = picture.getBasicPixel(x,y);
    int red = (value >> 16) & 0xff;
    int green = (value >> 8) & 0xff;
    int blue = value & 0xff;
    return new Color(red,green,blue);
  }

  public void setColor(Color newColor) {
    updatePicture(this.getAlpha(), newColor.getRed(), newColor.getGreen(), newColor.getBlue());
  }

  public void updatePicture(int alpha, int red, int green, int blue) {
    int value = (alpha << 24) + (red << 16) + (green << 8) + blue;
    picture.setBasicPixel(x,y,value);
  }

  public static int correctValue(int value)
{
  if (value < 0)
    value = 0;
  if (value > 255)
    value = 255;
  return value;
}


  public void setRed(int value) { updatePicture(getAlpha(), correctValue(value), getGreen(), getBlue()); }
  public void setGreen(int value) { updatePicture(getAlpha(), getRed(), correctValue(value), getBlue()); }
  public void setBlue(int value) { updatePicture(getAlpha(), getRed(), getGreen(), correctValue(value)); }
  public void setAlpha(int value) { updatePicture(correctValue(value), getRed(), getGreen(), getBlue()); }

  public double colorDistance(Color testColor) {
    double redDistance = this.getRed() - testColor.getRed();
    double greenDistance = this.getGreen() - testColor.getGreen();
    double blueDistance = this.getBlue() - testColor.getBlue();
    return Math.sqrt(redDistance * redDistance + greenDistance * greenDistance + blueDistance * blueDistance);
  }

  public static double colorDistance(Color color1, Color color2) {
    double redDistance = color1.getRed() - color2.getRed();
    double greenDistance = color1.getGreen() - color2.getGreen();
    double blueDistance = color1.getBlue() - color2.getBlue();
    return Math.sqrt(redDistance * redDistance + greenDistance * greenDistance + blueDistance * blueDistance);
  }

  public double getAverage() { return (getRed() + getGreen() + getBlue()) / 3.0; }

  public String toString() {
    return "Pixel row=" + getRow() + " col=" + getCol() + " red=" + getRed() + " green=" + getGreen() + " blue=" + getBlue();
  }

  /** Rotate the color using a rotation matrix */
  public void rotateColor(Matrix rotation) {
    Vector colorVector = new Vector(getRed(), getGreen(), getBlue());
    Vector rotated = rotation.multiply(colorVector);
    setRed(correctValue((int)rotated.getX()));
    setGreen(correctValue((int)rotated.getY()));
    setBlue(correctValue((int)rotated.getZ()));
  }

  /** Check if pixel is essentially white */
  public boolean isWhite() {
    return getRed() > 250 && getGreen() > 250 && getBlue() > 250;
  }
}
