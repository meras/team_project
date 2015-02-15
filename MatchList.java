import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Maintains a list of Match objects
 * The methods allow Matches to be added to the list
 */
public class MatchList implements Iterable<Match> {
    public static final int MAX_MATCHES = 52;
    private final List<Match> matchList;

    /**
     * Constructor for MatchList class
     */
    public MatchList() {
        matchList = new ArrayList<>();
    }

    /**
     * Checks if a given week can be allocated
     * @param week week number to check
     * @return true if week is not allocated, false when allocation exists
     */
    public boolean checkWeekAllocation(int week) {
        return (getMatch(week) == null);
    }

    //TODO temporary name
	/**
	 * Takes match details, makes new match object and adds it to matchList
	 * @param week the week the match is in
	 * @param loc the match location
	 * @param senior a boolean indicating whether match is senior or not
	 * @param ref1Nm the full name of the first ref allocated to the match
	 * @param ref2Nm the full name of the second ref allocated to the match
	 */
    public void addMatch(int week, int loc, boolean senior, String ref1Nm, String ref2Nm) {
        Match newMatch = new Match(week, loc, senior, ref1Nm, ref2Nm);
        matchList.add(newMatch);
    }

    /**
     * get number of matches currently in the match list
     */
    public int getNoMatches() {
        return matchList.size();
    }

    private Match getMatch(int matchWeekNo) {
        for (Match match : matchList) {
            if (match.getWeekNo() == matchWeekNo) {
                return match;
            }
        }
        return null;
    }
	
	public String getMatchAllocsText() {
        String title = "Match details\r\n\r\n";
        String tableHeader = String.format("%-8s%-12s%-12s%-20s%-20s%n%n", "Week", "Level", "Area", "Referee 1", "Referee 2");

        StringBuilder matchesOutBuilder = new StringBuilder();
		for(Match match : matchList) {
			matchesOutBuilder.append(match.getMatchLine());
		}
		
		return title + tableHeader + matchesOutBuilder;
	}

    /**
     * Returns an iterator over elements of type Match.
     *
     * @return an Iterator.
     */
    public Iterator<Match> iterator() {
        return matchList.iterator();
    }
}