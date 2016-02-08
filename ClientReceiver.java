
//imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;

/**
 * The Class ClientReceiver Gets messages from other clients via the server (by
 * the ServerSender thread).
 */
public class ClientReceiver extends Thread {

	/** The server. */
	private BufferedReader server;

	/** The game board. */
	private Game game;

	/** The quit true until the clients press quit button. */
	private boolean quit = true;

	/** The to server sender. */
	private PrintStream toServer;

	/** The frame for the main client table. */
	private JFrame frame2;

	/**
	 * The first boolean to determine if the gui have to be updated in order not
	 * to get exception.
	 */
	private boolean first = true;

	/**
	 * The if player begin the game with the same name (in order not to get
	 * exception) .
	 */
	private boolean ifBegin = true;// if player begin the game not with the same
									// name

	/** The begin first for player who begins the game first. */
	private final String beginFirst = "begin";// player who requested starts the

	/** The begin second for player who begins the game second. */
	private final String beginSecond = "start";

	/** The player exists. The message if the player already exists */
	private final String playerExists = "The client with your name already exist, please select another nickname";

	/** The end. Client Exit message */
	private final String end = "End";

	/** The exist. If the player exists. quit */
	private final String exist = "Exist";

	/** The no. Message no from the request */
	private final String no = "No";

	/** The play. Request to play */
	private final String play = "Play";

	/** The info. to display the client info */
	private final String info = "information";

	/** The same player. if request itself */
	private final String samePlayer = "SamePlayer";

	/** The is invited. if the player is already invited */
	private final String isInvited = "isInvited";
	
	/** The is playing. if the player is already playing*/
	private final String isPlaying = "isPlaying";

	/** nickname of the player */
	private final String nickname;
	/**
	 * Instantiates a new client receiver.
	 *
	 * @param server
	 *            the server. To get information from server
	 * @param game
	 *            the game board
	 * @param toServer
	 *            the to server. send information from server
	 */
	ClientReceiver(BufferedReader server, Game game, PrintStream toServer, String nickname) {
		this.server = server;
		this.game = game;
		this.toServer = toServer;
		this.nickname = nickname;
	}

	/*
	 * run method of the thread
	 */
	public void run() {

		try {
			while (quit) {
				String s = server.readLine();
				// if get's the null or end message. Quit the thread
				if (s == null || s.equals(end)) {
					quit = false;
				}
				// if get's the message that player exists
				else if (s.equals(exist)) {
					System.out.println("\n");
					System.out.println(playerExists);
					System.out.println("\n");
					quit = false;
				} else {
					ifBegin = false;
					String s2 = server.readLine();
					if (s2 != null && s != null) {

						// if player starts the game first (is Cross)
						if (s2.equals(beginFirst)) {
							begin(s);

							// if player starts the game second (is Nought)
						} else if (s2.equals(beginSecond)) {
							second(s);

							// request rejected
						} else if (s2.equals(no)) {
							rejected("Player");
						}
						// request to play against other player
						else if (s2.equals(play)) {
							request(s);

							// getting information
						} else if (s.equals(info)) {
							write(s2);

							// when requested yourself
						} else if (s2.equals(samePlayer)) {
							requestedYourself();
						}
						// player is invited
						else if (s2.equals(isInvited)) {
							playerIsInvited();
						}
						
						//player is Playing
						else if(s2.equals(isPlaying))
						{	
							playerIsPlaying();
						}
					}
				}

			}
			if (!ifBegin)
				frame2.dispose();
		} catch (IOException e) {
		
			System.exit(1); // Give up.
		}
	}

	/**
	 * Checks if the string is an integer (required for the move)
	 *
	 * @param s
	 *            the message
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
	 * When the game is started. start this method
	 * 
	 */
	private void playing() {
		try {

			boolean notEnd = true;
			frame2.setEnabled(false);
			while (notEnd) {

				String s2 = server.readLine();
				if (s2 != null) {
					game.setEnable(true);
					if (check(s2)) {
						game.move(Integer.valueOf(s2));
						// if the end message is printed end the game
					} else if (s2.equals("end")) {
						frame2.setEnabled(true);
						notEnd = false;
						game.setFrameInvisible();

						// disable the board until your turn
					} else if (s2.equals("disable")) {
						game.setEnable(false);
					}
				} else {
					server.close(); // Probably no point.
					throw new IOException("Got null from server"); // Caught
																	// below.
				}

			}
		} catch (IOException e) {
			
			System.exit(1); // Give up.
		}
	}

	/**
	 * To write infromation about clients and then pass to dissplay method
	 *
	 * @param numb
	 *            the numb
	 */
	private void write(String numb) {
		int number = Integer.parseInt(numb);
		Object[][] data = new Object[number][5];

		try {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < number; j++) {
					data[j][i] = server.readLine();
				}
			}
			display(data);

			// send to the gui to display the information
		} catch (IOException e) {
			
			System.exit(1); // Give up.
		}

	}

	/**
	 * player who bigins first
	 *
	 * @param s
	 *            the name of the player
	 */
	private void begin(String s) {
		// set the oppenent
		game.begin("("+nickname+ ") Begin X");
		game.isCross("X");
		game.addOpponent(s);
		playing();
	}

	/**
	 * player who begin second
	 *
	 * @param s
	 *            the s
	 */
	private void second(String s) {
		// set the opponent
		game.begin("("+nickname+ ") Second O");
		game.isCross("O");
		game.addOpponent(s);
		game.setEnable(false);
		playing();
	}
	
	/**
	 * 
	 * PLayer is already playing GUI
	 */
	private void playerIsPlaying()
	{
		JFrame frame = new JFrame("Game cancelation");
		OkComponent comp = new OkComponent(
				("Sorry! Player is already in the game. Refresh your board to see availability"), frame);

		frame.setSize(650, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);
	}

	/**
	 * Player is invited GUI.
	 */
	private void playerIsInvited() {
		JFrame frame = new JFrame("Game cancelation");
		OkComponent comp = new OkComponent(
				("Sorry! Player is already invited to play, \n or invited to play another player."), frame);

		frame.setSize(650, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);
	}

	/**
	 * Request for playing the game GUI
	 *
	 * @param name
	 *            the name of the opponent
	 */
	private void request(String name) {
		JFrame frame = new JFrame("Game request");
		RequestComponent comp = new RequestComponent(toServer, name, frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);

	}

	/**
	 * Rejected game GUI
	 *
	 * @param name
	 *            the name
	 */
	private void rejected(String name) {
		JFrame frame = new JFrame("Game cancelation");
		OkComponent comp = new OkComponent((name + " rejected to play against you"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);

	}

	/**
	 * Display f=metod to display the data of all the clients
	 *
	 * @param data
	 *            the data about all the clients
	 */
	private void display(Object[][] data) {
		if (!first)
			frame2.dispose();
		frame2 = new JFrame("[" + nickname + "] Lobby " );
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ViewComponent comp = new ViewComponent(toServer, data);
		frame2.setSize(400, 200);

		frame2.add(comp);

		frame2.setVisible(true);
		first = false;
	}

	/**
	 * Requested yourself GUI
	 */
	private void requestedYourself() {
		JFrame frame = new JFrame("Game cancelation");
		OkComponent comp = new OkComponent(("You cannot request yourself"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);
	}
}
