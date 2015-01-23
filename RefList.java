import java.util.*;

public class RefList {
	private static final int MAX_REFS = 12;
	//list of Referee objects - will be implemented as ArrayList
	private List<Referee> refList;
	public final int REF_NOT_FOUND = -1;
	/*
	 * since it seems we are using an array list there is no need to keep count of how many
	 * refs we have; we can just use ArrayList.size() every time we need to check this
	 */
	
	public RefList() {
		refList = new ArrayList<Referee>();
	}
	
	public List<Referee> getRefList(){
		return refList;
	}

	public Referee getRefAtIndex(int index) {
		return refList.get(index);
	}
	
	/*
	 * to be used in GUI when checking if a new ref can be added
	 */
	public boolean checkForSpace() {
		return (refList.size() < MAX_REFS);
	}
	
	/*
	 * as the refs are in order as we read them from the file, here we can just add
	 * the ref to the end of the arraylist
	 */
	public void addRefFromFile(String refString) {
		Referee newRef = new Referee(refString);
		refList.add(newRef);
	}
	
	/*
	 * when a new ref is added from the GUI, we first call a method to create an ID based on their name, pass the
	 * info to the Referee constructor and then loop through the arraylist to find where the
	 * ref should be inserted. Done it this way for now but there may be better ways, for example
	 * Merunas you had an idea for this right?
	 * N.B. to create a new Referee I am using the same constructor as used when creating from the file,
	 * i.e. I am making a string from all the info and passing it to the constructor.
	 * This isn't ideal but seems nicer than having a constructor with 7 parameters.
	 * To be discussed...
	 */
	public void addRefFromGui(String firstNm, String lastNm, String qual, int allocs, String home, String travelInfo) {
		String newId = createId(firstNm, lastNm);
		String refData = newId + " " + lastNm + " " + qual + " " + allocs + " " + home + " " + travelInfo;
		Referee newRef = new Referee(refData);
		
		boolean added = false;
		int i = 0;
		while(!added && i < refList.size()) {
			String otherID = refList.get(i).getRefID();
			if(newId.compareTo(otherID) < 0) {
				refList.add(i, newRef);
				added = true;
			}
			else
				i++;
		}
		
		if(!added)
			refList.add(newRef);
	}
	
	private String createId(String first, String last) {
		String letterPart = "" + first.charAt(0) + last.charAt(0);
		int numPart = 1;
		
		for(int i = 0; i < refList.size(); i++) {
			Referee ref = refList.get(i);
			String otherInitials = ref.getRefID().substring(0,2);
			if(letterPart.equals(otherInitials))
				numPart++;
		}
	
		String newRefId = letterPart + numPart;
		return newRefId;
	}
	
	/*
	 * What I am imagining with the 2 methods below: the GUI reads in the ref's
	 * first and last name. It passes them to findFromName, which returns the index
	 * position of the ref within the list (or a "sentinel value" if the ref was not found).
	 * Then the GUI class calls deleteRef using that index.
	 */
	
	public void deleteRef(int index) {
		refList.remove(index);
	}
	
	public int findFromName(String first, String last) {
		Referee ref = null;
		boolean found = false;
		int refIndex = 0;
		
		while(!found && refIndex < refList.size()) {
			ref = refList.get(refIndex);
			if(ref.getFName().equals(first) && ref.getLName().equals(last))
				found = true;
			else
				refIndex++;
		}
	
		if(!found)
			return REF_NOT_FOUND;
		else
			return refIndex;
	}
	

	/*
	 * This is my initial suggestion for allocating refs to matches.
	 * As I see it the GUI class will call this method when the allocate refs button is pressed.
	 * First it uses toArray() to create a normal array from the ArrayList - this is because I want to use
	 * Arrays.sort() to sort the list based on number of allocations (see compareTo in Referee class) and 
	 * it needs to be an array for that to work. This array gets sorted.
	 * Then it creates three separate ArrayLists for local refs, adjacent refs and non-adjacent refs.
	 * Then it loops through the sorted array, picking out suitable refs and placing them in
	 * the appropriate ArrayList.
	 * At the end we have three ArrayLists sorted on number of allocations. These are combined into
	 * 1 ArrayList sorted in order of suitability for the match, which is returned to the GUI class.
	 * 
	 * Fairly long method but quite hard to split up. In any case this is just the starting point
	 * I guess.
	 */
	public List<Referee> getSuitableRefs(int matchLoc, boolean seniorMatch) {
		Referee[] arrayToSort = new Referee[refList.size()];
		arrayToSort = refList.toArray(arrayToSort);
		Arrays.sort(arrayToSort);

		//array indecees
		int localIndex = 0;
		int adjIndex = 0;
		int nonAdjIndex = 0;

		List<Referee> suitableRefs = new ArrayList<Referee>();

		for(int i=0; i < arrayToSort.length; i++) {
			Referee ref = arrayToSort[i];
			int home = ref.getHomeArea();
			boolean refWillTravel = ref.getTravelInfo(matchLoc);

			if(!seniorMatch || ref.checkIfQualified()) {
				if (home == matchLoc) {
					suitableRefs.add(localIndex, ref);
					localIndex++;
				} else if ((home == Referee.CENTRAL || matchLoc == Referee.CENTRAL) && refWillTravel) {
					suitableRefs.add(localIndex + adjIndex, ref);
					adjIndex++;
				} else if (refWillTravel) {
					suitableRefs.add(localIndex + adjIndex + nonAdjIndex, ref);
					nonAdjIndex++;
				}
			}
		}
		return suitableRefs;
	}
	
}
