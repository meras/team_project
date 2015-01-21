
public class Referee implements Comparable<Referee> {
	
	private String refID, fName, lName, qualification, homeArea;
	/* n.b. was going to have qualification as a boolean (e.g. would be true if qualification
	 * above 1 and false otherwise). But since we're going to have to store the actual name of the
	 * qualification to use in other parts of program (e.g. to print out the report at the end), better
	 * to record the string itself. Then checkIfQualified() checks if the number in the qualification
	 * is above 1 and returns true or false accordingly.
	 */
	private static final int NUM_AREAS = 3;
	//2 arrays here, one to hold the names of the different areas,
	//one to hold the boolean values representing whether the ref will travel
	//to the area. Used as parallel arrays.
	//see getTravelInfo() below... Thoughts?
	private static final String[] AREAS = {"North", "Central", "South"};
	private boolean[] travelInfo;
	private int numAllocations;

	//TODO this will bring great pleasure
	public static final int NORTH = 0;
	public static final int CENTRAL = 1;
	public static final int SOUTH = 2;

	public Referee(String refInfo) {
		String [] infoTokens = refInfo.split("[ ]+");
		refID = infoTokens[0];
		fName = infoTokens[1];
		lName = infoTokens[2];
		qualification = infoTokens[3];
		numAllocations = Integer.parseInt(infoTokens[4].trim());
		homeArea = infoTokens[5];
		travelInfo = new boolean [NUM_AREAS];
		setTravelInfo(infoTokens[6]);
	}
	
	//mutator methods
	
	public void setQualification(String qual) {
		qualification = qual;
	}
	
	//should we have mutator methods for each area? i.e. north, central, south?
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
	//TODO great pleasure here removes the loop
	public boolean getTravelInfo(String area) {
		boolean found = false;
		int i = 0;
		
		while(!found && i < AREAS.length) {
			if(area.equals(AREAS[i]))
				found = true;
			else
				i++;
		}
		return travelInfo[i];
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
	
	public boolean checkIfQualified() {
		int qualNum = Integer.parseInt(qualification.substring(3));
		return (qualNum > 1);
	}
	
	public int getNumAllocs() {
		return numAllocations;
	}
	
	public String getHomeArea() {
		return homeArea;
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
