package main.java.insertion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.table.DefaultTableModel;

import main.java.insertion.alternative.AlternativeController;
import main.java.insertion.alternative.AlternativeFrame;
import main.java.insertion.alternative.AlternativeModel;
import main.java.mvc.SaveProgram;
import main.java.mvc.ToolController;
import main.java.mvc.gui.ToolView;
import main.java.types.program.StudyProgram;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class InsertionController {
	
	private InsertionFrame theView;
	private InsertionModel theModel;
	private InsertionController thisInstance;
	private String courseCode;
	
	
	public InsertionController(InsertionFrame theView,InsertionModel theModel){
		this.theView = theView;
		this.theModel = theModel;
		thisInstance= this;
		
		//Footer listeners
		this.theView.addCancelListener(new InsertionCancelListener());
		this.theView.addNextListener(new InsertionNextListener());
		this.theView.addBackListener(new InsertionBackListener());
		
		//Header listeners
		this.theView.addAddRowListener(new AddRowButtonListener());
		this.theView.addRemoveRowListener(new RemoveRowButtonListener());
		this.theView.addAlternativeListener(new AlternativeListener());
		this.theView.addCurriculumListener(new AddCurriculumListener());
	}
	
	public InsertionController(InsertionFrame theView,InsertionModel theModel,StudyProgram editProg){
		this(theView,theModel);
		generateTables(editProg);
	}
	
	private void generateTables(StudyProgram editProg){
		theModel.setTableModels(theView.generateTableModels());
		theModel.generateStudyProgramTables(editProg);
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
			//If true than the table was submitted with the program info
			if(theView.nextTable()){	
				
				theModel.setTableModels(theView.generateTableModels());
				theModel.setName(theView.getInfoPanel().getNameText());
				
				StudyProgram newProg = theModel.generateStudyProgram();
				SaveProgram progSave = new SaveProgram(theView.getInfoPanel().getCurricTree(),newProg);
				progSave.saveAction();
			}
		}
	}
	
	class AddRowButtonListener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			InsertionTable table = theView.getCurrentTable();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
	     	model.addRow(new Object[]{"", "", ""});
	     	table.setRowHeight(table.getRowCount()-1, 25);
		}
	}
	

	class RemoveRowButtonListener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			InsertionTable table = theView.getCurrentTable();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			if((table.getRowCount()-1)>=0){
				model.removeRow(table.getRowCount()-1);
			}
		}
	}
	
	class AlternativeListener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			String currentAlternatives = "";
			courseCode = "";
			InsertionTable table = theView.getCurrentTable();
			int index = table.getSelectedRow();
			if(index == -1){
				//TODO message dialog
				ToolView.displayWarningMessage(Messages.getSelectRowMessage());
			}else{
				//TODO Open alternative window
				if(table.getModel().getValueAt(index, 0)==null){
					ToolView.displayWarningMessage("Please enter the course first");
					return;
				}
				//Get the course code for which we put the alternative
				courseCode = table.getModel().getValueAt(index, 0).toString().trim();

				
				if(theModel.getAlternativeCourses().containsKey(courseCode)){
					currentAlternatives = theModel.getAlternativeCourses().get(courseCode);
				}
				
				AlternativeFrame newAltView = new AlternativeFrame();
				AlternativeModel newAltModel = new AlternativeModel(currentAlternatives);
				AlternativeController newController = new AlternativeController(newAltView,newAltModel,thisInstance);
			}			  	
		}
	}
	
	public void addAlternative(String exp){
		theModel.getAlternativeCourses().put(courseCode, exp);
	}
	
	class AddCurriculumListener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {			
			String curricPath= "";
			String newCurric = theView.insertNewCurriculumInfo();
			if(newCurric.equals("0")){ //if user cancels on the runtime
				return;
			}
			String path = ToolController.chooseDirectoryInsertion();
			if(path.equals("No Selection")){//if the user cancels choice on the runtime
				return;
			}
			
			curricPath = path + File.separator + newCurric;			
			new File(curricPath).mkdir();	
			ToolController.curriculumChooseActionInsertion(path);
			theView.getInfoPanel().updateCurriculums();
		}
	}
	
	
	
	class InsertionBackListener implements ActionListener{	
		@Override
		public void actionPerformed(ActionEvent e) {
			theView.backTable();
		}
	}
}
