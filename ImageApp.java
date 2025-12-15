/*
  ImageApp: Main application for image processing operations
  Includes recoloring, rotation, and image compositing
*/
import java.awt.Color;
import java.util.Scanner;
import java.io.File;

public class ImageApp
{
  private static Scanner scanner = new Scanner(System.in);
  
  public static void main(String[] args)
  {
    System.out.println("=== IMAGE PROCESSING APP ===");
    System.out.println("Current working directory: " + System.getProperty("user.dir"));
    
    boolean running = true;
    while (running) {
      displayMenu();
      int choice = getIntInput("Enter your choice (1-11): ");
      
      switch (choice) {
        case 1:  testOriginalImage();              break;
        case 2:  recolorImage();                   break;
        case 3:  createNegativeImage();            break;
        case 4:  createGrayscaleImage();           break;
        case 5:  rotate180();                      break;
        case 6:  rotate90Counterclockwise();       break;
        case 7:  rotate90Clockwise();              break;
        case 8:  insertImageInteractive();         break;
        case 9:  testVectorMatrixOperations();     break;
        case 10: test2DArrayAlgorithms();          break;
        case 11: running = false; 
                 System.out.println("Goodbye!");    break;
        default: System.out.println("Invalid choice. Please try again.");
      }
      
      if (choice != 11) {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
      }
    }
    scanner.close();
  }
  
  /* ----------  MENU & INPUT  ---------- */
  private static void displayMenu() {
    System.out.println("\n=== MAIN MENU ===");
    System.out.println("1. View Original Image");
    System.out.println("2. Recolor Image (BRG)");
    System.out.println("3. Create Negative Image");
    System.out.println("4. Create Grayscale Image");
    System.out.println("5. Rotate 180 Degrees");
    System.out.println("6. Rotate 90° Counterclockwise");
    System.out.println("7. Rotate 90° Clockwise");
    System.out.println("8. Insert Small Image onto Large Image");
    System.out.println("9. Test Vector/Matrix Operations");
    System.out.println("10. Test 2D Array Algorithms");
    System.out.println("11. Exit");
    System.out.println("=================");
  }
  
  private static int getIntInput(String prompt) {
    System.out.print(prompt);
    while (!scanner.hasNextInt()) {
      System.out.println("Please enter a valid number!");
      scanner.next();
      System.out.print(prompt);
    }
    int input = scanner.nextInt();
    scanner.nextLine(); // consume newline
    return input;
  }
  
