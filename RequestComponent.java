//imports
import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class RequestComponent.
 */
public class RequestComponent extends JPanel {
	
	/**
	 * Instantiates a new request component.
	 *
	 * @param server the server stream
	 * @param name the name of the opponent
	 * @param frame the frame of the GUI
	 */
	public RequestComponent(PrintStream server, String name, JFrame frame) {
		super();

		//creates the label and the request winow
		LabelGUI label = new LabelGUI((name + " requested to play against you!"));
		RequestWindow window = new RequestWindow(name, server, frame);
		setLayout(new BorderLayout());

		//adds the label and the window to the panel
		add(label, BorderLayout.CENTER);
		add(window, BorderLayout.SOUTH);
	}
}
