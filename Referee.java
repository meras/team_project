
public class Referee implements Comparable<Referee> {
	
	private String refID, fName, lName, qualificationType;
	
	/* n.b. was going to have qualification as a boolean (e.g. would be true if qualification
	 * above 1 and false otherwise). But since we're going to have to store the actual name of the
	 * qualification to use in other parts of program (e.g. to print out the report at the end), better
	 * to record the string itself. Then checkIfQualified() checks if the number in the qualification
	 * is above 1 and returns true or false accordingly.
	 */
	private boolean allocated = false;
	private int homeArea, qualificationLevel;
	private static final int NUM_AREAS = 3;
	//2 arrays here, one to hold the names of the different areas,
	//one to hold the boolean values representing whether the ref will travel
	//to the area. Used as parallel arrays.
	//see getTravelInfo() below... Thoughts?
	private static final String[] AREAS = {"North", "Central", "South"};
	private boolean[] travelInfo;
	private int numAllocations;

	public static final int NORTH = 0;
	public static final int CENTRAL = 1;
	public static final int SOUTH = 2;

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
	
	public void setNumAllocs(int numAloc) 
	{
		 numAllocations = numAloc;
	}
	
	public void setQualificationType(String qual) {
		qualificationType = qual;
	}
	
	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}
	

	public void setQualificationLevel(int level)
	{
		qualificationLevel = level;
	}

	public void setHomeArea(String home) {
		if(home.equals("North"))
			homeArea = NORTH;
		else if(home.equals("Central"))
			homeArea = CENTRAL;
		else
			homeArea = SOUTH;
	}
	
	//should we have mutator methods for each area? i.e. north, central, south?
	/* TODO we discussed passing a boolean array to this but this needs to take a string 
	because that's what the file's going to give it.
	Can have a separate method to set the travel info when editing/adding a ref from the GUI,
	which could take a boolean array. Or this boolean array could just be passed to the alternative
	constructor
	*/ 
	public void setTravelInfo(String travelStr) {
		for(int i=0; i < travelInfo.length; i++)
			travelInfo[i] = (travelStr.charAt(i) == 'Y');
	}

	public void incrementAllocs() {
		numAllocations++;
	}
	
	//accessor methods
	
	/*
	 * this takes an area as a parameter and loops through the array of areas 
	 * (north, central, south) to find a match with that parameter. 
	 * Then it returns the corresponding boolean value from the parallel travelInfo array.
	 */
	public boolean getTravelInfo(int area) {
		return travelInfo[area];
	}
	
	public String getRefID() {
		return refID;
	}
	
	public String getFName() {
		return fName;
	}
	
	public String getLName() {
		return lName;
	}
	
	public String getQualificationType(){
		return qualificationType;
	}
	
	public int getQualificationLevel()
	{
		return qualificationLevel;
	}
	
	public boolean checkIfQualified() {
		return (qualificationLevel > 1);
	}
	
	public int getNumAllocs() {
		return numAllocations;
	}
	
	public int getHomeArea() {
		return homeArea;
	}

	public String getHomeString() {
		return AREAS[homeArea];
	}
	
	public boolean isAllocated() {
		return allocated;
	}
	/*
	 *since we are inserting new refs directly into the right position into the list, we never
	 *have to sort on ID. Therefore we can use compareTo to sort on number of allocations.
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
