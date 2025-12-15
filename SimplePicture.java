import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;

/**
 * A class that represents a simple picture. A simple picture may have
 * an associated file name and a title. A simple picture has pixels, 
 * width, and height. A simple picture uses a BufferedImage to 
 * hold the pixels. You can show a simple picture in a 
 * PictureFrame (a JFrame). You can also explore a simple picture.
 * 
 * Author: Barb Ericson ericson@cc.gatech.edu
 */
public class SimplePicture implements DigitalPicture {

    /////////////////////// Fields /////////////////////////

    private String fileName;
    private String title;
    private BufferedImage bufferedImage;
    private PictureFrame pictureFrame;
    private String extension;

    /////////////////////// Constructors /////////////////////////

    public SimplePicture() {
        this(200, 100);
    }

    public SimplePicture(String fileName) {
        load(fileName);
    }

    public SimplePicture(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        title = "None";
        fileName = "None";
        extension = "jpg";
        setAllPixelsToAColor(Color.white);
    }

    public SimplePicture(int width, int height, Color theColor) {
        this(width, height);
        setAllPixelsToAColor(theColor);
    }

    public SimplePicture(SimplePicture copyPicture) {
        if (copyPicture.fileName != null) {
            this.fileName = new String(copyPicture.fileName);
            this.extension = copyPicture.extension;
        }
        if (copyPicture.title != null)
            this.title = new String(copyPicture.title);

        if (copyPicture.bufferedImage != null) {
            this.bufferedImage = new BufferedImage(
                copyPicture.getWidth(),
                copyPicture.getHeight(),
                BufferedImage.TYPE_INT_RGB
            );
            this.copyPicture(copyPicture);
        }
    }

    public SimplePicture(BufferedImage image) {
        this.bufferedImage = image;
        title = "None";
        fileName = "None";
        extension = "jpg";
    }

    ////////////////////////// Methods //////////////////////////////////

    public String getExtension() { return extension; }

    public void copyPicture(SimplePicture sourcePicture) {
        for (int sourceX = 0, targetX = 0; sourceX < sourcePicture.getWidth() && targetX < this.getWidth(); sourceX++, targetX++) {
            for (int sourceY = 0, targetY = 0; sourceY < sourcePicture.getHeight() && targetY < this.getHeight(); sourceY++, targetY++) {
                Pixel sourcePixel = sourcePicture.getPixel(sourceX, sourceY);
                Pixel targetPixel = this.getPixel(targetX, targetY);
                targetPixel.setColor(sourcePixel.getColor());
            }
        }
    }

