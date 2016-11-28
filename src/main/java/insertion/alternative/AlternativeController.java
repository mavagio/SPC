package main.java.insertion.alternative;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import main.java.insertion.InsertionController;
import main.java.mvc.gui.ToolView;


/**
 * @author Martin Avagyan 
 */

public class AlternativeController {
	
	private AlternativeFrame theView;
	private AlternativeModel theModel;
	private InsertionController mainController;
	
	public AlternativeController(AlternativeFrame theView,AlternativeModel theModel,InsertionController mainController){
		this.theView = theView;
		this.theModel = theModel;
		this.mainController = mainController;
		
		//IMPORTANT! the order of the following three is very important
		
		//Generate the alt expression
		theModel.altStringToAltExpression();
		
		//Generate the table
		theModel.initTable(theView.getTable());
		
		//Set the expression in the view
		theView.getFooter().setExpressionFildText(theModel.getAlternatives());
		
		this.theView.addCancelListener(new InsertionCancelListener());
		this.theView.addNextListener(new InsertionNextListener());
		//Add header listeners
		this.theView.addAddRowListener(new AddRowButtonListener());
		this.theView.addRemoveRowListener(new RemoveRowButtonListener());
	}
	

	class InsertionCancelListener implements ActionListener{	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(theView.displayQuestion("Are you sure, you want to cancel?")){
				theView.exitFrame();
			}
		}
	}
	class InsertionNextListener implements ActionListener{	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(theView.submit()){
				theModel.tableToAlternativeString(theView.getFooter().getExpressionFildText(),
											     (DefaultTableModel) theView.getTable().getModel());
				mainController.addAlternative(theModel.getAlternatives());
				ToolView.displaySuccessMessageTime("Alternatives are successfully added!");
			}			
		}
	}	
	

	class AddRowButtonListener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			AlternativeTable table = theView.getTable();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			if(model.getRowCount()<=25){
		     	model.addRow(new Object[]{theModel.c++, "", "",""});
		     	table.setRowHeight(table.getRowCount()-1, 25);
			}
		}
	}
	
	class RemoveRowButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AlternativeTable table = theView.getTable();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			if((table.getRowCount()-1)>=0){
				model.removeRow(table.getRowCount()-1);
				theModel.c--;
			}
		}
	}
	
	
}
