package main.java.filehandler.outputhandler;

import java.util.ArrayList;



import main.java.types.program.StudyProgram;

/**
 * @author Martin Avagyan 
 */

/**
 * The class for writing study program for bachelors degree into *.txt
 * */
public class StudyProgramWriterBS extends StudyProgramWriter{

	
	public StudyProgramWriterBS(StudyProgram program,String path) {
		super(program,path);
	}
		
	/**
	 * Get the BS fields
	 * */
	@Override
	protected void initialize(){
		degree = depart.getBsFields();
	}
}