    public void setAllPixelsToAColor(Color color) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                getPixel(x, y).setColor(color);
            }
        }
    }

    public BufferedImage getBufferedImage() { return bufferedImage; }

    public Graphics getGraphics() { return bufferedImage.getGraphics(); }

    public Graphics2D createGraphics() { return bufferedImage.createGraphics(); }

    public String getFileName() { return fileName; }

    public void setFileName(String name) { fileName = name; }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
        if (pictureFrame != null)
            pictureFrame.setTitle(title);
    }

    public int getWidth() { return bufferedImage.getWidth(); }

    public int getHeight() { return bufferedImage.getHeight(); }

    public PictureFrame getPictureFrame() { return pictureFrame; }

    public void setPictureFrame(PictureFrame pictureFrame) { this.pictureFrame = pictureFrame; }

    public Image getImage() { return bufferedImage; }

    public int getBasicPixel(int x, int y) { return bufferedImage.getRGB(x, y); }

    public void setBasicPixel(int x, int y, int rgb) { bufferedImage.setRGB(x, y, rgb); }

    public Pixel getPixel(int x, int y) { return new Pixel(this, x, y); }

    public Pixel[] getPixels() {
        int width = getWidth();
        int height = getHeight();
        Pixel[] pixelArray = new Pixel[width * height];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                pixelArray[row * width + col] = new Pixel(this, col, row);
        return pixelArray;
    }

    public Pixel[][] getPixels2D() {
        int width = getWidth();
        int height = getHeight();
        Pixel[][] pixelArray = new Pixel[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                pixelArray[row][col] = new Pixel(this, col, row);
        return pixelArray;
    }

    public void load(Image image) {
        Graphics2D graphics2d = bufferedImage.createGraphics();
        graphics2d.drawImage(image, 0, 0, null);
        show();
    }

    public void show() {
        if (pictureFrame != null)
            pictureFrame.updateImageAndShowIt();
        else
            pictureFrame = new PictureFrame(this);
    }

    public void hide() {
        if (pictureFrame != null)
            pictureFrame.setVisible(false);
    }

    public void setVisible(boolean flag) {
        if (flag) show();
        else hide();
    }

    public void explore() { new PictureExplorer(new SimplePicture(this)); }

    public void repaint() {
        if (pictureFrame != null)
            pictureFrame.repaint();
        else
            pictureFrame = new PictureFrame(this);
    }

    // ====================== FOLDER-FALLBACK LOAD =====================
    public boolean load(String fileName) {
        try {
            if (!loadOrFailSafe(fileName)) {
                if (!loadOrFailSafe("lib/" + fileName)) {
                    if (!loadOrFailSafe("lib1/" + fileName)) {
                        throw new IOException("File not found in default folders");
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Error loading " + fileName + ": " + ex.getMessage());
            bufferedImage = new BufferedImage(600, 200, BufferedImage.TYPE_INT_RGB);
            addMessage("Couldn't load " + fileName, 5, 100);
            return false;
        }
    }

    private boolean loadOrFailSafe(String fileName) {
        try {
            loadOrFail(fileName);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void loadOrFail(String fileName) throws IOException {
        this.fileName = fileName;
        int posDot = fileName.indexOf('.');
        if (posDot >= 0)
            this.extension = fileName.substring(posDot + 1);

        if (title == null)
            title = fileName;

        File file = new File(this.fileName);
        if (!file.canRead()) {
            file = new File(FileChooser.getMediaPath(this.fileName));
            if (!file.canRead())
                throw new IOException(this.fileName + " could not be opened. Check the path");
        }
        bufferedImage = ImageIO.read(file);
    }

    public boolean loadPictureAndShowIt(String fileName) {
        boolean result = load(fileName);
        show();
        return result;
    }

    public void writeOrFail(String fileName) throws IOException {
        String extension = this.extension;
        File file = new File(fileName);
        File fileLoc = file.getParentFile();
        if (fileLoc == null) {
            fileName = FileChooser.getMediaPath(fileName);
            file = new File(fileName);
            fileLoc = file.getParentFile();
        }

        if (!fileLoc.canWrite())
            throw new IOException(fileName + " could not be opened. Check write permissions.");

        int posDot = fileName.indexOf('.');
        if (posDot >= 0)
            extension = fileName.substring(posDot + 1);

        ImageIO.write(bufferedImage, extension, file);
    }

    public boolean write(String fileName) {
        try {
            writeOrFail(fileName);
            return true;
        } catch (Exception ex) {
            System.out.println("There was an error trying to write " + fileName);
            ex.printStackTrace();
            return false;
        }
    }

    public static String getMediaPath(String fileName) { return FileChooser.getMediaPath(fileName); }

    public void addMessage(String message, int xPos, int yPos) {
        Graphics2D graphics2d = bufferedImage.createGraphics();
        graphics2d.setPaint(Color.white);
        graphics2d.setFont(new Font("Helvetica", Font.BOLD, 16));
        graphics2d.drawString(message, xPos, yPos);
    }

    public void drawString(String text, int xPos, int yPos) { addMessage(text, xPos, yPos); }

    public Picture scale(double xFactor, double yFactor) {
        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.scale(xFactor, yFactor);

        Picture result = new Picture((int) (getWidth() * xFactor), (int) (getHeight() * yFactor));
        Graphics2D g2 = (Graphics2D) result.getGraphics();
        g2.drawImage(this.getImage(), scaleTransform, null);
        return result;
    }

    public Picture getPictureWithWidth(int width) {
        double xFactor = (double) width / this.getWidth();
        return scale(xFactor, xFactor);
    }

    public Picture getPictureWithHeight(int height) {
        double yFactor = (double) height / this.getHeight();
        return scale(yFactor, yFactor);
    }

    @Override
    public String toString() {
        return "Simple Picture, filename " + fileName +
               " height " + getHeight() + " width " + getWidth();
    }

} // end of SimplePicture