  private static String getStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }
  
  /* ----------  LOADER WITH FALLBACK  ---------- */
  private static Picture loadPicture(String filename) {
    try {
      System.out.println("Loading: " + filename);
      File file = new File(filename);
      if (!file.exists()) {
        System.out.println("ERROR: File not found: " + filename);
        System.out.println("Looked in: " + file.getAbsolutePath());
        return null;
      }
      Picture picture = new Picture(filename);
      System.out.println("Loaded: " + filename + "  (" + 
                         picture.getWidth() + "×" + picture.getHeight() + ")");
      return picture;
    } catch (Exception e) {
      System.out.println("ERROR loading " + filename + ": " + e.getMessage());
      return null;
    }
  }
  
  /* ----------  MENU ACTIONS  ---------- */
  private static void testOriginalImage() {
    String path = getImageFileChoice();
    Picture p = loadPicture(path);
    if (p == null) return;
    p.explore();
  }
  
  private static void recolorImage() {
    processImage(ImageApp::changeColor);
  }
  
  private static void createNegativeImage() {
    processImage(ImageApp::negativeColor);
  }
  
  private static void createGrayscaleImage() {
    processImage(ImageApp::grayscale);
  }
  
  private static void rotate180() {
    processImage(ImageApp::rotate180);
  }
  
  private static void rotate90Counterclockwise() {
    String path = getImageFileChoice();
    Picture p = loadPicture(path);
    if (p == null) return;
    Pixel[][] rotated = rotate90(p.getPixels2D());
    Picture out = pixelsToPicture(rotated);
    out.explore();
    savePrompt(out);
  }
  
  private static void rotate90Clockwise() {
    String path = getImageFileChoice();
    Picture p = loadPicture(path);
    if (p == null) return;
    Pixel[][] rotated = rotateNeg90(p.getPixels2D());
    Picture out = pixelsToPicture(rotated);
    out.explore();
    savePrompt(out);
  }
  
  private static void insertImageInteractive() {
    System.out.println("\n=== INSERT IMAGE ===");
    System.out.println("Choose large image:");
    Picture large = loadPicture(getImageFileChoice());
    if (large == null) return;
    
    System.out.println("Choose small image:");
    Picture small = loadPicture(getImageFileChoice());
    if (small == null) return;
    
    int row = getIntInput("Start row: ");
    int col = getIntInput("Start column: ");
    
    insertImage(large.getPixels2D(), small.getPixels2D(), row, col);
    large.explore();
    savePrompt(large);
  }
  
  /* ----------  HELPERS  ---------- */
  private static void processImage(PixelProcessor proc) {
    String path = getImageFileChoice();
    Picture p = loadPicture(path);
    if (p == null) return;
    proc.apply(p.getPixels2D());
    p.explore();
    savePrompt(p);
  }
  
  private static void savePrompt(Picture pic) {
    if (getStringInput("Save image? (yes/no): ").equalsIgnoreCase("yes")) {
      String name = getStringInput("Filename (no extension): ");
      pic.write(name + ".jpg");
    }
  }
  
  /* ----------  IMAGE FILE CHOICE WITH FALLBACK  ---------- */
  private static String getImageFileChoice() {
    System.out.println("\nAvailable images:");
    System.out.println("1. beach.jpg   (lib/ or images/)");
    System.out.println("2. robot.jpg   (lib/ or images/)");
    System.out.println("3. swan.jpg    (lib/ or images/)");
    System.out.println("4. caterpillar.jpg (lib/ or images/)");
    System.out.println("5. flower1.jpg (lib/ or images/)");
    System.out.println("6. flower2.jpg (lib/ or images/)");
    System.out.println("7. temple.jpg  (lib/ or images/)");
    System.out.println("8. Enter custom filename");
    System.out.println("9. Default test image (640x480.jpg)");
    
    int choice = getIntInput("Choose (1-9): ");
    switch (choice) {
      case 1: return checkFileExists("lib/beach.jpg",   "images/beach.jpg",   "beach.jpg");
      case 2: return checkFileExists("lib/robot.jpg",   "images/robot.jpg",   "robot.jpg");
      case 3: return checkFileExists("lib/swan.jpg",    "images/swan.jpg",    "swan.jpg");
      case 4: return checkFileExists("lib/caterpillar.jpg","images/caterpillar.jpg","caterpillar.jpg");
      case 5: return checkFileExists("lib/flower1.jpg", "images/flower1.jpg", "flower1.jpg");
      case 6: return checkFileExists("lib/flower2.jpg", "images/flower2.jpg", "flower2.jpg");
      case 7: return checkFileExists("lib/temple.jpg",  "images/temple.jpg",  "temple.jpg");
      case 8: return getStringInput("Enter filename: ");
      case 9: return "640x480.jpg";
      default:return checkFileExists("lib/beach.jpg",   "images/beach.jpg",   "beach.jpg");
    }
  }
  
  private static String checkFileExists(String... paths) {
    for (String p : paths) {
      if (new File(p).exists()) {
        System.out.println("Found: " + p);
        return p;
      }
    }
    System.out.println("Warning: none found, trying " + paths[0]);
    return paths[0];
  }
  
  /* ----------  ALGORITHMS  ---------- */
  public static void changeColor(Pixel[][] px) {
    for (Pixel[] row : px)
      for (Pixel p : row) {
        Color c = p.getColor();
        p.setColor(new Color(c.getBlue(), c.getRed(), c.getGreen()));
      }
  }
  
  public static void negativeColor(Pixel[][] px) {
    for (Pixel[] row : px)
      for (Pixel p : row) {
        Color c = p.getColor();
        p.setColor(new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue()));
      }
  }
  
  public static void grayscale(Pixel[][] px) {
    for (Pixel[] row : px)
      for (Pixel p : row) {
        Color c = p.getColor();
        int avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        p.setColor(new Color(avg, avg, avg));
      }
  }
  
  public static void rotate180(Pixel[][] px) {
    int h = px.length, w = px[0].length;
    Pixel[][] tmp = new Pixel[h][w];
    for (int r = 0; r < h; r++)
      for (int c = 0; c < w; c++)
        tmp[h - 1 - r][w - 1 - c] = px[r][c];
    for (int r = 0; r < h; r++)
      System.arraycopy(tmp[r], 0, px[r], 0, w);
  }
  
  public static Pixel[][] rotate90(Pixel[][] px) {
    int h = px.length, w = px[0].length;
    Pixel[][] out = new Pixel[w][h];
    for (int r = 0; r < h; r++)
      for (int c = 0; c < w; c++)
        out[c][h - 1 - r] = px[r][c];
    return out;
  }
  
  public static Pixel[][] rotateNeg90(Pixel[][] px) {
    int h = px.length, w = px[0].length;
    Pixel[][] out = new Pixel[w][h];
    for (int r = 0; r < h; r++)
      for (int c = 0; c < w; c++)
        out[w - 1 - c][r] = px[r][c];
    return out;
  }
  
  public static void insertImage(Pixel[][] base, Pixel[][] ins, int sr, int sc) {
    for (int r = 0; r < ins.length; r++)
      for (int c = 0; c < ins[0].length; c++)
        if (sr + r < base.length && sc + c < base[0].length)
          base[sr + r][sc + c].setColor(ins[r][c].getColor());
  }
  
  public static Picture pixelsToPicture(Pixel[][] px) {
    int h = px.length, w = px[0].length;
    Picture pic = new Picture(h, w);
    Pixel[][] dest = pic.getPixels2D();
    for (int r = 0; r < h; r++)
      for (int c = 0; c < w; c++)
        dest[r][c].setColor(px[r][c].getColor());
    return pic;
  }
  
  /* ----------  2D ARRAY TEST  ---------- */
  private static void test2DArrayAlgorithms() {
    int[][] a = { {1, 2}, {3, 4} };
    print2DArray(a);
    print2DArray(rotate2DArray(a));
  }
  
  public static int[][] rotate2DArray(int[][] a) {
    int[][] r = new int[a[0].length][a.length];
    for (int i = 0; i < a.length; i++)
      for (int j = 0; j < a[0].length; j++)
        r[a[0].length - 1 - j][i] = a[i][j];
    return r;
  }
  
  public static void print2DArray(int[][] a) {
    for (int[] row : a) {
      for (int v : row) System.out.print(v + " ");
      System.out.println();
    }
  }
  
  private static void testVectorMatrixOperations() {
    System.out.println("\n=== VECTOR/MATRIX TEST ===");
    Vector1by2 v1 = new Vector1by2(3, 4), v2 = new Vector1by2(1, 2);
    System.out.println("v1 = " + v1);
    System.out.println("v2 = " + v2);
    System.out.println("v1 + v2 = " + v1.add(v2));
    System.out.println("Dot = " + v1.dot(v2));
    
    Matrix2by2 m = new Matrix2by2();
    m.setRotationMatrix(90);
    System.out.println("Rotate (1,0) 90° → " + m.multiply(new Vector1by2(1, 0)));
  }
  
  /* ----------  FUNCTIONAL INTERFACE  ---------- */
  @FunctionalInterface
  private interface PixelProcessor {
    void apply(Pixel[][] px);
  }
}