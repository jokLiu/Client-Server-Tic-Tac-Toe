//imports
import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JPanel;

/**
 * The Class ViewComponent.
 */
public class ViewComponent extends JPanel {

	/** The server. */
	private PrintStream server;
	
	/** The table. */
	private ViewTable table;
	
	/**
	 * Instantiates a new view component.
	 *
	 * @param toServer the to server
	 * @param data the data
	 */
	public ViewComponent(PrintStream toServer, Object[][] data)
	{
		super();
		this.server = toServer;
		table = new ViewTable(data);
		ViewButtons buttons = new ViewButtons(table, server);
		setLayout(new BorderLayout());
		
		//adds the table and buttons to the panel
		add(table, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
		
	}
	
}
