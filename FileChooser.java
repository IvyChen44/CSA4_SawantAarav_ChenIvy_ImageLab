import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * A class to make working with a file chooser easier for students.
 * It uses a JFileChooser to let the user pick a file and returns
 * the chosen file name.
 *
 * @author Barb Ericson
 */
public class FileChooser {

    /* ====================== Utility Methods ====================== */

    /**
     * Returns the full path for the given file name.
     *
     * @param fileName the name of a file
     * @return the full path to the file
     */
    public static String getMediaPath(String fileName) {
        return getMediaDirectory() + fileName;
    }

    /**
     * Displays a file chooser and returns the selected file path.
     *
     * @param chooser the JFileChooser to use
     * @return the selected file path, or null if canceled
     */
    public static String pickPath(JFileChooser chooser) {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        String path = null;
        int result = chooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
        }

        frame.dispose();
        return path;
    }

    /**
     * Lets the user pick a file and returns the full file name.
     *
     * @return the selected file name, or null if none selected
     */
    public static String pickAFile() {
        String mediaDir = getMediaDirectory();
        JFileChooser chooser = null;

        try {
            File dir = new File(mediaDir);
            if (dir.exists()) {
                chooser = new JFileChooser(dir);
            }
        } catch (Exception ignored) {
        }

        if (chooser == null) {
            chooser = new JFileChooser();
        }

        return pickPath(chooser);
    }

    /**
     * Determines the media directory to use.
     *
     * @return the media directory path
     */
    public static String getMediaDirectory() {

        // Try current working directory first
        try {
            String dir = System.getProperty("user.dir");
            if (new File(dir).exists()) {
                return dir + File.separator;
            }
        } catch (Exception ignored) {
        }

        // Try locating an images directory near the class file
        try {
            URL classURL = FileChooser.class.getResource("FileChooser.class");
            if (classURL != null) {
                URI uri = classURL.toURI();
                File classFile = new File(uri.getPath());
                File imagesDir = new File(classFile.getParentFile(), "images");

                if (imagesDir.exists()) {
                    return imagesDir.getPath() + File.separator;
                }
            }
        } catch (Exception ignored) {
        }

        // Fallback: current directory
        return System.getProperty("user.dir") + File.separator;
    }

    /* ====================== Testing ====================== */

    public static void main(String[] args) {
        System.out.println("Media directory: " + getMediaDirectory());
        System.out.println("Selected file: " + pickAFile());
    }
    /**
 * Returns the first path that exists among the given candidates.
 * Usage: String path = FileChooser.checkExists("lib/beach.jpg", "images/beach.jpg", "beach.jpg");
 */
    public static String checkExists(String... candidates) {
        for (String p : candidates) {
            if (new File(p).exists()) return p;
        }
        return candidates.length > 0 ? candidates[0] : null;
    }
}
