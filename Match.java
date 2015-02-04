/**
 * Defines an object representing a single match
 */

public class Match {
    /**
     * instance variables
     */
    private int weekNumber;
    private String matchLevel;
    private String matchArea;
    private String refOne;
    private String refTwo;

    /**
     * Constructor for Match class
     *
     * @param week
     * @param area
     * @param isSenior
     */
    public Match(int week, int area, boolean isSenior, String firstRef, String secondRef) {

        weekNumber = week;
        // area is being passed as an int so have to check which it is and set matchArea String accordingly
        if (area == Referee.NORTH)
            matchArea = "North";
        else if (area == Referee.CENTRAL)
            matchArea = "Central";
        else if (area == Referee.SOUTH)
            matchArea = "South";

        if (isSenior == true) {
            matchLevel = "Senior";
        } else {
            matchLevel = "Junior";
        }
        refOne = firstRef;
        refTwo = secondRef;

        //print match details
        System.out.println("Match details  -  Week No:" + weekNumber + "   Area:" + matchArea + "   Level:" + matchLevel);
    }

    /**
     * Accessor method for week number
     *
     * @return
     */
    public int getWeekNo() {
        return weekNumber;
    }

    /**
     * Accessor method for match level
     *
     * @return
     */
    public String getLevel() {
        return matchLevel;
    }

    /**
     * Accessor method for area of match
     *
     * @return
     */
    public String getArea() {
        return matchArea;
    }

    /**
     * Constructor and accessor method for refOne
     *
     * @return
     */
    public String getRefOne() {
        return refOne;
    }

    /**
     * Constructor and accessor method for refTwo
     *
     * @return
     */
    public String getRefTwo() {
        return refTwo;
    }

    /**
     * Method to get formatted line for match report document
     *
     * @return
     */
    public String getMatchLine() {

        String matchLine = String.format("%d %s %s %s %s%n", weekNumber, matchLevel, matchArea, refOne, refTwo);

        return matchLine;
    }
}

