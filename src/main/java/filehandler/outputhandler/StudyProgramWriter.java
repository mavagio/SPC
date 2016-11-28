package main.java.filehandler.outputhandler;

import java.util.ArrayList;





import java.util.HashMap;

import main.java.mvc.ToolModel;
import main.java.types.Course;
import main.java.types.program.StudyProgram;
import main.java.util.Degree;
import main.java.util.Department;

/**
 * @author Martin Avagyan 
 */

/**
 * The class for writing Study Program in *.txt file
 * */

public class StudyProgramWriter extends ProgramWriter{

	protected Department depart;
	protected Degree degree;
	
	public StudyProgramWriter(StudyProgram program) {
		this(program,"C:/Users/Martin/Desktop/testExample/");
	}
	
	public StudyProgramWriter(StudyProgram program,String path) {
		super(program);
		depart = ToolModel.getDepartment();
		filePath = path;
		filePath = filePath + ((StudyProgram)this.program).getProgramName() + ".txt";
		initialize();
		writeProgram();
	}
	
	/**
	 * The method is overriden by inherited classes (BS and MS)
	 * */
	protected void initialize(){}
	
	/**
	 * The main method for writing the program into file
	 * */
		
	public void writeProgram(){
		ArrayList<String> lines = new ArrayList<String>();
			
		lines.add("Study Curriculum");
		lines.add("\n\n");
		lines.add("Year\t " + program.getStudyYear());
		lines.add("\n\n");
		lines.add("Study Programes");
		lines.add("\n\n");
		
		lines.add(degree.getMandatoryField());
		lines.addAll(makeLine(program.getPropadeuic()));
		lines.add("\n");
		
		lines.add(degree.getPostMandatoryField());
		lines.addAll(makeLine(program.getPostPropadeuic()));
		lines.add("\n");
		
		lines.add(degree.getOptionalField());
		lines.addAll(makeLine(program.getMinorElectives()));
		lines.add("\n");
		
		lines.add(degree.getProjectField());
		lines.addAll(makeLine(program.getBachelorProject()));
		lines.add("end");
		
		writeLine(lines);
	}
	
	/**
	 * This method overrides the makeLine of ProgramWriter
	 * by including alternatives for each course
	 * */
	
	
	@Override
	protected ArrayList<String>  makeLine(HashMap<String,Course> subProgram) {
		ArrayList<String> lines =  new ArrayList<String>();
		HashMap<String,String> alternatives;
		String combineLine = "";
		for(String coruseCodes: subProgram.keySet()){
			combineLine += coruseCodes + "\t" ;
			combineLine += subProgram.get(coruseCodes).getCourseName() + "\t";
			combineLine += (int)subProgram.get(coruseCodes).getCourseCredits() + "\t";	
			
			//check for alternatives
			alternatives = ((StudyProgram) program).getAlternativeCourses();
			if(alternatives.containsKey(coruseCodes)){
				combineLine +="or\t" + alternatives.get(coruseCodes);
			}
			lines.add(combineLine);
			combineLine = "";
		}		
		return lines;
	}	

}
