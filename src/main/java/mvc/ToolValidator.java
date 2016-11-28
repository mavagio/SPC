package main.java.mvc;

import java.io.File;

import main.java.filehandler.inputhandler.InputValidator;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

/**
 * The following class is designed to validate the input before interpretation
 * The class has methods for both student folder validation and curriculum folder
 * The class also handles partially the error message that the user will receive
 * */
public class ToolValidator {
		
	/**
	 * Student folder validity
	 * */
	public static boolean checkValidityStudent(){	
		boolean isValid = false;
		File folder = new File(ToolModel.studentFolderPath);
		File[] listOfFiles = folder.listFiles();
		File file;
		String path;
		
		for (int i = 0; i < listOfFiles.length; i++) {
			file = listOfFiles[i];
			if (file.isFile() && file.getName().endsWith(".txt")) {
				isValid = true;
				path = ToolModel.studentFolderPath.trim()+"\\"+ file.getName().trim();
				if (!new InputValidator(path).checkStudents((ToolModel.degree
						.equals("BS")) ? ToolModel.getDepartment().getBsFields()
						.getAllFields() : ToolModel.getDepartment().getMsFields()
						.getAllFields())) {
					return false;
				}
			}
		}
		
		if(!isValid){
			ToolModel.setErrorReading(Messages.getEmpltyFolderError());
		}
		return isValid;
	}
	
	/**
	 * Curriculum folder validity
	 * */
	public static boolean checkValidityCurriculum(){
		boolean isValid = false;
		boolean allProgsValid = true;
		File folder = new File(ToolModel.curriculumFolderPath);
		File[] listOfFiles = folder.listFiles();
		String path;
		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isDirectory()) {	
				isValid = true;
				path = ToolModel.curriculumFolderPath+File.separator +file.getName();				
				if(! checkValidityProgram(path)){
					allProgsValid = false;
				}	
			}
		}
		
		if (!isValid) {
			ToolModel.setErrorReading(Messages.getEmpltyFolderError());
		}
		if(!allProgsValid){
			//TODO set error for 1 prog not valid ToolModel.setErrorReading();
		}
		return isValid && allProgsValid;
	}
	
	/**
	 * Program validity
	 * */	
	private static boolean checkValidityProgram(String folderpath) {
		boolean isValid = false;
		File folder = new File(folderpath);
		File[] listOfFiles = folder.listFiles();
		File file;
		String path;

		for (int i = 0; i < listOfFiles.length; i++) {
			file = listOfFiles[i];
			if (file.isFile() && file.getName().endsWith(".txt")) {
				isValid = true;
				path = folderpath + File.separator + file.getName().trim();
				if (!new InputValidator(path).checkCurriculums((ToolModel.degree
						.equals("BS")) ? ToolModel.getDepartment().getBsFields()
						.getAllFields() : ToolModel.getDepartment().getMsFields()
						.getAllFields())) {
					return false;
				}
			}
		}

		if (!isValid) {
			ToolModel.setErrorReading(Messages.getEmpltyFolderError());
		}

		return isValid;
	}
}
