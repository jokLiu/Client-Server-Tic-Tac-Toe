//imports
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class ViewButtons.
 */
public class ViewButtons extends JPanel {


	/** The to server. */
	private PrintStream toServer;
	
	/** The table. */
	private ViewTable table;

	/**
	 * Instantiates a new view buttons.
	 *
	 * @param table the table GUI
	 * @param server the server stream
	 */
	public ViewButtons(ViewTable table, PrintStream server) {
		super();
		this.toServer = server;
		this.table = table;

		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(e -> printRefresh());

		JButton exit = new JButton("Quit");
		exit.addActionListener(e -> printQuit());

		JButton request = new JButton("Request");
		request.addActionListener(e -> printRequest());

		add(request);
		add(refresh);
		add(exit);

	}

	/**
	 * Prints the request to other client
	 */
	private void printRequest() {
		//checks if the something is selected in the table
		if (table.isSelected()) {
			String name = table.getSelectedName();
			
			//checks if a player is available
			if (table.getAvailable().equals("true")) {
				toServer.println(name);
				toServer.println("Play");
			} else {
				isNotAvailableGUI(name);
			}
		}
		else nothingSelectedGUI();
	}

	/**
	 * Prints the refresh.
	 */
	private void printRefresh() {
		toServer.println("info");
		toServer.println("info");
	}

	/**
	 * Prints the quit.
	 */
	private void printQuit() {
		toServer.println("Game");
		toServer.println("End");
	}

	/**
	 * Not available gui.
	 *
	 * @param name the message of for the GUI window
	 */
	private void isNotAvailableGUI(String name) {
		JFrame frame = new JFrame("Request cancelation");
		OkComponent comp = new OkComponent(name + " is already playing", frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);

	}
	
	/**
	 * Nothing selected gui.
	 */
	private void nothingSelectedGUI()
	{
		JFrame frame = new JFrame("Error");
		OkComponent comp = new OkComponent("No player is selected. Please select a player", frame);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(comp);

		frame.setVisible(true);
	}
}
