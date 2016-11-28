package main.java.types.tree;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import main.java.types.Curriculum;

/**
 * @author Martin Avagyan 
 */

public class TreeMakerInsertionCurriculum extends TreeMaker{
	ArrayList<Curriculum> curricList;
	
	/*public TreeMakerCurriculum(Rectangle bounds){
		super(bounds);
	}*/
	
	public TreeMakerInsertionCurriculum(){
		root = new DefaultMutableTreeNode("All curriculums");
	}
			
	protected void initTree(){
		for(Curriculum curric : curricList ){
			addAFile(curric.getName(),root);
		}
	}
	
	public void setPrograms(ArrayList<Curriculum> curricList){
		root.removeAllChildren();
		this.curricList = curricList;
	}
}

