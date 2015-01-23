/**
 * Maintains a list of Match objects
 * The methods allow Matches to be added to the list
 */

public class MatchList {

	public static final int MAX_MATCHES = 52;
	public Match [] matchArray;
	private int matchListPosition;

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
		matchArray [matchListPosition] = newMatch;
		matchListPosition++;	
	}

	/**
	 * get number of matches currently in the array
	 */	
	public int getNoMatches() {return matchListPosition;}

	public Match getMatch(String matchWeekNo) {
		for (int i = 0; i < MAX_MATCHES; i++){
			if(matchArray[i] != null){
				if(matchWeekNo.equals(matchArray[i].getWeekNo())){
					return matchArray[i];	
				}
			}
		}
		return null;
	}
}