package main.java.mvc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import main.java.check.CheckTool;
import main.java.check.CheckToolMS;
import main.java.mvc.gui.ToolView;
import main.java.mvc.listeners.CheckListener;
import main.java.mvc.listeners.SaveListenerProgram;
import main.java.mvc.listeners.SaveListenerResult;
import main.java.types.Curriculum;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;
import main.java.util.ErrorMessage;
import main.java.util.Messages;

/**
*@author Martin Avagyan 
* */

/**
 * The controller of the tool.
 * This class controls all the interaction between the view and the model of the tool
 * */

public class ToolController {
	private static ToolView theView;
	private static ToolModel theModel;
	
	public ToolController(ToolView theView, ToolModel theModel) {
		ToolController.theView = theView;
		ToolController.theModel = theModel;
			
		ToolController.theView.addSaveResultsListener(new SaveListenerResult(theModel,theView));
		ToolController.theView.addSaveProgramsListener(new SaveListenerProgram(theModel,theView));
		ToolController.theView.addCheckListener (new CheckListener(theModel,theView));
		ToolController.theView.addStudentChooseListener(new StudentChooseListener());
		ToolController.theView.addCurriculumChooseListener(new CurriculumChooseListener());
		ToolController.theView.addProgramInsertListener(new addProgramListener());
		ToolController.theView.editProgramInsertListener(new editProgramListener());		
		ToolController.theView.addChangeDegreeListener(new ChangeDegreeListener());
	}
	
	
	// TODO test the code
	class ChangeDegreeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {				
			Object source = e.getSource();
			if (source instanceof JButton) {
				/*if (ToolModel.degree.equals("BS")) {
					ToolModel.setDegree("MS");
					((JButton) source).setText("Change Degree " + ToolModel.degree);
				} else {
					ToolModel.setDegree("BS");
					((JButton) source).setText("Change Degree " + ToolModel.degree);
				}*/
				//TODO text here is not in the best way, it should be placed in messages
				ToolView.displayWarningMessage("This version of the program does not support master degree." +
						" \n Please refer an older version form the website \n" +
						"https://martinavagyan.github.io/SPC/");
				resetAll();
			}
		}
	}
	
	// TODO Make it for department
	class ChangeDepartmentListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {				
			Object source = e.getSource();
			if (source instanceof JButton) {
				if (ToolModel.degree.equals("BS")) {
					ToolModel.setDegree("MS");
					((JButton) source).setText("Change Degree " + ToolModel.degree);
				} else {
					ToolModel.setDegree("BS");
					((JButton) source).setText("Change Degree " + ToolModel.degree);
				}
				resetAll();
			}
		}
	}
	
	//TODO make this better
	private static void resetAll(){
		theView.resetAll();
		theModel.resetAll();
	}
	
	//TODO ADD Student folder listener 
	class StudentChooseListener extends JPanel implements ActionListener{		

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String path = chooseToolDirectory();
			
			if (!path.equals("No Selection")) {
				theModel.setStudentFolderPath(path);
				theView.setStudentDirectory(path);
				if(!ToolValidator.checkValidityStudent()){
					ToolView.displayWarningMessage(Messages.getWrongFolderHintMessage()
												+ ToolModel.getErrorReading());
					return;
				}
				theModel.readAllStudents();
				
				if(theModel.getStudentProgrames() == null){
					resetAll();
					ToolView.displayWarningMessage(Messages.getInterpretError()+ErrorMessage.getInstance().getMessage());
				}else{
					theView.setStudentProgram(theModel.getStudentProgrames());
					theView.updateStudents();	
				}					
			} else {
				ToolView.displayWarningMessage(Messages.getNoSelectionMessage());
			}
		}
	}
	
	//TODO ADD Curriculum folder listener 	
	public class CurriculumChooseListener extends JPanel implements ActionListener{		
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			curriculumChooseAction();
		}
	}

	//TODO chenage this 
	public static void curriculumChooseAction(){
		String path = chooseToolDirectory();
		
		if (!path.equals("No Selection")) {
			theModel.setCurriculumFolderPath(path);
			theView.setCurriculumDirectory(path);		
			if(!ToolValidator.checkValidityCurriculum()){
				ToolView.displayWarningMessage(Messages.getWrongFolderHintMessage()
											+ ToolModel.getErrorReading());
				return;
			}
			theModel.readAllCurriculums();
			if(theModel.getCurriculums() == null){
				resetAll();
				ToolView.displayWarningMessage(Messages.getInterpretError()+ErrorMessage.getInstance().getMessage());
				return;
			}
			
			theView.setCurriculums(theModel.getCurriculums());
			theView.updateCurriculums();	
			
		} else {
			ToolView.displayWarningMessage(Messages.getNoSelectionMessage());
		}
	}
	
	//TODO chenage this 
	public static void curriculumChooseActionInsertion(String path){
			theModel.setCurriculumFolderPath(path);		
			theView.setCurriculumDirectory(path);		
			theModel.readAllCurriculums();
			if(theModel.getCurriculums() == null){
				resetAll();
				ToolView.displayWarningMessage(Messages.getInterpretError()+ErrorMessage.getInstance().getMessage());
				return;
			}
			theView.setCurriculums(theModel.getCurriculums());	
			theView.updateCurriculums();	
	}
	
	/**
	 * Method for selecting a folder from the file system
	 * With message box if there is no selection 
	 * */
	public static String chooseDirectoryInsertion(){
		String path = chooseToolDirectory();
		
		if (!path.equals("No Selection")) {
			return path;			
		}
		ToolView.displayWarningMessage(Messages.getNoSelectionMessage());
		return "No Selection";
	}
	
	/**
	 * Method for selecting a folder from the file system
	 * Returns the folder path if selected, else returns 
	 * -No Selection string
	 * */
	public static String chooseToolDirectory(){
		String choosertitle = "Choose Folder";
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {			
			return  chooser.getSelectedFile().getAbsolutePath();
		}
		return "No Selection";	
	}
	
	/**
	 * Method for selecting a file from the file system
	 * Returns the file path if selected, else returns 
	 * "No Selection" string
	 * */
	public static String chooseToolFile(JPanel tempPanel){
		String choosertitle = "Choose Folder";
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(tempPanel) == JFileChooser.APPROVE_OPTION) {			
			return  chooser.getSelectedFile().getAbsolutePath();
		}
		return "No Selection";	
	}
	
	//TODO separate this
	class addProgramListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			theView.insertionDialog();
		}
	}
	class editProgramListener implements ActionListener {			
		public void actionPerformed(ActionEvent e) {	
			StudyProgram tempProg = new StudyProgram();
			try{
				//check if the student folder and curriculum folder are choose
				if(ToolModel.getCurriculumFolderPath().equals("")){
					ToolView.displayWarningMessage(Messages.getSelectFodersMessage());
					return;
				}				
				//check if 1 program is selected
				System.out.println(theView.getCurriculumTree().getPaths().length );
				if(theView.getCurriculumTree().getPaths().length !=3){
					theView.displayWarningMessage(Messages.getSelectStudyProgramEditMessage());
					return;
				}
				//get select paths of programs
				TreePath [] newCurriculumPath = theView.getCurriculumTree().getPaths();
								
	            //Get the program
				tempProg = getSelectedProgram(newCurriculumPath);
			}catch(NumberFormatException ex){
				theView.displayErrorMessage(Messages.getCheckErrorMessage());
				ex.printStackTrace();
			}
			
			if(tempProg!=null){
				theView.editDialog(tempProg);
			}else{
				theView.displayWarningMessage(Messages.getSelectStudyProgramEditMessage());
				return;
			}			
		}
		private StudyProgram getSelectedProgram(TreePath [] newCurriculumPath) {
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
	         					return tempProg;
	         				}
	         			}
	         		}
	         	}
	         }   
	         return null;
		}
	}
}

