import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The file processor contains static methods to read and write files
 */
public class FileProcessor {

    private FileProcessor() {
    }

    /**
    * Retrieves information about the referees from a given files and creates a referee which is added to the RefList object
    * @param refereesInFile the name of the file which contains the ref information
    * @param referees the list of all the referee objects that have been made
    */
    public static void readIn(String refereesInFile, RefList referees) {
        try (Scanner in = new Scanner(new FileReader(refereesInFile))){
            while (in.hasNextLine()) {
                referees.addRefFromFile(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not find the " + refereesInFile + " file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Write referee and match details to a file
     *
     * @param fileOutName Output file
     * @param textToWrite String to be written to fileOutName
     * @return
     */
    public static boolean writeFileOut(String fileOutName, String textToWrite) {
        try (PrintWriter writer = new PrintWriter(fileOutName)) {
            writer.write(textToWrite);
            return true; //IO operations were successful
        }
        catch (IOException e) {
            return false; //IO operations were not successful
        }
    }
}
