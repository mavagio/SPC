package test.java;


import main.java.filehandler.outputhandler.ProgramWriter;
import main.java.mvc.ToolModel;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;

import org.junit.Before;
import org.junit.Test;

public class CheckToolTest {
	StudentProgram student;
	StudyProgram program;
	ToolModel model;
	
	
	@Before
	public void setUp() throws Exception {
		/*student = new ProgramInterpreter("test/student.txt").interpreteStudentProgram();
		program = new ProgramInterpreter("test/programme.txt").interpreteStudyProgram();*/
	}
	
	@Test
	public void testCheckAll() {
		ProgramWriter courseWriter = new ProgramWriter( "writer/example2.txt",program);		
		//courseWriter.writeProgram();
		
		//ProgramWriter courseWriter2 = new ProgramWriter( "writer/example.txt",student);
		//courseWriter2.writeProgram();
	}

}
