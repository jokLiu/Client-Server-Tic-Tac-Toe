//imports
import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * The Class NoughtsCrossesComponent.
 */
public class NoughtsCrossesComponent extends JPanel
{
	
	/**
	 * Instantiates a new noughts crosses component.
	 *
	 * @param game the game
	 * @param model the model
	 */
	public NoughtsCrossesComponent(NoughtsCrosses game, NoughtsCrossesModel model)
	{
		super();
		
		//board 
		BoardView board = new BoardView(model);

		//add observers to the model
		model.addObserver(board);
		
		//set border layout
		setLayout(new BorderLayout());
		
		//add board to the panel
		add(board, BorderLayout.CENTER);

	}
}
