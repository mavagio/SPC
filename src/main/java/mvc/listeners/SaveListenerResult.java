package main.java.mvc.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import main.java.mvc.ToolController;
import main.java.mvc.ToolModel;
import main.java.mvc.gui.ToolView;
import main.java.types.program.StudentProgram;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class SaveListenerResult extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	ToolModel theModel;
	ToolView theView;
	
	

	public SaveListenerResult(ToolModel theModel, ToolView theView){
		this.theModel = theModel;
		this.theView = theView;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(ToolModel.getStudentFolderPath().equals("") || ToolModel.getCurriculumFolderPath().equals("")){
			ToolView.displayWarningMessage(Messages.getSelectFodersMessage());
			return;
		}
		
		if(theView.getResultTree().getPaths().length == 0){
			ToolView.displayWarningMessage(Messages.getSelectStudentMessage());
			return;
		}
		String path = ToolController.chooseToolFile(this);
		if (path.equals("No Selection")) {
			ToolView.displayWarningMessage(Messages.getNoSelectionMessage());
			return;
		}
		
		
		
		TreePath [] newStudentPath = theView.getResultTree().getPaths();
		
		//For students
		ArrayList<StudentProgram> tempStudArray = new ArrayList<StudentProgram> ();
        for (TreePath tp : newStudentPath) {	            	
        	String tempStudentNumber = tp.getLastPathComponent().toString();   
			Matcher m = Pattern.compile("\\((.*?)\\)").matcher(
					tempStudentNumber);
			while (m.find()) {
				tempStudentNumber = m.group(1);
			}
			
        	for(StudentProgram tempStudent: theView.getResultStudentPrograms()){
        		if(tempStudent.getStudentNumber().trim().equals(tempStudentNumber)){
        			tempStudArray.add(tempStudent);
        		}
        	}
        }
        
        
		theModel.setSaveFolderPath(path+File.separator);

		if(theModel.saveInterpretedStuds(tempStudArray)){
			ToolView.displaySuccessMessageTime(Messages.getSuccessSaveMessage());
		}else{
			theView.displayErrorMessage(Messages.getSelectSaveLocationMessage());
		}
		
	}
}
