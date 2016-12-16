package main.java.filehandler.inputhandler;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.mvc.ToolModel;
import main.java.mvc.gui.ToolView;
import main.java.types.Course;
import main.java.types.program.StudentProgram;
import main.java.util.Degree;
import main.java.util.Department;
import main.java.util.ErrorMessage;
import main.java.util.Interval;

/**
 * @author Martin Avagyan 
 */

public class StudentProgramInterpreter extends ProgramInterpreter{

	public StudentProgramInterpreter(String finleName) {
		super(finleName);
	}
	private StudentProgram newStudentProgram;
	
	public StudentProgram interpreteProgram(){
				
		newStudentProgram = new StudentProgram();
		//Get current department
		dept = ToolModel.getDepartment();
		
		initialize();
		
		//end condition for each section
		String endKeyWord = dept.getEndKey();
		
		//get year interval
		Interval year = dept.getYear();
		
		//get year interval
		Interval name = dept.getStudentName();
		
		//get year interval
		Interval number = dept.getStudentName();
		
		//student selected variant
		Interval variant = dept.getStudentVariant();
		
		newStudentProgram.setStudentNumber(interpreteStudentNumber( findInterval(number.getStart(),number.getEnd())));
		newStudentProgram.setStudentName(interpreteStudentName( findInterval(name.getStart(),name.getEnd())));
		
		//Fill in propadeuic section
		HashMap<String,Course> propadeuic = interpreteSection( structureLines (findInterval(degree.getMandatoryField(),endKeyWord)));
		if(propadeuic != null){
			newStudentProgram.setPropadeuic(propadeuic);
		}else{
			return null;
		}
		
		//Fill in post propadeuic section
		HashMap<String,Course> postpropa = interpreteSection( structureLines (findInterval(degree.getPostMandatoryField(),endKeyWord)));
		if(postpropa != null){
			newStudentProgram.setPostPropadeuic	(postpropa);
		}else{
			return null;
		}
		
		//Fill in optional section
		HashMap<String,Course> optional = interpreteSection( structureLines (findInterval(degree.getOptionalField(),endKeyWord)));
		if(optional != null){
			newStudentProgram.setMinorElectives	(optional);
		}else{
			return null;
		}
		
		//Fill in project section
		HashMap<String,Course> project = interpreteSection( structureLines (findInterval(degree.getProjectField(),endKeyWord)));
		if(project != null){
			newStudentProgram.setBachelorProject	(project);
		}else{
			return null;
		}
		
		//Fill in extra section
		HashMap<String,Course> extra = interpreteSection( structureLines (findInterval(degree.getExtraCoursesField(),keyWordExists("Honours College")?"Honours College":endKeyWord)));
		if(extra != null){
			newStudentProgram.setExtraCourses	(extra);
		}else{
			return null;
		}
		
		newStudentProgram.setStudyYear(interpreteYear( findInterval(year.getStart(),year.getEnd())));		
		newStudentProgram.setStudentNumber(interpreteStudentNumber( findInterval(number.getStart(),number.getEnd())));
		newStudentProgram.setStudentName(interpreteStudentName( findInterval(name.getStart(),name.getEnd())));
		newStudentProgram.setVariant(interpreteStudentVariant( findInterval(variant.getStart(),variant.getEnd())));
		
		newStudentProgram.setRemarks(interpreteRemark());
		return newStudentProgram;
	}
	
	protected void initialize(){	
		//Get the degree from specified department
		degree = dept.getBsFields();
	}
	
	/**
	 * Interpret the student number from supplied arrayList
	 * ArrayList in assumed to have 1 line, containing the student number
	 * */
	
	
	protected String interpreteStudentNumber(ArrayList<String> lines){		
		String firstWord = "";
		String[] parts;
		if(lines.size() == 1){
			firstWord = lines.get(0).trim();
			parts = firstWord.split(" ", 2);
			firstWord = parts[0].replaceAll("[^\\d.]", "");
		}else {
			//ErrorMessage();
		}
		return firstWord;
	}
	
	/**
	 * Interpret the student chosen program
	 * */
	
	protected String interpreteStudentVariant(ArrayList<String> lines){		
		String programName = "";
		if(lines.size() == 1){
			programName = lines.get(0).trim();
			//System.out.println("And it is = " + firstWord);
		}else {
			//ErrorMessage();
		}
		return programName;
	}
	
	/**
	 * Interpret the student name from supplied arrayList
	 * ArrayList in assumed to have 1 line, containing the student name
	 * */
	
	protected String interpreteStudentName(ArrayList<String> lines){		
		String firstWord = "";
		String[] parts;
		if(lines.size() == 1){
			firstWord = lines.get(0).trim();
			parts = firstWord.split(" ", 2);
			firstWord = parts[1];
			//System.out.println("And it is = " + firstWord);
		}else {
			//ErrorMessage();
		}
		return firstWord;
	}
	
	/**
	 * The function makes sure that we also save a grade represented as letters
	 * For example "V" is saved in the form of 10.10, latter interpreted to "V"
	 * */
	protected double interpreteGrade(String grade){	
		return (isFloat (grade)) ? Double.parseDouble(grade): 10.10;
	}
	

	protected HashMap<String,Course> interpreteSection(ArrayList<String> section){
		ArrayList<String> interpretedLine = new ArrayList<String>();
		try{			
			HashMap<String,Course> returnHash = new HashMap<String,Course>();
			for(String line : section){			
				Course newCourse =null;
				interpretedLine = lineInterpreter(line);
				if(interpretedLine.size() == 4){
					newCourse = new Course(interpretedLine.get(0),interpretedLine.get(1),interpreteGrade(interpretedLine.get(2)),
							  interpreteCredits(interpretedLine.get(3)));	
				}else if(interpretedLine.size() == 3){
					newCourse = new Course(interpretedLine.get(0),interpretedLine.get(1),interpreteCredits(interpretedLine.get(2)));
				}
				returnHash.put(interpretedLine.get(0), newCourse);
			}
			return returnHash;
		}catch(Exception e){
			ErrorMessage.getInstance().setMessage(newStudentProgram.getStudentName()+" ("+newStudentProgram.getStudentName()+")"+"\nError line: "+interpretedLine.toString());
			return null;
		}
	}
	
}
