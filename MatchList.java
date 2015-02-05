import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Maintains a list of Match objects
 * The methods allow Matches to be added to the list
 */
public class MatchList implements Iterable<Match>, FileOutable {
    public static final int MAX_MATCHES = 52;
    private List<Match> matchList;

    /**
     * Constructor for MatchList class
     */
    public MatchList() {
        matchList = new ArrayList<Match>();
    }

    public boolean checkWeekAllocation(int week) {
        boolean weekAllocated = true;
        if (getMatch(week) != null) weekAllocated = false;
        return weekAllocated;
    }

    /**
     * add match to match list based on week number
     */
    public void addMatch(Match newMatch) {
        matchList.add(newMatch);
    }

    //TODO temporary name
    public void alternativeAddMatch(int week, int loc, boolean senior, String ref1Nm, String ref2Nm) {
        Match newMatch = new Match(week, loc, senior, ref1Nm, ref2Nm);
        matchList.add(newMatch);
    }

    /**
     * get number of matches currently in the match list
     */
    public int getNoMatches() {
        return matchList.size();
    }

    public Match getMatch(int matchWeekNo) {
        for (Match match : matchList) {
            if (match.getWeekNo() == matchWeekNo) {
                return match;
            }
        }
        return null;
    }
	
	public String getFileOutText() {
		StringBuilder matchesOutBuilder = new StringBuilder();
		
		for(Match match : matchList) {
			String matchLine = match.getMatchLine();
			matchesOutBuilder.append(matchLine);
		}
		
		String matchesOutText = matchesOutBuilder.toString();
		return matchesOutText;
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