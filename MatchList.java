/**
 * Maintains a list of Match objects
 * The methods allow Matches to be added to the list
 */

public class MatchList {

	public static final int MAX_MATCHES = 52;
	public Match [] matchArray;
	private int matchCount;
	
	/**
	 * Constructor for MatchList class
	 */	
	public MatchList(){
		matchArray = new Match[MAX_MATCHES];
	}
	
	/**
	 * add match to array based on week number
	 */		
	public void addMatch(Match newMatch){
		matchArray [matchCount - 1] = newMatch;
		matchCount++;	
	}
	
	/**
	 * get number of matches currently in the array
	 */	
	public int getNoMatches() {return matchCount;}
}
