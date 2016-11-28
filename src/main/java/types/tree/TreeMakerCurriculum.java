package main.java.types.tree;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import main.java.types.Curriculum;

import main.java.types.program.StudyProgram;

/**
 * @author Martin Avagyan 
 */

public class TreeMakerCurriculum extends TreeMaker{
	ArrayList<Curriculum> curricList;
	
	/*public TreeMakerCurriculum(Rectangle bounds){
		super(bounds);
	}*/
	
	public TreeMakerCurriculum(){
		root = new DefaultMutableTreeNode("All programs");
	}
			
	protected void initTree(){
		for(Curriculum curric : curricList ){
			DefaultMutableTreeNode subNode = addAFile(curric.getName(),root);
			for(StudyProgram program: curric.getProgrames()){
				addAFile(program.getProgramName() + " " + program.getStudyYear(),subNode);
			}
		}
	}
	
	public void setPrograms(ArrayList<Curriculum> curricList){
		root.removeAllChildren();
		this.curricList = curricList;
	}
}
