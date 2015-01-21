import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The file processor contains static methods to read and write files
 * TODO this can be incorporated into main gui
 */
public class FileProcessor {

    public static void readIn(String refereesInFile) {
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(refereesInFile));
            while (in.hasNextLine()) {
                //TODO create referee instances
            }
        } catch (FileNotFoundException e) {
            //TODO joptionpane here
        } finally {
            if (in != null) { in.close(); }
        }
    }

    /**
     *
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
            if (out != null) { out.close(); }
        }
        //might move system exit to main gui to reduce coupling between classes
        System.exit(0);
    }
}
