import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RefList implements Iterable<Referee> {
	private static final int MAX_REFS = 12;
	private final List<Referee> refList;
	public final int REF_NOT_FOUND = -1;

	public RefList() {
		refList = new ArrayList<>();
	}

	/**
	 * Checks if there is still room for another Referee, returns true if there is.
	 * @return boolean indicating whether there is room
	 */
	public boolean checkForSpace() {
		return (refList.size() < MAX_REFS);
	}

	/**
	 * Creates a Referee from info read from input file and adds it to the end of refList.
	 * @param refString the info line read in from the file 
	 */
	public void addRefFromFile(String refString) {
		Referee newRef = new Referee(refString);
		refList.add(newRef);
	}

	/**
	 * Gets the current amount of Referees in refList
	 * @return an int corresponding to the size of refList
	 */
	public int getRefereeCount() {
		return refList.size();
	}

	/**
	 * Used to add a Referee object fom information input to the GUI. First calls method to create the ref's ID then
	 * creates a new Referee, checks where it should be placed in refList based on order of IDs and adds it 
	 * to refList in that position.
	 * @param firstNm the first name input to the GUI
	 * @param lastNm the last name input to the GUI
	 * @param qual the ref's qualification
	 * @param allocs the ref's number of match allocations
	 * @param home the ref's home area
	 * @param travelInfo information on where the ref will travel
	 */
	public void addRefFromGui(String firstNm, String lastNm, String qual, int allocs, String home, String travelInfo) {
		String newId = createId(firstNm, lastNm);
		//create string to pass to Referee constructor
		String refData = newId + " " + firstNm + " " + lastNm + " " + qual + " " + allocs + " " + home + " " + travelInfo;
		Referee newRef = new Referee(refData);

		//place new Referee in refList based on ID order
		boolean added = false;
		int i = 0;
		while (!added && i < refList.size()) { //loop through refList
			String otherID = refList.get(i).getRefID(); //get ID to compare 
			//if new ref's ID should be before compared ID, add new ref at compared ref's position
			if (newId.compareTo(otherID) < 0) {
				refList.add(i, newRef);
				added = true; //new ref has been added, exit loop
			} else
				i++; //if new ref should be placed after compared ref, check next position
		}
		//If new ID does not come before any existing ID, place new Ref at end of refList
		if (!added)
			refList.add(newRef);
	}

	/**
	 * generates a unique ID for a ref added from the GUI based on first and last name
	 * @param first the new ref's first name
	 * @param last the new ref's last name
	 * @return the new ref's ID
	 */
	private String createId(String first, String last) {
		//get letter part of ID from initials
		String letterPart = ("" + first.charAt(0) + last.charAt(0)).toUpperCase();
		int numPart = 1; // set number part to 1 initially

		for (Referee ref : refList) { //loop through refList
			String otherInitials = ref.getRefID().substring(0, 2); //get compared ref's initials
			if (letterPart.equals(otherInitials))
				numPart++; //if matching initials found, increment the number part of ID
		}
		//concatenate the letter part and number part
		return letterPart + numPart;
	}

	/**
	 * used to delete a Referee from refList
	 * @param first the ref's first name
	 * @param last the ref's last name
	 * @return true if the ref of that name was found and deleted, false if not found
	 */

	public boolean deleteRef(String first, String last) {
		Referee findRefResult = findRef(first, last); //look for ref in refList

		if (findRefResult != null) { //if ref exists, remove from refList
			refList.remove(findRefResult);
			return true;
		} else //ref not found, return false
			return false;
	}

	/**
	 * looks for a Referee in refList based on their name
	 * @param first the ref's first name
	 * @param last the ref's last name
	 * @return Referee object if found, else null
	 */
	public Referee findRef(String first, String last) {
		Referee ref = null;
		boolean found = false;
		int refIndex = 0;
		String firstToSearch = first.toLowerCase();
		String lastToSearch = last.toLowerCase();		

		while(!found && refIndex < refList.size()) { //loop through refList
			ref = refList.get(refIndex);
			String otherFirst = ref.getFName().toLowerCase();
			String otherLast = ref.getLName().toLowerCase();
			//compare name to name of ref at position i
			if(otherFirst.equals(firstToSearch) && otherLast.equals(lastToSearch))
				found = true; //if found, exit loop
			else
				refIndex++; //if not found check against next ref
		}

		if(!found) //if ref not in refList return null
			return null;
		else //if ref was found, return the object
			return ref;
	}

	/**
	 * takes in information about a match and creates a list of suitable refs, in order of suitability.
	 * makes an array of Referees from refList then sorts that array in order of number of allocations.
	 * then loops through the sorted array, checking each ref's suitability for the match based on their
	 * home area and willingness to travel and placing them in the appropriate position in a new List:
	 * first the local refs, then the adjacent refs, then the non-adjacent refs
	 * @param matchLoc the location of the match
	 * @param seniorMatch boolean indicating whether match is senior or not
	 * @return List populated with suitable refs in order of suitability for the match
	 */

	public List<Referee> getSuitableRefs(int matchLoc, boolean seniorMatch) {
		//make an array of Referees from refList
		Referee[] arrayToSort = new Referee[refList.size()]; 
		arrayToSort = refList.toArray(arrayToSort); 
		Arrays.sort(arrayToSort); //sort the array on number of allocations

		//count of local and adjacent refs already in suitable refs list
		int numLocalRefs = 0;
		int numAdjRefs = 0;
		//arrayList to be populated with suitable referees
		List<Referee> suitableRefs = new ArrayList<>();

		for (Referee ref : arrayToSort) { //loop through array sorted on number of allocations
			int home = ref.getHomeArea(); //get ref's home area
			boolean refWillTravel = ref.getTravelInfo(matchLoc); //get whether ref will travel to match location

			if (!seniorMatch || ref.checkIfQualified()) { //first check if ref is qualified for the match
				//if ref lives in match location, add ref in position after last local ref
				if (home == matchLoc) {
					suitableRefs.add(numLocalRefs, ref);
					numLocalRefs++; //increment number of local refs in list
					//if ref lives in an adjacent area, add ref in position after last adjacent ref
				} else if ((home == Referee.CENTRAL || matchLoc == Referee.CENTRAL) && refWillTravel) {
					suitableRefs.add(numLocalRefs + numAdjRefs, ref);
					numAdjRefs++; //increment number of adjacent refs in list
					//if ref lives in a non-adjacent area, add ref to end of list
				} else if (refWillTravel) {
					suitableRefs.add(ref);
				}
			}
		}
		return suitableRefs;
	}
	
	public String getRefsOutText() {
		StringBuilder refsOutBuilder = new StringBuilder();

		for(Referee ref : refList) {
			refsOutBuilder.append(ref.getRefLine());
		}
		return refsOutBuilder.toString();
	}
	
	/**
	 * Returns an iterator over elements of type Referee.
	 * @return an Iterator.
	 */
	public Iterator<Referee> iterator() {
		return refList.iterator();
	}
}
