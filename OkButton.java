import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The Class OkButton GUI.
 */
public class OkButton extends JPanel
{

	/** The frame. */
	JFrame frame;
	
	/**
	 * Instantiates a new ok button.
	 *
	 * @param frame the frame of the GUI
	 */
	public OkButton( JFrame frame)
	{
		super();
		this.frame = frame;
		
		JButton okay = new JButton("Ok");
		okay.addActionListener(e -> pressOk());
		
		add(okay);
	
	}
	
	/**
	 * Press ok, to close the window
	 */
	private void pressOk()
	{
		frame.dispose();
	}
}
