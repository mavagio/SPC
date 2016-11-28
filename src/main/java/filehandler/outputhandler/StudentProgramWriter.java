package main.java.filehandler.outputhandler;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.types.Course;
import main.java.types.program.StudentProgram;

/**
 * @author Martin Avagyan 
 */

public class StudentProgramWriter extends ProgramWriter{

	public StudentProgramWriter(StudentProgram program,String path) {
		super(program);
		filePath = path;
		filePath = filePath + ((StudentProgram)this.program).getStudentName() + ".txt";
	}
	
	public void writeProgram(){
		ArrayList<String> lines = new ArrayList<String>();
		
		lines.add("Year: ");
		lines.add(program.getStudyYear());
		lines.add("\n");
		
		lines.add("Name: ");
		lines.add(((StudentProgram)program).getStudentName());
		lines.add("\n");
		
		lines.add("Mandatory  propaedeutic");
		lines.add("\n");
		lines.addAll(makeLine(program.getPropadeuic()));
		lines.add("\n");
		
		lines.add("Mandatory  post-propaedeutic");
		lines.add("\n");
		lines.addAll(makeLine(program.getPostPropadeuic()));
		lines.add("\n");
		
		lines.add("Minor & electives");
		lines.add("\n");
		lines.addAll(makeLine(program.getMinorElectives()));
		lines.add("\n");
		
		lines.add("Minor & electives");
		lines.add("\n");
		lines.addAll(makeLine(program.getBachelorProject()));
		lines.add("\n");
		
		writeLine(lines);
	}
	
	protected ArrayList<String>  makeLine(HashMap<String,Course> subProgram) {
		ArrayList<String> lines =  new ArrayList<String>();
		String combineLine = "";
		for(String coruseCodes: subProgram.keySet()){
			combineLine += coruseCodes + "\t" ;
			combineLine += subProgram.get(coruseCodes).getCourseName() + "\t";
			combineLine += subProgram.get(coruseCodes).getGrade() + "\t";
			combineLine += subProgram.get(coruseCodes).getCourseCredits() + "\t";			
			lines.add(combineLine);
			combineLine = "";
		}
		
		return lines;
	}
}
