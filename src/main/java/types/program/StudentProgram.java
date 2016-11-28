package main.java.types.program;

import java.util.HashMap;
import java.util.Map;

import main.java.types.Course;

/**
*@author Martin Avagyan 
* */

public class StudentProgram extends Program{
	
	/** Personal Details of Student */	
	private String studentNumber;
	private String studentName;
	private String matchedProgramName = "None";
	private String remarks = ""; 
	private String variant = ""; 	
	private StudyProgram matchedStudyProgram = new StudyProgram();
	
	/** Variable for storing if the student matched with any program */
	private boolean matched;
	
	public StudentProgram(){
		this("No number","No name");
	}
	
	public StudentProgram(String studentNumber, String studentName){
		super();		
		this.studentName = studentName;
		this.studentNumber = studentNumber;
		matched = false;
	}
	
	/**
	 * Setters & getters 
	 * */
	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentName() {
		return studentName;
	}
	
	public String getStudentSurnameName(){
		String surname = studentName.substring(studentName.lastIndexOf(" ")+1);
		String name = studentName.replaceAll(" \\S*$", "");
		return surname + " " + name;
	}
	
	

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public void setPersonalDetails(String studentNumber, String studentName) {
		this.studentNumber = studentNumber;
		this.studentName = studentName;
	}
	
	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public String getMatchedProgramName() {
		return matchedProgramName;
	}

	public void setMatchedProgramName(String matchedProgramName) {
		this.matchedProgramName = matchedProgramName;
	}
	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public void setCoursesFalse(){
		setHashCourseFalse(propadeuic);
		setHashCourseFalse(postPropadeuic);
		setHashCourseFalse(minorElectives);
		setHashCourseFalse(bachelorProject);
	}
	
	private void setHashCourseFalse(HashMap<String,Course> studentProg){
		for (Map.Entry<String, Course> studyProgCourse : studentProg.entrySet()) {
			studentProg.get(studyProgCourse.getKey()).setMatched(0);
		}
	}

	public StudyProgram getMatchedStudyProgram() {
		return matchedStudyProgram;
	}

	public void setMatchedStudyProgram(StudyProgram matchedStudyProgram) {
		this.matchedStudyProgram = matchedStudyProgram;
	}
	
	public HashMap<String,Course> filterAccpeted(HashMap<String,Course> prog){
		HashMap<String,Course> filtredProg = new HashMap<String,Course>();
		
		for(String coruseCodes: prog.keySet()){	
			if(prog.get(coruseCodes).isMatched() == 0){
				filtredProg.put(coruseCodes, prog.get(coruseCodes));
			}
		}
		
		return filtredProg;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	} 
	
	/**
	 * The following function counts the number of not matched courses.
	 * */
	public int getNotMatchedCount(){
		int count = 0;
		
		count+=counter(this.getMinorElectives());
		count+=counter(this.getPropadeuic());
		count+=counter(this.getBachelorProject());
		count+=counter(this.getPostPropadeuic());
		return count;
	}
	
	private int counter(HashMap<String,Course> subProgram){
		int count = 0;
		for(String coruseCodes: subProgram.keySet()){	
			if(subProgram.get(coruseCodes).isMatched()==0){
				count++;
			}
		}
		return count;
	}
	
	public StudentProgram copy(){
		StudentProgram sProg = new StudentProgram(new String(studentNumber),new String(studentName));		
		sProg.setBachelorProject(copyHash(bachelorProject));
		sProg.setMinorElectives (copyHash(minorElectives));
		sProg.setPostPropadeuic (copyHash(postPropadeuic));
		sProg.setPropadeuic     (copyHash(propadeuic));	
		sProg.setExtraCourses   (copyHash(extraCourses));  
		
		sProg.setMatchedProgramName(new String(matchedProgramName));
		sProg.setRemarks(new String(remarks));
		sProg.setVariant(new String(variant));
		sProg.setMatchedStudyProgram(matchedStudyProgram);
		
		return sProg;
	}	
	
}
