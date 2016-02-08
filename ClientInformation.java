
//imports
//imports
import java.util.ArrayList;

/**
 * The Class ClientInformation to keep informaton about clients
 *
 * 
 */

public class ClientInformation {

	// fields:

	// names of the players which are connected
	private ArrayList<String> names = new ArrayList<String>();

	// specifies if the player is playing or not
	private ArrayList<Boolean> available = new ArrayList<Boolean>();

	// counter for wins of the clients
	private ArrayList<Integer> win = new ArrayList<Integer>();

	// counter for losses of the clients
	private ArrayList<Integer> lost = new ArrayList<Integer>();

	// counter for tie games of the clients
	private ArrayList<Integer> tie = new ArrayList<Integer>();

	// names of players which are requested to play
	// in order to avoid multiple requests and error when multiple games is
	// accepted
	private ArrayList<String> invitedPlayers = new ArrayList<String>();

	/**
	 * Adds the new clients
	 *
	 * @param name
	 *            the name of the client
	 */
	public void addNew(String name) {

		this.names.add(name);
		available.add(true);
		win.add(0);
		lost.add(0);
		tie.add(0);
	}

	/**
	 * checks if there a player with a same name already exist
	 *
	 * @param name
	 *            the name of the player
	 * @return true, if successful
	 */
	public boolean exist(String name) {
		if (names.contains(name)) {
			return true;
		} else
			return false;

	}

	/**
	 * add a name of player which is invited to play
	 *
	 * @param name
	 *            the name of the player
	 */
	public void addInvited(String name) {
		invitedPlayers.add(name);
	}

	/**
	 * Removes the player from invited list
	 *
	 * @param name
	 *            the name of the player
	 */
	public void removeInvited(String name) {
		invitedPlayers.remove(name);
		
	}

	/**
	 * Check if player is already invited to play
	 *
	 * @param name
	 *            the name of the player
	 * @return true, if successful
	 */
	public boolean checkInvited(String name) {
		if (invitedPlayers.contains(name)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Gets the number of clients in the list
	 *
	 * @return the number
	 */
	public int getNumber() {

		return names.size();
	}

	/**
	 * Get names of all the clients
	 *
	 * @return names the name of the players
	 */
	public ArrayList<String> getNames() {
		return names;
	}

	/**
	 * Gets the win.
	 *
	 * @return the win score
	 */
	public ArrayList<Integer> getWin() {
		return win;
	}

	/**
	 * Gets the lost score.
	 *
	 * @return the lost score
	 */
	public ArrayList<Integer> getLost() {
		return lost;
	}

	/**
	 * Gets the tie score
	 *
	 * @return the tie score
	 */
	public ArrayList<Integer> getTie() {
		return tie;
	}

	/**
	 * Gets the availability of the players
	 *
	 * @return the if available
	 */
	public ArrayList<Boolean> getIfAvailable() {
		return available;
	}

	/**
	 * Gets the availability of particular player
	 *
	 * @param name
	 *            the name of the player
	 * @return the availability
	 */
	public boolean getAvailability(String name) {
		int index = names.indexOf(name);
		return available.get(index);
	}

	/**
	 * Sets the availability.
	 *
	 * @param name
	 *            the name of the player
	 * @param avail
	 *            the availability of the player
	 */
	public void setAvailability(String name, boolean avail) {
		int index = names.indexOf(name);
		available.set(index, avail);
	}

	/**
	 * Adds the win.
	 *
	 * @param name
	 *            the name of the player
	 */
	public void addWin(String name) {
		int index = names.indexOf(name);
		int temp = win.get(index);
		win.set(index, temp + 1);
		System.out.println("win");
	}

	/**
	 * Adds the lost.
	 *
	 * @param name
	 *            the name of the player
	 */
	public void addLost(String name) {
		int index = names.indexOf(name);
		int temp = lost.get(index);
		lost.set(index, temp + 1);
		System.out.println("lost");
	}

	/**
	 * Adds the tie.
	 *
	 * @param name
	 *            the name of the player
	 */
	public void addTie(String name) {
		int index = names.indexOf(name);
		int temp = tie.get(index);
		tie.set(index, temp + 1);
		System.out.println("tie");
	}

	/**
	 * Removes the player from the board Happens when player quits
	 * 
	 * @param name
	 *            name the name of the player
	 */
	public void remove(String name) {
		int index = names.indexOf(name);
		names.remove(index);
		win.remove(index);
		lost.remove(index);
		tie.remove(index);
		available.remove(index);

	}

}
