//iports
import java.io.BufferedReader;
import java.io.IOException;

// TODO: Auto-generated Javadoc
// Gets messages from client and puts them in a queue, for another
// thread to forward to the appropriate client.

/**
 * The Class ServerReceiver.
 */
public class ServerReceiver extends Thread {
	
	/** The my clients name. */
	private String myClientsName;
	
	/** The my client reader from  */
	private BufferedReader myClient;
	
	/** The win message. */
	private final String win = "win";
	
	/** The lost message. */
	private final String lost = "lost";
	
	/** The tie message. */
	private final String tie = "tie";
	
	/** The accept request message. */
	private final String accept = "Yes";
	
	/** The no to request message. */
	private final String no = "No";
	
	/** The info about clients message. */
	private final String info = "info";
	
	/** The end of the client message. */
	private final String end = "End";
	
	/** The begin first message. */
	private final String beginFirst = "begin";// player who requested starts the
	
	/** The remove player from the list when exits message. */
	private final String remove = "remove";
	
	/** The disable the board message. */
	private final String disable = "disable";
	
	/** The play. game request */
	private final String play = "Play";
	
	/** The quit. The quit message */
	private final String quit = "Quit";
	
	/** The not end. True until player decides to exit */
	private boolean notEnd = true;
	
	/** The begin second message. */
	private final String beginSecond = "start";
	
	/** The client table. */
	private ClientTable clientTable;
	
	/** The recipient. the message from the client */
	private String recipient;
	
	/** The text.  the second message from the client*/
	private String text;
	
	/** The inform. all the info about the client */
	private ClientInformation inform;

	/**
	 * Instantiates a new server receiver.
	 *
	 * @param n the name of the client
	 * @param c the reader from the server
	 * @param t the client table
	 * @param inform the information
	 */
	public ServerReceiver(String n, BufferedReader c, ClientTable t, ClientInformation inform) {
		myClientsName = n;
		myClient = c;
		clientTable = t;
		this.inform = inform;

	}


	/* 
	 * run method of the thread
	 */
	public void run() {
		try {
			while (notEnd) {
				//reads the line from the client
				recipient = myClient.readLine();

				if (recipient != null && !recipient.equals("Quit")) {
					//reads the second line from the client
					text = myClient.readLine();
					if (text != null) {

						// if text is a current game move, send it to other
						// player
						if (check(text)) {
							gameMove();

							// if it is an accept of the request, start the game
							// and send messages for both players
						} else if (text.equals(accept)) {
							acceptedRequest();

							// if request rejected
						} else if (text.equals(no)) {
							rejectedRequest();

							// to get information about current players
						} else if (text.equals(info)) {

							sendInfo();

						}

						// end client reciever
						else if (text.equals(end)) {
							endClient();

						}
						// add the score
						else if (text.equals(win) || text.equals(lost) || text.equals(tie)) {
							addScore();

						}

						// if client closes remove client from client list
						else if (text.equals(remove)) {
							inform.remove(myClientsName);

						}
						
						// disable the board for one move
						else if (text.equals(disable)) {
							disableMessage();

						}

						// if request to play
						else if (text.equals(play)) {
							// if player is not the same person send a request
							// else send message back that player requested
							// himself
							playRequest();

						}

						// simple message
						else {
							simpleMessage();
						}

					}
				} else {
					//Quit message to Client Sender
					Message msg = new Message(quit);
					MessageQueue queue2 = clientTable.getQueue(myClientsName);
					queue2.offer(msg);
					notEnd = false;

				}

			}
			myClient.close();
			System.out.println(myClientsName + " disconnected");
			myClient.close();
		} catch (IOException e) {
			System.err.println("Something went wrong with the client " + myClientsName + " " + e.getMessage());
			// No point in trying to close sockets. Just give up.
			// We end this thread (we don't do System.exit(1)).
		}

	}
	

