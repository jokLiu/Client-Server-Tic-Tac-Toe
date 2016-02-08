
//imports
import java.util.*;

/**
 * The Class ClientTable.
 */
public class ClientTable {

	/** The queue table. */
	private Map<String, MessageQueue> queueTable = new TreeMap<String, MessageQueue>();

	// The following overrides any previously existing nickname, and
	// hence the last client to use this nickname will get the messages
	// for that nickname, and the previously exisiting clients with that
	// nickname won't be able to get messages. Obviously, this is not a
	// good design of a messaging system. So I don't get full marks:

	/**
	 * Adds the new player
	 *
	 * @param nickname
	 *            the nickname
	 */
	public void add(String nickname) {
		queueTable.put(nickname, new MessageQueue());
	}

	/**
	 * Gets the queue.
	 *
	 * @param nickname
	 *            the nickname
	 * @return the queue
	 */
	public MessageQueue getQueue(String nickname) {
		return queueTable.get(nickname);
	}

	/**
	 * Gets the names.
	 *
	 * @return the names
	 */
	public String getNames() {
		Set<String> set = queueTable.keySet();
		String[] names = (String[]) set.toArray();
		String name = "";
		for (int i = 0; i < names.length; i++) {
			name += names.toString() + ";   \n";
		}
		return "The clients which are connected: " + name;
	}

}
