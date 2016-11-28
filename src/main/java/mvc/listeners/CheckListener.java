package main.java.mvc.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.TreePath;

import main.java.check.CheckTool;
import main.java.check.CheckToolMS;
import main.java.mvc.ToolModel;
import main.java.mvc.gui.ToolView;
import main.java.types.Curriculum;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;
import main.java.util.ErrorMessage;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class CheckListener implements ActionListener{
	ToolModel theModel;
	ToolView theView;


	public CheckListener(ToolModel theModel, ToolView theView){
		this.theModel = theModel;
		this.theView = theView;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {			
		try{
			//check if the student folder and curriculum folder are choose
			if(theModel.getStudentFolderPath().equals("") || ToolModel.getCurriculumFolderPath().equals("")){
				theView.displayWarningMessage(Messages.getSelectFodersMessage());
				return;
			}
			
			//check at least one program and student is chosen
			if(theView.getStudentTree().getPaths().length == 0 || theView.getCurriculumTree().getPaths().length == 0){
				theView.displayWarningMessage(Messages.getSelectStudentMessage());
				return;
			}
			
			//get select paths of students and programs
			TreePath [] newStudentPath = theView.getStudentTree().getPaths();
			TreePath [] newCurriculumPath = theView.getCurriculumTree().getPaths();
			
			//For students
			ArrayList<StudentProgram> tempStudArray = getSelectedStudents(newStudentPath);
			
            //For Programs
			ArrayList<StudyProgram> tempProgArray = getSelectedPrograms(newCurriculumPath);

			// Check for which degree
			CheckTool newCheck;
			if (ToolModel.degree.equals("BS")) {
				// Main check with all BS students and programs
				newCheck = new CheckTool(tempProgArray, tempStudArray);
			} else {
				// Main check with all MS students and programs
				newCheck = new CheckToolMS(tempProgArray, tempStudArray);
			}

			newCheck.checkAll();
			if(newCheck.isSuccessful()==false){
				ToolView.displayWarningMessage(Messages.getCheckError()+ErrorMessage.getInstance().getMessage());
				return;
			}
			
			
			// get checked students
            tempStudArray =  newCheck.getFinalStudentProgrames();
            
            //update results
            theView.setResultStudentPrograms(tempStudArray);
            theView.updateResults();            
            
		}catch(NumberFormatException ex){
			theView.displayErrorMessage(Messages.getCheckErrorMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Gets all the selected students and puts inside an arrayList
	 * @param tempStudArray is the ArrayList of selected students
	 * */
	private ArrayList<StudentProgram> getSelectedStudents(TreePath [] newStudentPath){
		
		ArrayList<StudentProgram> tempStudArray = new ArrayList<StudentProgram> ();
		
		for (TreePath tp : newStudentPath) {	            	
        	String tempStudentNumber = tp.getLastPathComponent().toString();   
			Matcher m = Pattern.compile("\\((.*?)\\)").matcher(
					tempStudentNumber);
			while (m.find()) {
				tempStudentNumber = m.group(1);
			}
			
        	for(StudentProgram tempStudent: theModel.getStudentProgrames()){
        		if(tempStudent.getStudentNumber().trim().equals(tempStudentNumber)){
        			tempStudArray.add(tempStudent);
        		}
        	}
        }
		
		return tempStudArray;
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
