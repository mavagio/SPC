package main.java.mvc;

import java.io.File;

import javax.swing.tree.TreePath;

import main.java.mvc.gui.ToolView;
import main.java.types.program.StudyProgram;
import main.java.types.tree.TreeMakerInsertionCurriculum;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class SaveProgram {
	TreeMakerInsertionCurriculum curricTree;
	StudyProgram program;

	public SaveProgram(TreeMakerInsertionCurriculum curricTree,StudyProgram program){
		this.curricTree = curricTree;
		this.program = program;
	}
	

	public void saveAction(){
		//check if the folder is selected
		if(ToolModel.getCurriculumFolderPath().equals("")){
			ToolView.displayWarningMessage(Messages.getSelectFodersMessage());
			return;
		}	
		
		//check if a folder is selected
		if(curricTree.getPaths().length == 0){
			ToolView.displayWarningMessage(Messages.getChooseCurriculumMessage());
			return;
		}
		
		//check if more than 1 folder is selected
		if(curricTree.getPaths().length > 2){
			ToolView.displayWarningMessage(Messages.getSingleFolderMessage());
			return;
		}
		
		//get select paths of a curriculum
		TreePath [] curriculumPath = curricTree.getPaths();
		
		//Set the study year
		String year = getSelectedPrograms(curriculumPath);
		year = year.replaceAll("[^\\d.]", "");
		program.setStudyYear(year);
		
		//Get the correct path
		String path = ToolModel.getCurriculumFolderPath() + File.separator
				+ getSelectedPrograms(curriculumPath) + File.separator;
		ToolModel.saveStudyProgram(program,path);
		ToolView.displaySuccessMessageTime(Messages.getSuccessSavedProgramMessage());
	}
			
		
	/**
	 * Gets the name of the curriculum and returns it
	 * */
	private String getSelectedPrograms(TreePath[] newCurriculumPath) {
		String tempCurricName = "";

		for (TreePath tp : newCurriculumPath) {
			if (tp.getPath().length >=2) {
				tempCurricName = tp.getPath()[1].toString().trim();
			}
		}		
		return tempCurricName;
	}
		
}
