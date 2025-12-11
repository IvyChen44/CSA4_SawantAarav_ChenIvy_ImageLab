import java.awt.Color;

public class ImageApp
{
  public static void main(String[] args)
  {
    // use any file from the lib folder
    String pictureFile = "lib/beach.jpg";

    // Load original image
    Picture origImg = new Picture(pictureFile);
    Pixel[][] origPixels = origImg.getPixels2D();
    System.out.println(origPixels[0][0].getColor());
    origImg.explore();


    // ============================================================
    // Image #1 – Recolor every pixel (example: tint red)
    // ============================================================
    Picture recoloredImg = new Picture(pictureFile);
    Pixel[][] recoloredPixels = recoloredImg.getPixels2D();

    for (int r = 0; r < recoloredPixels.length; r++) {
      for (int c = 0; c < recoloredPixels[0].length; c++) {
        Pixel p = recoloredPixels[r][c];
        int red = Math.min(255, p.getRed() + 50);
        int green = p.getGreen();
        int blue = p.getBlue();
        p.setColor(new Color(red, green, blue));
      }
    }
    recoloredImg.explore();


    // ============================================================
    // Image #2 – Negative
    // ============================================================
    Picture negImg = new Picture(pictureFile);
    Pixel[][] negPixels = negImg.getPixels2D();

    for (int r = 0; r < negPixels.length; r++) {
      for (int c = 0; c < negPixels[0].length; c++) {
        Pixel p = negPixels[r][c];
        p.setColor(new Color(255 - p.getRed(),
                             255 - p.getGreen(),
                             255 - p.getBlue()));
      }
    }
    negImg.explore();


    // ============================================================
    // Image #3 – Grayscale
    // ============================================================
    Picture grayscaleImg = new Picture(pictureFile);
    Pixel[][] grayscalePixels = grayscaleImg.getPixels2D();

    for (int r = 0; r < grayscalePixels.length; r++) {
      for (int c = 0; c < grayscalePixels[0].length; c++) {
        Pixel p = grayscalePixels[r][c];
        int avg = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
        p.setColor(new Color(avg, avg, avg));
      }
    }
    grayscaleImg.explore();


    // ============================================================
    // Image #4 – Rotate 180 degrees
    // ============================================================
    Picture upsidedownImage = new Picture(pictureFile);
    Pixel[][] upsideDownPixels = upsidedownImage.getPixels2D();

    int h = upsideDownPixels.length;
    int w = upsideDownPixels[0].length;

    for (int r = 0; r < h; r++) {
      for (int c = 0; c < w; c++) {
        upsideDownPixels[r][c].setColor(
          origPixels[h - 1 - r][w - 1 - c].getColor()
        );
      }
    }
    upsidedownImage.explore();


    // ============================================================
    // Image #5 – Rotate +90 degrees (right)
    // ============================================================
    Picture rotateImg = new Picture(origImg.getHeight(), origImg.getWidth());
    Pixel[][] rotatePixels = rotateImg.getPixels2D();

    for (int r = 0; r < origPixels.length; r++) {
      for (int c = 0; c < origPixels[0].length; c++) {
        rotatePixels[c][origPixels.length - 1 - r].setColor(
          origPixels[r][c].getColor()
        );
      }
    }
    rotateImg.explore();


    // ============================================================
    // Image #6 – Rotate -90 degrees (left)
    // ============================================================
    Picture rotateImg2 = new Picture(origImg.getHeight(), origImg.getWidth());
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();

    for (int r = 0; r < origPixels.length; r++) {
      for (int c = 0; c < origPixels[0].length; c++) {
        rotatePixels2[origPixels[0].length - 1 - c][r].setColor(
          origPixels[r][c].getColor()
        );
      }
    }
    rotateImg2.explore();


    // ============================================================
    // Final Image – Add a small image into a larger one
    // ============================================================
    Picture finalImg = new Picture(pictureFile);
    Picture small = new Picture("lib/smallbeach.jpg");

    Pixel[][] big = finalImg.getPixels2D();
    Pixel[][] tiny = small.getPixels2D();

    int startRow = 50;
    int startCol = 50;

    for (int r = 0; r < tiny.length; r++) {
      for (int c = 0; c < tiny[0].length; c++) {

        if (startRow + r < big.length && startCol + c < big[0].length) {
          big[startRow + r][startCol + c].setColor(
            tiny[r][c].getColor()
          );
        }
      }
    }

    finalImg.explore();
  }
}
