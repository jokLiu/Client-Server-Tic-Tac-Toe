
//imports
import java.io.PrintStream;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
// Continuously reads from message queue for a particular client,
// forwarding to the client.

/**
 * The Class ServerSender.
 */
public class ServerSender extends Thread {

	/** The queue. */
	private MessageQueue queue;

	/** The client. */
	private PrintStream client;

	/** The check. */
	private boolean check = true;

	/** The info. */
	private ClientInformation info;

	/** The win. */
	private final String win = "win";

	/** The lost. */
	private final String lost = "lost";

	/** The tie. */
	private final String tie = "tie";

	/**
	 * Instantiates a new server sender.
	 *
	 * @param q
	 *            the queue
	 * @param c
	 *            the print stream
	 * @param info
	 *            the client information
	 */
	public ServerSender(MessageQueue q, PrintStream c, ClientInformation info) {
		queue = q;
		client = c;
		this.info = info;
	}

	/*
	 * run method of the thread
	 */
	public void run() {
		while (check) {
			Message msg = queue.take();
			String mes = msg.getText();
			//prints the information about all the players
			if (mes.equals("i")) {
				client.println("information");
				String temp = Integer.toString(info.getNumber());
				client.println(temp);
				print();

				//ends the server sender thread
			} else if (mes.equals("Quit")) {
				check = false;
				client.println(mes);
				
				//adds the score to the scoreboard
			} else if (mes.equals(win) || mes.equals(lost) || mes.equals(tie)) {

				Message name = queue.take();
				if (mes.equals(win))
					info.addWin(name.toString());
				else if (mes.equals(lost))
					info.addLost((name.toString()));
				else if (mes.equals(tie))
					info.addTie((name.toString()));
			} else{
				client.println(msg);
			}
		}

		client.close();
	}

	/**
	 * Print method.
	 */
	private void print() {

		printNames();
		printWin();
		printLost();
		printTie();
		printAvailable();
	}

	/**
	 * Prints the names of all the clients
	 */
	private void printNames() {

		ArrayList<String> names = info.getNames();

		for (int i = 0; i < names.size(); i++) {

			String temp = names.get(i);
			client.println(temp);

		}
	}

	/**
	 * Prints the win scores
	 */
	private void printWin() {
		ArrayList<Integer> win = info.getWin();
		for (int i = 0; i < win.size(); i++) {
			client.println(Integer.toString(win.get(i)));
		}
	}

	/**
	 * Prints the lost scores
	 */
	private void printLost() {
		ArrayList<Integer> lost = info.getLost();
		for (int i = 0; i < lost.size(); i++) {
			client.println(Integer.toString(lost.get(i)));
		}
	}

	/**
	 * Prints the tie scores
	 */
	private void printTie() {
		ArrayList<Integer> tie = info.getTie();
		for (int i = 0; i < tie.size(); i++) {
			client.println(Integer.toString(tie.get(i)));
		}
	}

	/**
	 * Prints the availability scores
	 */
	private void printAvailable() {
		ArrayList<Boolean> avai = info.getIfAvailable();
		for (int i = 0; i < avai.size(); i++) {
			client.println(Boolean.toString(avai.get(i)));
		}
	}

}
