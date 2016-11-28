package main.java.types.tree;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import main.java.types.Curriculum;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;

/**
 * @author Martin Avagyan 
 */

public class TreeMakerResult extends TreeMaker{
	ArrayList<StudentProgram> programs = new ArrayList<StudentProgram>();
	
	public TreeMakerResult(){
		root = new DefaultMutableTreeNode("All students");
	}
	
	
	protected void initTree(){
		for(StudentProgram prog : programs ){
			addAFile(prog.getStudentName()+ " (" + prog.getStudentNumber()+")  " + statusUpdate(prog),root);
		}
	}
	
	private String statusUpdate(StudentProgram prog){
		if(prog.isMatched()){
			return "Accepted: " + prog.getMatchedProgramName();
		}
		return "Rejected";
	}
	
	public void setPrograms(ArrayList<StudentProgram> programs){
		root.removeAllChildren();
		this.programs = programs;
	}
}
