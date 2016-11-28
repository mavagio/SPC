package main.java.filehandler.inputhandler;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.types.Course;
import main.java.types.program.StudentProgram;

/**
 * @author Martin Avagyan 
 */

public class StudentProgramInterpreterMS extends StudentProgramInterpreter{

	public StudentProgramInterpreterMS(String finleName) {
		super(finleName);
	}
	
	@Override 
	protected void initialize(){
		degree = dept.getMsFields();
	}
}
