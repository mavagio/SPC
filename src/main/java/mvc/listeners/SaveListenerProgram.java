package main.java.mvc.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import main.java.mvc.ToolController;
import main.java.mvc.ToolModel;
import main.java.mvc.gui.ToolView;
import main.java.types.Curriculum;
import main.java.types.program.StudyProgram;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class SaveListenerProgram extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	ToolModel theModel;
	ToolView theView;
	
	

	public SaveListenerProgram(ToolModel theModel, ToolView theView){
		this.theModel = theModel;
		this.theView = theView;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(ToolModel.getCurriculumFolderPath().equals("")){
			ToolView.displayWarningMessage(Messages.getSelectFodersMessage());
			return;
		}
		if(theView.getCurriculumTree().getPaths().length == 0){
			ToolView.displayWarningMessage(Messages.getSelectStudentMessage());
			return;
		}
		String path = ToolController.chooseToolFile(this);
		if (path.equals("No Selection")) {
			ToolView.displayWarningMessage(Messages.getNoSelectionMessage());
			return;
		}
		
		TreePath [] newCurriculumPath = theView.getCurriculumTree().getPaths();
		
		//Get all the programs
		ArrayList<StudyProgram> tempProgArray = getSelectedPrograms(newCurriculumPath);
       			
		theModel.setSaveFolderPath(path+File.separator);

		if(theModel.saveInterpretedProgs(tempProgArray)){
			ToolView.displaySuccessMessageTime(Messages.getSuccessSaveMessage());
		}else{
			theView.displayErrorMessage(Messages.getSelectSaveLocationMessage());
		}		
	}
	
	/**
	 * Gets all the selected programs and puts inside an arrayList
	 * @param tempProgArray is the ArrayList of selected programs
	 * */
	private ArrayList<StudyProgram> getSelectedPrograms(TreePath [] newCurriculumPath) {

		 ArrayList<StudyProgram> tempProgArray = new ArrayList<StudyProgram> ();
		 
         for (TreePath tp : newCurriculumPath) {	
         	String tempCurricName;
         	if(tp.getPath().length == 3){
	            	tempCurricName = tp.getPath()[1].toString().trim();
         	}else{
         		continue;
         	}
         	
         	String tempProgName = tp.getLastPathComponent().toString();
         	tempProgName = tempProgName.substring(0, tempProgName.length() - 8).trim();	            	
         	
         	for(Curriculum tempCurric: theModel.getCurriculums()){
         		if(tempCurric.getName().trim().equals(tempCurricName)){
         			for(StudyProgram tempProg: tempCurric.getProgrames()){
         				if(tempProg.getProgramName().trim().equals(tempProgName)){
         					tempProgArray.add(tempProg);
         				}
         			}
         		}
         	}
         }   
         
		return tempProgArray;
	}
}
