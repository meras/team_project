import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * The file processor contains static methods to read and write files
 * TODO this can be incorporated into main gui
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
            JOptionPane.showMessageDialog("Could not find the " + refereesInFile + " file.", errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
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
    public static void saveAndClose(String matchAllocsFile) {

        MatchList matchList = new MatchList();

        PrintWriter out = null;

        try {
            out = new PrintWriter(matchAllocsFile);

            StringBuilder matchAllocsOut = new StringBuilder();

            for (int i = 0; i < matchList.getNoMatches(); i++) {
                //TODO get match and referee info to append
                matchAllocsOut.append("");
                matchAllocsOut.append(System.lineSeparator());
            }

            out.print(matchAllocsOut.toString());

        } catch (IOException e) {
            //showError("I/O exception: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        //might move system exit to main gui to reduce <<coupling>> between classes
        System.exit(0);
    }
}
