package main.java.types.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;


import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author Martin Avagyan 
 */

public abstract class TreeMaker {

	
	protected JCheckBoxTree theTree;	
	protected JScrollPane scrollBox;	
	protected DefaultMutableTreeNode root;
	protected TreePath[] paths;
	
	public TreeMaker(){
		
	}
	
	public JCheckBoxTree genTree(){
		initTree();
		theTree = new JCheckBoxTree(root);	
		paths = theTree.getCheckedPaths();
		setTreeProperties();
		
		this.theTree.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
	        public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {	        	
	            paths = theTree.getCheckedPaths();
	        }           
	    }); 
		
		return theTree;
	}	

	protected abstract void initTree();
	
	protected DefaultMutableTreeNode addAFile(String fileName, DefaultMutableTreeNode parentFolder){		
		DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(fileName);
		parentFolder.add(newFile);		
		return newFile;
	}
	
	/**
	 * Tree style and sizes
	 * @return 
	 * */
	protected void setTreeProperties(){
		theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);        
        theTree.setVisibleRowCount(8);
        
        
        theTree.setShowsRootHandles(true);
        theTree.expandRow(0);        
        theTree.setRootVisible(true);
        
        theTree.setBackground(Color.decode("#FAFAFA"));
	}

	public TreePath[] getPaths() {
		return paths;
	}

	public void setPaths(TreePath[] paths) {
		this.paths = paths;
	}
	
	
}
