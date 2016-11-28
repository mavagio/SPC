package main.java.filehandler.inputhandler;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.types.Course;
import main.java.types.program.StudyProgram;

/**
 * @author Martin Avagyan 
 */

public class StudyProgramInterpreterMS extends StudyProgramInterpreter{

	public StudyProgramInterpreterMS(String finleName) {
		super(finleName);
	}
	
	@Override 
	protected void initialize(){
		degree = dept.getMsFields();
	}
	
}