	/**
	 * Check if the message is integer
	 *
	 * @param s the message
	 * @return true, if successful
	 */
	private boolean check(String s) {

		try {
			Integer.valueOf(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;

	}

	/**
	 * Game move.
	 * Send the move to the client
	 */
	private void gameMove() {

		Message msg = new Message(text);
		MessageQueue queue2 = clientTable.getQueue(recipient);

		if (queue2 != null) {

			queue2.offer(msg);
		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);

	}

	/**
	 * Accepted request.
	 * 
	 */
	private void acceptedRequest() {
		//remove players from invitation list
		inform.removeInvited(myClientsName);
		inform.removeInvited(recipient);
		
		//disable players availability
		inform.setAvailability(myClientsName, false);
		inform.setAvailability(recipient, false);
		
		//messages for both players to determine which starts first
		Message name1 = new Message(myClientsName);
		Message msg1 = new Message(beginSecond);
		Message name2 = new Message(recipient);
		Message msg2 = new Message(beginFirst);
		MessageQueue queue = clientTable.getQueue(myClientsName);
		MessageQueue queue2 = clientTable.getQueue(recipient);

		if (queue != null) {
			queue.offer(name2);
			queue.offer(msg1);
			queue2.offer(name1);
			queue2.offer(msg2);
		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);

	}

	/**
	 * Rejected request.
	 */
	private void rejectedRequest() {
		//remove players from invitation list
		inform.removeInvited(myClientsName);
		inform.removeInvited(recipient);
		
		//send a message to other client
		Message name2 = new Message(recipient);
		Message msg2 = new Message(text);

		MessageQueue queue2 = clientTable.getQueue(recipient);

		if (queue2 != null) {
			queue2.offer(name2);
			queue2.offer(msg2);
		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);
	}

	/**
	 * Send info message to update the board
	 */
	private void sendInfo() {
		Message msg2 = new Message("i");
		MessageQueue queue = clientTable.getQueue(myClientsName);

		if (queue != null) {

			queue.offer(msg2);

		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);

	}

	/**
	 * End client message
	 */
	private void endClient() {
		Message msg2 = new Message("End");
		MessageQueue queue = clientTable.getQueue(myClientsName);

		if (queue != null) {

			queue.offer(msg2);

		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);

	}

	/**
	 * Adds the score from the game, when game ends
	 */
	private void addScore() {
		Message msg2 = new Message(text);
		Message name = new Message(recipient);
		inform.setAvailability(myClientsName, true);
		MessageQueue queue = clientTable.getQueue(recipient);

		if (queue != null) {

			queue.offer(msg2);
			queue.offer(name);

		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);

	}

	/**
	 * Disable message.
	 */
	private void disableMessage() {
		Message msg = new Message("disable");
		MessageQueue queue = clientTable.getQueue(myClientsName);

		if (queue != null) {

			queue.offer(msg);
		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);

	}

	/**
	 * Play request.
	 */
	private void playRequest() {
		//checks if the player is not already playing
		if (inform.getAvailability(recipient)) {
			
			//checks if the player is not invited
			if (!inform.checkInvited(recipient)) {
				
				//checks if the player is not the same person
//					if not then prints the request message to the opponent
				// else prints message back to  the player which requested
				if (!myClientsName.equals(recipient)) {
					Message msg = new Message(text);
					Message name = new Message(myClientsName);
					MessageQueue queue = clientTable.getQueue(recipient);
					inform.addInvited(recipient);
					inform.addInvited(myClientsName);
					if (queue != null) {
						queue.offer(name);
						queue.offer(msg);
					} else
						System.err.println("Message for unexistent client " + recipient + ": " + text);
				} else {
					Message msg = new Message("SamePlayer");
					Message name = new Message(myClientsName);
					MessageQueue queue = clientTable.getQueue(myClientsName);

					if (queue != null) {
						queue.offer(name);
						queue.offer(msg);
					} else
						System.err.println("Message for unexistent client " + recipient + ": " + text);
				}
			} else {
				//prints a message back to the player 
				// with the messahe that playr is already invtied
				Message msg = new Message("isInvited");
				Message name = new Message(myClientsName);
				MessageQueue queue = clientTable.getQueue(myClientsName);

				if (queue != null) {
					queue.offer(name);
					queue.offer(msg);
				} else
					System.err.println("Message for unexistent client " + recipient + ": " + text);
			}
			//prints a message back to the player 
			// with the messahe that playr is already playing
		} else {
			Message msg = new Message("isPlaying");
			Message name = new Message(myClientsName);
			MessageQueue queue = clientTable.getQueue(myClientsName);

			if (queue != null) {
				queue.offer(name);
				queue.offer(msg);
			} else
				System.err.println("Message for unexistent client " + recipient + ": " + text);
		}
	}

	/**
	 * Simple message.
	 */
	private void simpleMessage() {
		Message name = new Message(recipient);
		Message msg = new Message(text);
		MessageQueue queue = clientTable.getQueue(recipient);

		if (queue != null) {
			queue.offer(name);
			queue.offer(msg);
		} else
			System.err.println("Message for unexistent client " + recipient + ": " + text);

	}
}
