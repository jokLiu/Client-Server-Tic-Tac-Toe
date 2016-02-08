//imports
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * The Class ViewTable.
 */
public class ViewTable extends JPanel{

	/** The column names. */
	private Object[] columnNames = {"Client Name" , "Win", "Lost", "Tie", "Available"};
	
	/** The client data. */
	private Object[][] clientData;
	
	/** The list. */
	private JTable list;
	
	/**
	 * Instantiates a new view table.
	 *
	 * @param data the data of the players
	 */
	public ViewTable(Object[][] data)
	{
		super();
		this.clientData = data;
		//default table model
		DefaultTableModel model = new DefaultTableModel(clientData, columnNames);
		
		//sets the grid layout
		setLayout(new GridLayout(1,0));
		list = new JTable(model);

		//sets the selection mode that only one line could be selected
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(list));
	
	
	}
	
	/**
	 * Gets the selected name.
	 *
	 * @return the selected name
	 */
	public String getSelectedName()
	{
		int row = list.getSelectedRow();
	
		String name = (String)list.getValueAt(row, 0);
		
		return name;
		
	}
	
	/**
	 * Gets the availability in string "true" or "false"
	 *
	 * @return the available
	 */
	public String getAvailable()
	{
		int row = list.getSelectedRow();
		String check = (String)list.getValueAt(row, 4);
		
		return check;
	}
	
	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	public boolean isSelected()
	{
		int row = list.getSelectedRow();
		return (row != -1);
	}
	
}
