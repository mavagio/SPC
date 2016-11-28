package main.java.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.types.Course;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;
import main.java.util.ErrorMessage;

import org.mvel2.MVEL;

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

public class CheckTool {
	static ArrayList<StudyProgram> programmes;
	static ArrayList<StudentProgram> studentProgrames;
	static ArrayList<StudentProgram> finalStudentProgrames;
	private boolean success = true;
	private String currentYear = "";
	

	public CheckTool(ArrayList<StudyProgram> programmes, ArrayList<StudentProgram> studentProgrames){
		setProgrammes(programmes);
		setStudentProgrames(studentProgrames);
		finalStudentProgrames = new ArrayList<StudentProgram>();
	}
	
	/**
	 * Check for all students with all programs
	 * */
	public void checkAll(){
		StudentProgram bestMatchStudProg = new StudentProgram();
		finalStudentProgrames = new ArrayList<StudentProgram>();
		for(StudentProgram studentProg : studentProgrames ){
			//Initialaize running best
			bestMatchStudProg = studentProg.copy();
			for(StudyProgram studyProg : programmes ){
				//retrieve year in case of error present the user
				currentYear = studyProg.getStudyYear();
				//Setting the matched program
				studentProg.setMatchedStudyProgram(studyProg.copy());				
				if(checkProgrammes(studentProg,studyProg)){
					studentProg.setMatched(true);
					studentProg.setMatchedProgramName(studyProg.getProgramName()+" " + studyProg.getStudyYear());
					break;
				}else{
					studentProg.setMatchedProgramName(studyProg.getProgramName()+" " + studyProg.getStudyYear());
				}
				if(bestMatchStudProg.getNotMatchedCount() > studentProg.getNotMatchedCount()){
					bestMatchStudProg =  studentProg.copy();
				}
				//reset the courses to false
				studentProg.setCoursesFalse();
				studentProg.setMatched(false);
			}
			//Check if the running best still has less missmatches
			if(bestMatchStudProg.getNotMatchedCount() < studentProg.getNotMatchedCount()){
				finalStudentProgrames.add(bestMatchStudProg);
			}else{
				finalStudentProgrames.add(studentProg.copy());
			}
		}		
	}
	
	/**
	 * Check for one student with one program
	 * */
	protected boolean checkProgrammes(StudentProgram studentProg, StudyProgram studyProg) {
		boolean propadeuic,postProp,bachProj,minor;
		
		propadeuic = checkHashMaps(studyProg.getPropadeuic(), studentProg.getPropadeuic(), studyProg.getAlternativeCourses(),studentProg.getMatchedStudyProgram().getPropadeuic());
		postProp =   checkHashMaps(studyProg.getPostPropadeuic(), studentProg.getPostPropadeuic(), studyProg.getAlternativeCourses(),studentProg.getMatchedStudyProgram().getPostPropadeuic());
		bachProj =   checkHashMaps(studyProg.getBachelorProject(), studentProg.getBachelorProject(), studyProg.getAlternativeCourses(),studentProg.getMatchedStudyProgram().getBachelorProject());
		
		//check for minor
		minor = checkForMinors(studyProg.getMinorElectives(), studentProg.getMinorElectives(),studentProg.getMatchedStudyProgram().getMinorElectives());
		
		//If all the mandatory fields are matched with the given program
		//The current program is accepted, and we will move on to next student
		return  propadeuic && postProp && bachProj && minor;   
	}
	
	protected boolean checkForMinors(HashMap<String,Course> studyProgMap, HashMap<String,Course> studentProgMap,HashMap<String,Course> studentProgramStudyProgram){
		boolean outcome = true;
		
		for(Map.Entry<String,Course> studentProgCourse: studentProgMap.entrySet()){
			if (studyProgMap.containsKey(studentProgCourse.getKey())){
				studentProgMap.get(studentProgCourse.getKey()).setMatched(1);
				studentProgramStudyProgram.get(studentProgCourse.getKey()).setMatched(1);
			}else{
				outcome = false;
			}
		}
		
		return outcome;
	}
	
