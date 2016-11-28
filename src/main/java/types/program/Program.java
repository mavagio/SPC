package main.java.types.program;


import java.util.HashMap;
import java.util.Map;

import main.java.types.Course;

/**
*@author Martin Avagyan 
* */

public abstract class Program {
	
	protected  HashMap<String,Course> propadeuic;
	protected  HashMap<String,Course> postPropadeuic;
	protected  HashMap<String,Course> minorElectives;	
	protected  HashMap<String,Course> bachelorProject;
	protected  HashMap<String,Course> extraCourses;
	protected  String studyYear;
	
	
	public Program(){
		propadeuic 	    = new HashMap<String,Course>();
		postPropadeuic  = new HashMap<String,Course>();
		minorElectives  = new HashMap<String,Course>();
		bachelorProject = new HashMap<String,Course>();
		extraCourses    = new HashMap<String,Course>();
		studyYear = "";
	}

	public HashMap<String, Course> getPropadeuic() {
		return propadeuic;
	}

	public void setPropadeuic(HashMap<String, Course> propadeuic) {
		this.propadeuic = propadeuic;
	}

	public HashMap<String, Course> getPostPropadeuic() {
		return postPropadeuic;
	}

	public void setPostPropadeuic(HashMap<String, Course> postPropadeuic) {
		this.postPropadeuic = postPropadeuic;
	}

	public HashMap<String, Course> getMinorElectives() {
		return minorElectives;
	}

	public void setMinorElectives(HashMap<String, Course> minorElectives) {
		this.minorElectives = minorElectives;
	}

	public HashMap<String, Course> getBachelorProject() {
		return bachelorProject;
	}

	public void setBachelorProject(HashMap<String, Course> bachelorProject) {
		this.bachelorProject = bachelorProject;
	}
	
	public String getStudyYear() {
		return studyYear;
	}

	public void setStudyYear(String studyYear) {
		this.studyYear = studyYear;
	}

	public HashMap<String, Course> getExtraCourses() {
		return extraCourses;
	}

	public void setExtraCourses(HashMap<String, Course> extraCourses) {
		this.extraCourses = extraCourses;
	}
	protected HashMap<String,Course> copyHash(HashMap<String,Course> map){
		HashMap<String,Course> cMap = new HashMap<String,Course>();		
		for( Map.Entry<String, Course> entry : map.entrySet()){
			cMap.put(new String(entry.getKey()), entry.getValue().copy());
		}				
		return cMap;
	}
	
}
