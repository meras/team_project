import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * The file processor contains static methods to read and write files
 */
public class FileProcessor {

    private FileProcessor() {
    }

    /**
    * Retrieves information about the referees from a given files and creates a referee which is added to the RefList object
    * @param refereesInFile the name of the file which contains the ref information
    * @param referees       the list of all the referee objects that have been made
    */
    public static void readIn(String refereesInFile, RefList referees) {
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(refereesInFile));
            while (in.hasNextLine()) {
                referees.addRefFromFile(in.nextLine());
            }
        } catch (FileNotFoundException e) {
        //    JOptionPane.showMessageDialog("Could not find the " + refereesInFile + " file.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Prints the information about the referees and the matchs
     * @param matchAllocsFile   the name of the file which is to be created
     */
    public static boolean writeFileOut(String fileOutName, String textToWrite) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(fileOutName);

            writer.write(textToWrite);
            return true; //IO operations were successful
        }
        catch (IOException e) {
            //showError("I/O exception: " + e.getMessage());
            return false; //IO operations were not successful
        } 
        finally {
            if (writer != null)
                writer.close();
        }
    }
}
