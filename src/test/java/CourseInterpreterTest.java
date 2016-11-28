package test.java;

import main.java.mvc.ToolModel;
import main.java.types.program.Program;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;

import org.junit.Before;
import org.junit.Test;

public class CourseInterpreterTest {

	StudentProgram student;
	StudyProgram program;
	ToolModel model;
	
	@Before
	public void setUp() throws Exception {
		/*student = new ProgramInterpreter("data/test/student.txt").interpreteStudentProgram();
		program = new ProgramInterpreter("data/test/programme.txt").interpreteStudyProgram();*/

	}
	
	/**
	 * Standard scenario
	 * */	
	@Test
	public void test() {
		printTestStudent(program);
		
	}
	
	private void printTestStudent(Program newProgram){
		System.out.println("-------------SECTION Year---------------");
		System.out.println("key is: "+newProgram.getStudyYear()); 
		System.out.println("-------------SECTION---------------");
		for(String coruseCodes: newProgram.getPropadeuic().keySet()){
			System.out.println("key is: "+newProgram.getPropadeuic().get(coruseCodes));
		}
		System.out.println("-------------SECTION---------------");
		for(String coruseCodes: newProgram.getPostPropadeuic().keySet()){
			System.out.println("key is: "+newProgram.getPostPropadeuic().get(coruseCodes));
		}
		System.out.println("-------------SECTION---------------");
		for(String coruseCodes: newProgram.getMinorElectives().keySet()){
			System.out.println("key is: "+newProgram.getMinorElectives().get(coruseCodes));
		}
		System.out.println("-------------SECTION---------------");
		for(String coruseCodes: newProgram.getBachelorProject().keySet()){
			System.out.println("key is: "+newProgram.getBachelorProject().get(coruseCodes));
		}
	}
}
