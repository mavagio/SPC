package main.java.check;

import java.util.ArrayList;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;

	/**
	 *@author Martin Avagyan 
	 * */
	
	/**
	*The following class is the check engine of the program
	*it allows to input list of @param study programs and list of 
	*@param student programs and check for each student which study program 
	*is compatable
	*
	*
	*The class also fills in information of which course didn't match
	*This will later be used to output a pdf report for student program 
	* */

public class CheckToolMS extends CheckTool{

		public CheckToolMS(ArrayList<StudyProgram> programmes,
				ArrayList<StudentProgram> studentProgrames) {
			super(programmes, studentProgrames);
		}
		
		@Override
		//Check for one student with one program
		protected boolean checkProgrammes(StudentProgram studentProg, StudyProgram studyProg) {
			boolean mandatory,guided,bachProj,freeChoice;
			
			mandatory = checkHashMaps(studyProg.getPropadeuic(), studentProg.getPropadeuic(), studyProg.getAlternativeCourses(),studentProg.getMatchedStudyProgram().getPropadeuic());			
			bachProj =   checkHashMaps(studyProg.getBachelorProject(), studentProg.getBachelorProject(), studyProg.getAlternativeCourses(),studentProg.getMatchedStudyProgram().getBachelorProject());
			
			//check for minor			
			guided =   checkForMinors(studyProg.getPostPropadeuic(), studentProg.getPostPropadeuic(),studentProg.getMatchedStudyProgram().getPostPropadeuic());
			
			//free choice check
			freeChoice = checkForMinors(studyProg.getMinorElectives(), studentProg.getMinorElectives(),studentProg.getMatchedStudyProgram().getMinorElectives());
			
			//If all the mandatory fields are matched with the given program
			//The current program is accepted, and we will move on to next student
			//System.out.println("mandatory: "+mandatory + "bachProj: "+bachProj + "guided: "+guided + "freeChoice: "+freeChoice);
			return  mandatory && guided && bachProj && freeChoice;   
		}

}


