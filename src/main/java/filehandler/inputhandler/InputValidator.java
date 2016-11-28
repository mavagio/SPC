package main.java.filehandler.inputhandler;


import java.io.File;
import java.util.ArrayList;

import main.java.mvc.ToolModel;
import main.java.types.program.Program;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class InputValidator {

	protected ArrayList<String> courseText;
	protected String fileName;
						
	public InputValidator(String finleName) {
		
		this.fileName = finleName.substring(finleName.lastIndexOf(File.separator) + 1);
		this.fileName = this.fileName.substring(0,this.fileName.indexOf(".txt"));
		courseText = new ProgramReader(finleName).read();	
	}

	/**
	 * Check validity of students
	 * */
	public boolean checkStudents(ArrayList<String> keyWords){				
		for(String keyWord: keyWords){
			if(!hasWord(keyWord)){				
				ToolModel.setErrorReading(Messages.getWrongDegreeStudentMessage());				
				return false;				
			}				
		}
		
		if(!hasWord(ToolModel.studentKey)){			
			ToolModel.setErrorReading(Messages.getWrongFolderStudentMessage());
			return false;
		}		
		return true;
	}
	
	/**
	 * Check validity of curriculums
	 * */
	public boolean checkCurriculums(ArrayList<String> keyWords){				
		for(String keyWord: keyWords){
			if(!hasWord(keyWord)){				
				ToolModel.setErrorReading(Messages.getWrongDegreeCurriculumMessage());				
				return false;				
			}				
		}
		
		if(!hasWord(ToolModel.curricKey)){			
			ToolModel.setErrorReading(Messages.getWrongFolderCurriculumMessage());
			return false;
		}		
		return true;
	}

	/**
	 * Finds an interval given @param upperKeyWord and @param lowerKeyWord
	 * Used in all *.txt files
	 * */
	protected boolean hasWord(String keyword){
		for (String line : this.courseText) {	
			
			if(line.trim().toLowerCase().indexOf(keyword.trim().toLowerCase()) != -1){
				return true;
			}
		}
		return false;
	}
}
