//imports
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class OkComponent.
 */
public class OkComponent  extends JPanel
{
	
	/**
	 * Instantiates a new ok component.
	 *
	 * @param name the name of the label
	 * @param frame the frame of the Ok GUI
	 */
	public OkComponent( String name, JFrame frame)
	{
		super();
		
		//creates a lable and the button
		LabelGUI label = new LabelGUI(name );
		OkButton button  = new OkButton( frame);
		setLayout(new BorderLayout());
		
		//add label and button to the GUI
		add(label, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
	}
}
