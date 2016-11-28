package main.java.types.tree;


import java.awt.Rectangle;
import java.util.ArrayList;






import javax.swing.tree.DefaultMutableTreeNode;

import main.java.types.program.StudentProgram;

/**
 * @author Martin Avagyan 
 */

public class TreeMakerStudent extends TreeMaker{
	ArrayList<StudentProgram> programs;
	
	public TreeMakerStudent(){
		root = new DefaultMutableTreeNode("All students");
	}
	
	
	protected void initTree(){
		for(StudentProgram prog : programs ){
			addAFile(prog.getStudentName()+ " (" + prog.getStudentNumber()+")",root);
		}
	}
	
	public void setPrograms(ArrayList<StudentProgram> programs){
		root.removeAllChildren();
		this.programs = programs;
	}
}
