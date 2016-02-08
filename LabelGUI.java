
//imports
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The Class LabelGUI.
 */
public class LabelGUI extends JPanel {

	/**
	 * Instantiates a new label gui.
	 *
	 * @param message
	 *            the message of the GUI
	 */
	public LabelGUI(String message) {
		super();

		// label
		JLabel label = new JLabel(message, SwingConstants.CENTER);

		// sets the border layout
		setLayout(new BorderLayout());

		// sets the position
		add(label, BorderLayout.EAST);

		// adds the label onto the panel
		add(label);
	}

}
