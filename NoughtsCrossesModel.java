
//imports
import java.io.PrintStream;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

/**
 * The Class NoughtsCrossesModel.
 */
public class NoughtsCrossesModel extends Observable {

	/** The Noughts and Crosses game */
	private NoughtsCrosses oxo;

	/** The move. */
	public boolean move = false;

	/** The turn made. */
	private String turnMade;

	/** The server stream */
	private PrintStream server;

	/** The sign. */
	private int sign;

	/** The name of opponent */
	private String nameOpp;

	/** The counter. */
	private int counter = 0;

	/**
	 * Instantiates a new noughts crosses model.
	 *
	 * @param oxo
	 *            the game
	 * @param server
	 *            the server stream
	 */
	public NoughtsCrossesModel(NoughtsCrosses oxo, PrintStream server) {
		super();
		this.oxo = oxo;
		this.server = server;
	}

	/**
	 * Printing move to the server
	 *
	 * @param msg
	 *            the msg
	 */
	public void printing(String msg) {
		// if the current player is cross and he made a move print to server
		if (sign == oxo.CROSS && !isCrossTurn()) {

			server.println(nameOpp);
			server.println(msg);
			if (whoWon() == oxo.BLANK) {

				server.println(nameOpp);
				server.println("disable");
			}

		}
		// if the current player is nougth and he made a move print to server
		else if (sign == oxo.NOUGHT && isCrossTurn()) {

			server.println(nameOpp);
			server.println(msg);
			if (whoWon() == oxo.BLANK) {

				server.println(nameOpp);
				server.println("disable");
			}

		}

	}

	/**
	 * Adds the opponent name which could be printed to the server
	 *
	 * @param name
	 *            the name
	 */
	public void addOpponentName(String name) {
		nameOpp = name;
	}

	/**
	 * Adds my sign.
	 *
	 * @param x
	 *            the x
	 */
	public void addMySign(int x) {
		sign = x;
	}

	/**
	 * Gets the sign at the specified position
	 *
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the int
	 */
	public int get(int i, int j) {
		return oxo.get(i, j);
	}

	/**
	 * Adds the move.
	 *
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 */
	public void addMove(int i, int j) {
		turnMade = "";
		turnMade += Integer.valueOf(i);
		turnMade += Integer.valueOf(j);
		printing(turnMade);
	}

	/**
	 * Check if it is Cross or nought turn
	 *
	 * @return true if it is cross's turn, false for nought's turn
	 */
	public boolean isCrossTurn() {
		return oxo.isCrossTurn();
	}
	/**
	 * Checks if there is a winner and prints message to end the game
	 * and add the score to the scoreboard
	 */
	public void checkWinner()
	{
		// checks if there is a winner
				if (whoWon() != oxo.BLANK) {
					// if the cross won end the game and add the winner to the score
					// board
					if (sign != oxo.CROSS && whoWon() == oxo.CROSS) {
						server.println(nameOpp);
						server.println("win");
						server.println(nameOpp);
						server.println("end");
						lost();

						// if the nought won end the game and add the looser to the
						// score board
					} else {
						server.println(nameOpp);
						server.println("lost");
						server.println(nameOpp);
						server.println("end");
						win();
					}
					counter = 0;
					// sleep before closing the game GUI
					try {
						TimeUnit.SECONDS.sleep(3);

					} catch (InterruptedException e) {
						// Handle exception
					}
					newGame();

					// if there is no winner until end, print tie game message to server
				} else if (counter == 9) {
					server.println(nameOpp);
					server.println("tie");
					server.println(nameOpp);
					server.println("end");
					draw();
					counter = 0;
					try {
						TimeUnit.SECONDS.sleep(3);

					} catch (InterruptedException e) {
						// Handle exception
					}
					newGame();

				}
	}

	/**
	 * Let the player whose turn it is play at a particular location.
	 *
	 * @param i
	 *            the row
	 * @param j
	 *            the column
	 */
	public synchronized void turn(int i, int j) {

		counter++;
		oxo.turn(i, j);
		setChanged();
		notifyObservers();
		addMove(i, j);
		checkWinner();

		

	}

	/**
	 * starts won game GUI message
	 */
	private void win() {
		JFrame frame = new JFrame("Game");
		OkComponent comp = new OkComponent(("Congratulations! You won a game!"), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);
	}

	/**
	 * starts lost game GUI message
	 */
	private void lost() {
		JFrame frame = new JFrame("Game");
		OkComponent comp = new OkComponent(("You lost the game... Try another time..."), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);
	}

	/**
	 * start tie game GUI message
	 */
	private void draw() {
		JFrame frame = new JFrame("Game");
		OkComponent comp = new OkComponent(("Draw. Friendship won! "), frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);
	}

	/**
	 * Determine who (if anyone) has won.
	 *
	 * @return CROSS if cross has won, NOUGHT if nought has won, oetherwise
	 *         BLANK
	 */
	public int whoWon() {
		return oxo.whoWon();
	}

	/**
	 * Start a new game.
	 */
	public void newGame() {
		oxo.newGame();
		setChanged();
		notifyObservers();
	}
}