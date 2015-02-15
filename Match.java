/**
 * Defines an object representing a single match
 */

public class Match {
    /**
     * instance variables
     */
    private final int weekNumber;
    private final String matchLevel;
    private String matchArea;
    private final String refOne;
    private final String refTwo;

    /**
     * Constructor for Match class
     *
     * @param week week number
     * @param area area code
     * @param isSenior boolean that represents referee level
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

        matchLevel = isSenior ? "Senior" : "Junior";

        refOne = firstRef;
        refTwo = secondRef;
    }

    /**
     * Accessor method for week number
     */
    public int getWeekNo() {
        return weekNumber;
    }

    /**
     * Accessor method for match level
     */
    public String getLevel() {
        return matchLevel;
    }

    /**
     * Accessor method for area of match
     */
    public String getArea() {
        return matchArea;
    }

    /**
     * Constructor and accessor method for refOne
     */
    public String getRefOne() {
        return refOne;
    }

    /**
     * Constructor and accessor method for refTwo
     */
    public String getRefTwo() {
        return refTwo;
    }

    /**
     * Method to get formatted line for match report document
     */
    public String getMatchLine() {
        return String.format("%-8d%-12s%-12s%-20s%-20s%n", weekNumber, matchLevel, matchArea, refOne, refTwo);
    }
}

