package main.java.types.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.types.Course;

/**
*@author Martin Avagyan 
* */

public class StudyProgram extends Program{
	
	protected HashMap<String,String> alternativeCourses;
	protected String programName;
	
	public StudyProgram(){
		this("Regular");
	}
	
	
	
	public StudyProgram(String programName){
		super();
		this.programName = programName;
		alternativeCourses = new HashMap<String,String>();
	}

	public void alternativeToCourse(String courseCode, String alternativeExp){
		alternativeCourses.put(courseCode, alternativeExp);
	}

	
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	public StudyProgram copy(){
		StudyProgram cProg = new StudyProgram(new String(programName));
		
		cProg.setBachelorProject(copyHash(bachelorProject));
		cProg.setMinorElectives (copyHash(minorElectives));
		cProg.setPostPropadeuic (copyHash(postPropadeuic));
		cProg.setPropadeuic     (copyHash(propadeuic));		
		cProg.setExtraCourses   (copyHash(extraCourses));   
		cProg.setStudyYear(new String(studyYear));	
		
		return cProg;
	}
	

	public HashMap<String, String> getAlternativeCourses() {
		return alternativeCourses;
	}



	public void setAlternativeCourses(HashMap<String, String> alternativeCourses) {
		this.alternativeCourses = alternativeCourses;
	}
	
	
}
