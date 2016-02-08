//imports
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class RequestWindow.
 */
public class RequestWindow  extends JPanel
{

	/** The to server. */
	PrintStream toServer;
	
	/** The name. */
	String name;
	
	/** The frame. */
	JFrame frame;
	
	/**
	 * Instantiates a new request window.
	 *
	 * @param name the name
	 * @param toServer the to server
	 * @param frame the frame
	 */
	public RequestWindow(String name, PrintStream toServer, JFrame frame)
	{
		super();
		this.frame = frame;
		this.name = name;
		this.toServer = toServer;
		
		JButton yes = new JButton("Yes");
		yes.addActionListener(e -> printYes());
		
		JButton no = new JButton("No");
		no.addActionListener(e -> printNo());
		
		add(yes);
		add(no);
	}
	
	/**
	 * Prints the yes when Yes button is pressed.
	 * 
	 */
	private void printYes()
	{
		toServer.println(name);
		toServer.println("Yes");
		frame.dispose();
	}
	
	/**
	 * Prints the no when No button is pressed.
	 */
	private void printNo()
	{
		toServer.println(name);
		toServer.println("No");
		frame.dispose();
	}
}
