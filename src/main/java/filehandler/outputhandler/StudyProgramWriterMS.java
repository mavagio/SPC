package main.java.filehandler.outputhandler;

import java.util.ArrayList;




import main.java.types.program.StudyProgram;

/**
 * @author Martin Avagyan 
 */

/**
 * The class for writing study program for master degree into *.txt
 * */
public class StudyProgramWriterMS extends StudyProgramWriter{

	public StudyProgramWriterMS(StudyProgram program,String path) {
		super(program,path);
	}
	
	/**
	 * Get the MS fields
	 * */
	@Override
	protected void initialize(){
		degree = depart.getMsFields();
	}
}
