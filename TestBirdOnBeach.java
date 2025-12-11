import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class TestBirdOnBeach {
    public static void main(String[] args) {
        // Load the background image from the lib folder
        SimplePicture beach = new SimplePicture("lib/beach.jpg");

        // Load the smaller image from the lib2 folder
        SimplePicture bird = new SimplePicture("lib2/bird.png");

        // Step 1: Rotate the bird image (before removing background)
        bird = rotateImage(bird, 45); // rotate 45 degrees clockwise

        // Remove the white background of the bird
        Pixel[][] birdPixels = bird.getPixels2D();
        for (int r = 0; r < birdPixels.length; r++) {
            for (int c = 0; c < birdPixels[0].length; c++) {
                Pixel p = birdPixels[r][c];
                // If the pixel is nearly white, make it transparent by setting it to background color
                if (p.getRed() > 250 && p.getGreen() > 250 && p.getBlue() > 250) {
                    p.setColor(new java.awt.Color(255, 255, 255, 0)); // optional: treat as transparent
                }
            }
        }

        // Add the bird to the beach at position (50, 50)
        Pixel[][] beachPixels = beach.getPixels2D();
        for (int r = 0; r < birdPixels.length; r++) {
            for (int c = 0; c < birdPixels[0].length; c++) {
                int beachRow = r + 50;
                int beachCol = c + 50;
                if (beachRow < beachPixels.length && beachCol < beachPixels[0].length) {
                    Pixel birdPixel = birdPixels[r][c];
                    // Only copy non-white pixels
                    if (!(birdPixel.getRed() > 250 && birdPixel.getGreen() > 250 && birdPixel.getBlue() > 250)) {
                        Pixel beachPixel = beachPixels[beachRow][beachCol];
                        beachPixel.setColor(birdPixel.getColor());
                    }
                }
            }
        }

        // Show the final image
        beach.show();

        // Save the final image to the output folder (or main folder)
        beach.write("beach_with_bird.jpg");
        System.out.println("Saved beach_with_bird.jpg");
    }

    // Method to rotate a SimplePicture by a given degree (returns new SimplePicture)
    private static SimplePicture rotateImage(SimplePicture img, double degrees) {
        double radians = Math.toRadians(degrees);
        int w = img.getWidth();
        int h = img.getHeight();

        SimplePicture rotated = new SimplePicture(w, h);
        Graphics2D g2d = rotated.getBufferedImage().createGraphics();
        AffineTransform at = new AffineTransform();
        at.rotate(radians, w / 2.0, h / 2.0); // rotate around center
        g2d.drawImage(img.getBufferedImage(), at, null);
        g2d.dispose();
        return rotated;
    }
}
