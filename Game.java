
//imports
import javax.swing.JFrame;

/**
 * The Class Game which keeps the game board
 */
public class Game {

	/** The board. */
	public NoughtsCrosses board;

	/** The frame. */
	private JFrame frame;

	/** The opponent. */
	private String opponent;

	/** The is in game. */
	private boolean isInGame = false;

	/** The _model. */
	private NoughtsCrossesModel _model;

	/**
	 * Instantiates a new game.
	 *
	 * @param board the Noughts and Crosses board
	 *            
	 * @param model the model
	 *            
	 */
	public Game(NoughtsCrosses board, NoughtsCrossesModel model) {
		this.board = board;
		this._model = model;

	}

	/**
	 * Adds the opponent.
	 *
	 * @param name the name
	 *            
	 */
	public void addOpponent(String name) {
		opponent = name;
		_model.addOpponentName(opponent);
	}

	/**
	 * Checks if is started.
	 *
	 * @param check the check
	 *            
	 */
	public void isStarted(boolean check) {
		isInGame = check;
	}

	/**
	 * Checks if is cross.
	 *
	 * @param s  the s
	 *           
	 */
	public void isCross(String s) {
		if (s.equals("X")) {
			_model.addMySign(board.CROSS);
		} else if (s.equals("O")) {
			_model.addMySign(board.NOUGHT);
		}
	}

	/**
	 * Checks if is playing.
	 *
	 * @return true, if is playing
	 */
	public boolean isPlaying() {
		return isInGame;
	}

	/**
	 * Gets the opponent.
	 *
	 * @return the opponent
	 */
	public String getOpponent() {
		return opponent;
	}

	/**
	 * Begins the game board
	 *
	 * @param s the s
	 *            
	 */
	public void begin(String s) {

		frame = new JFrame(s);
		NoughtsCrossesComponent comp = new NoughtsCrossesComponent(board, _model);

		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);

	}

	/**
	 * Sets the frame invisible.
	 */
	public void setFrameInvisible() {
		frame.dispose();
	}

	/**
	 * Sets the enable.
	 *
	 * @param dis the disable of the game
	 *            
	 */
	public void setEnable(boolean dis) {
		frame.setEnabled(dis);
	}

	/**
	 * Gets the frame.
	 *
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * adds the move
	 *
	 * @param move 
	 *            
	 */
	public void move(int move) {

		int i = move / 10;
		int j = move % 10;
		_model.turn(i, j);

	}

}
