package main.java.insertion;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.java.insertion.alternative.AlternativeTextField;
import main.java.mvc.gui.ToolBackTextInitial;
import main.java.mvc.gui.ToolView;
import main.java.types.Curriculum;
import main.java.types.tree.JScrollPaneTree;
import main.java.types.tree.TreeMakerInsertionCurriculum;

/**
 * @author Martin Avagyan 
 */

public class InsertionInfoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private  int width;
	private int scrollWidth;
	static ArrayList<Curriculum> curricList;
	
	private InsertionLabel selectCuric = new InsertionLabel("Select curriculum");
	private InsertionLabel insertName = new InsertionLabel("Insert name");
	AlternativeTextField nameField = new AlternativeTextField(20);
	
	private TreeMakerInsertionCurriculum curricTree = new TreeMakerInsertionCurriculum();
	private JScrollPaneTree treeScroll = new JScrollPaneTree();
	
	public InsertionInfoPanel(int width, int height){
		updateCurriculums();
		this.width = width;
		this.scrollWidth = (width/10)*9;
		this.setBackground(Color.decode("#EEEEEE"));
		this.setLayout(null);
		
		
		setCurriculums();
		
		setLocation();
		
		add(insertName);
		
		add(treeScroll);	
		add(selectCuric);
		add(nameField);
		
	}

	public void updateCurriculums() {
		curricList = ToolView.getCurriculums();
		setCurriculums();
	}
	private void setCurriculums(){
		if(ToolView.getCurriculums().isEmpty()){
			treeScroll.getViewport().add(new ToolBackTextInitial("No curriculums"));		
		}else{
			curricTree.setPrograms(ToolView.getCurriculums());
			treeScroll.getViewport().add(curricTree.genTree());					
		}
	}
	
	private void setLocation(){
		treeScroll.setBounds((width-scrollWidth-20)/2, 75, scrollWidth , 150);
		selectCuric.setBounds(0, 40, width, 20);	
		insertName.setBounds(0, 250, width, 20);
		nameField.setBounds(width/4,275,width/2,30);
	}

	public TreeMakerInsertionCurriculum getCurricTree() {
		return curricTree;
	}

	public String getNameText() {
		return nameField.getText();
	}	
	
}
