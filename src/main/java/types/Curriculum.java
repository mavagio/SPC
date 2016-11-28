package main.java.types;

import java.util.ArrayList;

import main.java.types.program.StudyProgram;

/**
 * @author Martin Avagyan 
 */

public class Curriculum {
	
	private String name;

	private String year;
	private String degree;
	private ArrayList<StudyProgram> programes;
	
	public Curriculum(){
		this("NA","NA",new  ArrayList<StudyProgram>());
	}
	
	public Curriculum(String year, String degree, ArrayList<StudyProgram> programes){		
		this.year=year;
		this.degree = degree;
		this.programes = programes;		
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public ArrayList<StudyProgram> getProgrames() {
		return programes;
	}

	public void setProgrames(ArrayList<StudyProgram> programes) {
		this.programes = programes;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addProgram(StudyProgram newProg){
		this.programes.add(newProg);
	}
	
}
