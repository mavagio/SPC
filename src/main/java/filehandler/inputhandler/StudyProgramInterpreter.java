package main.java.filehandler.inputhandler;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.mvc.ToolModel;
import main.java.types.Course;
import main.java.types.program.StudyProgram;
import main.java.util.ErrorMessage;
import main.java.util.Interval;

/**
 * @author Martin Avagyan 
 */

public class StudyProgramInterpreter extends ProgramInterpreter{
	

	private StudyProgram newProgram;
	
	public StudyProgramInterpreter(String finleName) {
		super(finleName);
	}
	

	public StudyProgram interpreteProgram(){
		newProgram = new StudyProgram(fileName);
		
		//Get current department
		dept = ToolModel.getDepartment();
		
		initialize();
		
		newProgram.setStudyYear(interpreteYear( findInterval("Study Curriculum","Study Programes")));
		
		HashMap<String,Course> propadeuic = interpreteSection((findInterval(degree.getMandatoryField(),degree.getPostMandatoryField())));
		if(propadeuic != null){
			newProgram.setPropadeuic(propadeuic);
		}else{
			return null;
		}
		
		/*HashMap<String,Course> postpropa = interpreteSection((findInterval(degree.getPostMandatoryField(),degree.getOptionalField())));
		if(postpropa != null){
			newProgram.setPostPropadeuic(postpropa);
		}else{
			return null;
		}*/
		
		HashMap<String,Course> optional = interpreteSection((findInterval(degree.getOptionalField(),degree.getProjectField())));
		if(optional != null){
			newProgram.setMinorElectives(optional);
		}else{
			return null;
		}
		
		HashMap<String,Course> project = interpreteSection(  (findInterval(degree.getProjectField(),"end")));
		if(project != null){
			newProgram.setBachelorProject(project);
		}else{
			return null;
		}
		
		HashMap<String,Course> extra = interpreteSection( (findInterval(degree.getExtraCoursesField(),"Courses")));
		if(extra != null){
			newProgram.setExtraCourses(extra);
		}else{
			return null;
		}		
		
		
		return newProgram;
	}
	
	protected void initialize(){	
		//Get the degree from specified department
		degree = dept.getBsFields();
	}
	
	protected ArrayList<String> lineInterpreter(String line){
		
		ArrayList<String> retrunInterpreated = new ArrayList<String>();		
		
		line  = line.replace("*", " ");
		
		String[] parts =  line.split("\t");
		for(String x: parts){
			if(x.trim().length()>=1 && x !=null && !(x.trim().indexOf(":") != -1 && x.trim().length() == 1)){
				retrunInterpreated.add(x.trim());
			}
		}
		return retrunInterpreated;
	}
	
	
	protected HashMap<String,Course> interpreteSection(ArrayList<String> section){
		ArrayList<String> interpretedLine = new ArrayList<String>();
		try
		{			
			String alternativeId = "\tor\t";
			String alternative;					
			HashMap<String,Course> returnHash = new HashMap<String,Course>();
			for(String line : section){			

				interpretedLine = lineInterpreter(line);
				Course newCourse = new Course(interpretedLine.get(0),interpretedLine.get(1),
												  interpreteCredits(interpretedLine.get(2)));
				//Search for alternative
				if (line.toLowerCase().contains(alternativeId.toLowerCase())) {
					//Put everything after the first or in alternative String 
					alternative = line.substring(line.indexOf(alternativeId) + alternativeId.length(),line.length());
					
					//Assign the string to the hashmap with key: course code, and value: alternative string
					newProgram.alternativeToCourse(	newCourse.getCourseCode(), alternative);
				}				
				returnHash.put(interpretedLine.get(0), newCourse);
			}
			return returnHash;
		}catch(Exception e){	
			ErrorMessage.getInstance().setMessage(newProgram.getStudyYear()+"\nError line: "+interpretedLine.toString());
			return null;
		}		
	}	
}