	/**
	 * This is the main check, where for each student we check
	 * if the course is contained within the program hashmap
	 * */ 
	protected boolean checkHashMaps(HashMap<String, Course> studyProgMap,
									HashMap<String, Course> studentProgMap,
									HashMap<String, String> alternative,
									HashMap<String, Course> studentProgramStudyProgram) {
		boolean outcome = true;
		for (Map.Entry<String, Course> studyProgCourse : studyProgMap.entrySet()) {
			if (studentProgMap.containsKey(studyProgCourse.getKey())) {
				studentProgMap.get(studyProgCourse.getKey()).setMatched(1);
				studentProgramStudyProgram.get(studyProgCourse.getKey()).setMatched(1);
			} else if (alternative.containsKey(studyProgCourse.getKey())
					&& isAlternativeValid(
							studyProgCourse.getKey(),
							alternative.get(studyProgCourse.getKey()),
							studentProgMap,studentProgramStudyProgram)) {
				studentProgramStudyProgram.get(studyProgCourse.getKey()).setMatched(1);
			}else {
				outcome = false;
			}
		}
		return outcome;
	}
	
	/**
	 * We check if the course has an alternative course
	 * Boolean evaluator
	 * */
	/**
	 * This method takes the string of alternative courses 
	 * and replaces them with a corresponding boolean value.
	 * */
	private boolean isAlternativeValid(String courseCode, String alternatives, HashMap<String,Course> studentProgMap,HashMap<String, Course> studentProgramStudyProgram){
		boolean valid = false;	
		String tfSub = "false";
		
		
		//The regex pattern
		Pattern pattern = Pattern.compile("\\([^(][\\S|-]+\\t\\w+( \\w+)*\\t\\d+\\)");
		Matcher matcher = pattern.matcher(alternatives);
		
		//Build new string with replaced values
		StringBuilder builder = new StringBuilder(alternatives.length());
		int index = 0;
		while (matcher.find(index)) {
		    builder.append(alternatives, index, matcher.start());			
		    
		    //Replace unnecessary characters, split into components    
		    String [] courseArray = ((matcher.group().replace("(","")).replace(")","")).split("\t");
		    
		    //We only need the first element of the array: the course code
		    String alternativeCourseCode = courseArray[0];
		    		    
		    //We check if the student program section contains the alternative course.
		    if(studentProgMap.containsKey(alternativeCourseCode)){
		    	studentProgMap.get(alternativeCourseCode).setMatched(2);
				//studentProgramStudyProgram.get(alternativeCourseCode).setMatched(true);
				//Insert true in the final string for the particular course
				tfSub = "true";
			}else{
				//Insert false in the final string for the particular course
				tfSub = "false";
			}
		    //Add the result to the final string
		    builder.append(tfSub);
		    index = matcher.end();
		}
		if (index < alternatives.length()) {
		    builder.append(alternatives, index, alternatives.length());
		}

		String finalExpression = ((builder.toString()).replace("\tand\t", "\t&&\t")).replace("\tor\t", "\t||\t");

		try{
			valid = (boolean) MVEL.eval(finalExpression);
		}catch(Exception e){
			ErrorMessage.getInstance().setMessage("\nError year: "+currentYear+"\nError line: "+alternatives);
			success = false;
		}
		
		return valid;
	}
	
	public ArrayList<StudyProgram> getProgrammes() {
		return programmes;
	}

	public void setProgrammes(ArrayList<StudyProgram> programmes) {
		this.programmes = programmes;
	}

	public ArrayList<StudentProgram> getFinalStudentProgrames() {
		return finalStudentProgrames;
	}

	public void setStudentProgrames(ArrayList<StudentProgram> studentProgrames) {
		this.studentProgrames = studentProgrames;
	}

	public boolean isSuccessful() {
		return success;
	}
	
}
