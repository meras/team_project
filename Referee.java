
public class Referee implements Comparable<Referee> {
	
	private String refID, fName, lName, qualificationType;
	private int homeArea, qualificationLevel;
	private boolean[] travelInfo;
	private int numAllocations;
	//boolean to keep track if ref has been allocated to a match within the program (to prevent deletion of ref if so)
	private boolean allocated = false;
	
	private static final int NUM_AREAS = 3;
	private static final String[] AREAS = {"North", "Central", "South"};
	//int constants to represent each area
	public static final int NORTH = 0;
	public static final int CENTRAL = 1;
	public static final int SOUTH = 2;

	/**
	 * Referee constructor - takes line read from input file, tokenises and sets
	 * instance variables accordingly
	 * @param refInfo the line read from the input file
	 */
	public Referee(String refInfo) {
		String [] infoTokens = refInfo.split("[ ]+");
		
		refID = infoTokens[0];
		fName = infoTokens[1];
		lName = infoTokens[2];
		
		qualificationType = infoTokens[3].substring(0,3);
		qualificationLevel = Integer.parseInt(infoTokens[3].substring(3));
		
		numAllocations = Integer.parseInt(infoTokens[4].trim());
		
		setHomeArea(infoTokens[5]);
		
		travelInfo = new boolean [NUM_AREAS];
		setTravelInfo(infoTokens[6]);
	}
	
	//mutator methods
	
	/**
	 * Mutator method for number of match allocations
	 * @param numAlloc the number of allocations
	 */
	public void setNumAllocs(int numAlloc) 
	{
		 numAllocations = numAlloc;
	}
	
	/**
	 * mutator for qualification type
	 * @param qual the type of qualification
	 */
	public void setQualificationType(String qual) {
		qualificationType = qual;
	}
	
	/**
	 * mutator for qualification level
	 * @param level the level of the ref's qualification
	 */
	public void setQualificationLevel(int level)
	{
		qualificationLevel = level;
	}

	/**
	 * mutator for the ref's home area. takes a string and sets homeArea as the corresponding
	 * int constant
	 * @param home the ref's home area as a string 
	 */
	public void setHomeArea(String home) {
		if(home.equals("North"))
			homeArea = NORTH;
		else if(home.equals("Central"))
			homeArea = CENTRAL;
		else
			homeArea = SOUTH;
	}
	
	/**
	 * mutator for ref's travel info. takes a string indicating travel preferences e.g. "YYN"
	 * checks each character - if 'Y' sets corresponding position of travelInfo array to true
	 * if 'N' sets to false
	 * @param travelStr string indicating ref's travel preferences
	 */ 
	public void setTravelInfo(String travelStr) {
		for(int i=0; i < travelInfo.length; i++)
			travelInfo[i] = (travelStr.charAt(i) == 'Y');
	}

	/**
	 * called whenever ref is allocated to a match
	 * besides incrementing numAllocs, checks if ref has been allocated to a match yet; 
	 * if not, sets allocated to true
	 */
	public void incrementAllocs() {
		numAllocations++;
		
		if(!allocated) 
			allocated = true;
	}
	
	//accessor methods
	
	/**
	 * gets info on whether ref will travel to a certain area. takes in the area as an integer
	 * and returns the boolean value of the travelInfo array at that position of the array
	 * @param area the area as an integer constant
	 * @return true if ref is willing to travel to the area, false if not
	 */
	public boolean getTravelInfo(int area) {
		return travelInfo[area];
	}
	
	/**
	 * accessor for refId
	 * @return the refID
	 */
	public String getRefID() {
		return refID;
	}
	
	/**
	 * accessor for ref's first name
	 * @return the ref's first name
	 */
	public String getFName() {
		return fName;
	}
	
	/**
	 * accessor the ref's last name
	 * @return the ref's last name
	 */
	public String getLName() {
		return lName;
	}
	
	/**
	 * accessor for the ref's qualification type
	 * @return the the ref's qualification type
	 */
	public String getQualificationType(){
		return qualificationType;
	}
	
	/**
	 * accessor for the ref's qualification level
	 * @return the ref's qualification level
	 */
	public int getQualificationLevel()
	{
		return qualificationLevel;
	}
	
	/**
	 * checks if the ref is qualified for senior matches
	 * @return true if ref is qualified, false if not
	 */
	public boolean checkIfQualified() {
		//ref is qualified if qualificationLevel is greater than 1
		return (qualificationLevel > 1);
	}
	
	/**
	 * accessor for number of allocations
	 * @return the ref's number of allocations
	 */
	public int getNumAllocs() {
		return numAllocations;
	}
	
	/**
	 * accessor for the ref's home area as an int
	 * @return the ref's home area as an int
	 */
	public int getHomeArea() {
		return homeArea;
	}

	/**
	 * accessor for the ref's home area as a string
	 * @return the ref's home area as a string
	 */
	public String getHomeString() {
		return AREAS[homeArea];
	}
	
	/**
	 * checks if the ref has been allocated to a match in Hibernia
	 * @return true if they have, false if not
	 */
	public boolean isAllocated() {
		return allocated;
	}
	/**
	 * compareTo method to be used in getSuitableRefs() in RefList.
	 * compares Referee objects based on number of allocations
	 * @param other the referee to be compared
	 * @return the result of the comparison
	 */
	public int compareTo(Referee other) {
		int thisAllocs = this.getNumAllocs();
		int otherAllocs = other.getNumAllocs();
		if(thisAllocs < otherAllocs)
			return -1;
		else if(thisAllocs > otherAllocs)
			return 1;
		else
			return 0;
	}
	
}
