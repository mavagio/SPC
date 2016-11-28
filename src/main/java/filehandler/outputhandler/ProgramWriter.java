package main.java.filehandler.outputhandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import main.java.types.Course;
import main.java.types.program.Program;
import main.java.util.Degree;
import main.java.util.Department;

/**
 * @author Martin Avagyan 
 */

/**
 * This class writes the study or student program into txt file
 * */
public class ProgramWriter {	
	
	protected String filePath = "C:/Users/Martin/Desktop/testexample/";
	protected Program program;
	protected boolean successfullyCreated = true;
	
	public ProgramWriter(String filePath, Program program){
		this.filePath = filePath;
		this.program = program;
	}
	public ProgramWriter(Program program){
		this.program = program;		
	}

	

	protected ArrayList<String>  makeLine(HashMap<String,Course> subProgram) {
		ArrayList<String> lines =  new ArrayList<String>();
		String combineLine = "";
		for(String coruseCodes: subProgram.keySet()){
			combineLine += coruseCodes + "\t" ;
			combineLine += subProgram.get(coruseCodes).getCourseName() + "\t";
			combineLine += subProgram.get(coruseCodes).getCourseCredits() + "\t";	
			lines.add(combineLine);
			combineLine = "";
		}		
		return lines;
	}
	
	protected void writeLine(ArrayList<String> lines){
		try {
			File file = new File(filePath);
	
			System.out.println("start " + filePath +"   end ");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);		
			for(String line: lines){
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			successfullyCreated = false;
		}
	}	
	
	public boolean isSuccessfullyCreated() {
		return successfullyCreated;
	}
}

