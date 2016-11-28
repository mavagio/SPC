package main.java.mvc.gui;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import main.java.mvc.ToolModel;

/**
 * @author Martin Avagyan 
 */

public class ToolJToolBar extends JToolBar{
	
	//TODO modify the following line
	String[] bookTitles = new String[] {"Computing science", "Mathematics",
            "Physics", "Chemistry"};

	JComboBox<String> progList = new JComboBox<>(bookTitles);
	
    private ToolButton btnAddProg = new ToolButton("Add Programme");    
    private ToolButton btnRemoveProg = new ToolButton("Remove Programme");   
    private ToolButton btnEditProg = new ToolButton("Edit Programme");    
    private ToolButton btnRemoveStud = new ToolButton("Remove Student"); 
    private ToolButton btnCheck = new ToolButton("Check");
    private ToolButton btnSaveResult = new ToolButton("Save results"); 
    private ToolButton btnSaveProg = new ToolButton("Save programs");  
    private ToolButton btnChangeDegree = new ToolButton("Change Degree "+ToolModel.degree); 

	public ToolJToolBar (int with){
		super();
		this.setBounds(0, 0, with, 28);
		this.setFloatable( false);
		
		this.setBackground(Color.decode("#FAFAFA"));
		this.setBorderPainted(false);

	
		
		initLogos();
		add(btnAddProg);
		//add(btnRemoveProg);
		add(btnEditProg);
		//add(btnRemoveStud);
		add(btnCheck);
		add(btnSaveResult);
		add(btnSaveProg);
		
		
		add(Box.createHorizontalGlue());
		
		//TODO modify this
		progList.setBackground(Color.decode("#fafafa"));
		
		progList.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		//progList.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#E0E0E0")));
		
		progList.setUI(ColorArrowUI.createUI(progList));
		progList.setSelectedItem(0);
		progList.setSize(50, progList.getPreferredSize().height);
		progList.setForeground(Color.decode("#607D8B"));
		
		add(progList);
		add(btnChangeDegree);
		
	}
	
	private void initLogos(){
		
		URL inputAddProg,inputAddStud,inputRemoveProg,inputRemoveStud,inputCheck,inputSave,inputChange;
		
		inputAddProg =  getClass().getResource("/main/resources/img/toolbaricons/white/addProg.png");
		inputAddStud =  getClass().getResource("/main/resources/img/toolbaricons/white/addStud.png");
		inputRemoveProg =  getClass().getResource("/main/resources/img/toolbaricons/white/removeProg.png");
		inputRemoveStud =  getClass().getResource("/main/resources/img/toolbaricons/white/removeStud.png");
		inputCheck =  getClass().getResource("/main/resources/img/toolbaricons/white/check.png");
		inputSave = getClass().getResource("/main/resources/img/toolbaricons/white/save.png");
		inputChange = getClass().getResource("/main/resources/img/toolbaricons/white/change.png");
		if(inputAddProg!=null && inputAddStud!=null && inputRemoveProg!=null && inputRemoveStud!=null && inputCheck!=null){
			try {			 
				
				 btnAddProg.setIcon(new ImageIcon(ImageIO.read(inputAddProg)));
				 btnEditProg.setIcon(new ImageIcon(ImageIO.read(inputAddStud)));
				 btnRemoveProg.setIcon(new ImageIcon(ImageIO.read(inputRemoveProg)));
				 btnRemoveStud.setIcon(new ImageIcon(ImageIO.read(inputRemoveStud)));
				 btnCheck.setIcon(new ImageIcon(ImageIO.read(inputCheck)));
				 btnSaveResult.setIcon(new ImageIcon(ImageIO.read(inputSave)));
				 btnSaveProg.setIcon(new ImageIcon(ImageIO.read(inputSave)));
				 btnChangeDegree.setIcon(new ImageIcon(ImageIO.read(inputChange)));
				 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ToolButton getBtnAddProg() {
		return btnAddProg;
	}
	public ToolButton getBtnRemoveProg() {
		return btnRemoveProg;
	}
	public void setBtnRemoveProg(ToolButton btnRemoveProg) {
		this.btnRemoveProg = btnRemoveProg;
	}
	public ToolButton getBtnEditProg() {
		return btnEditProg;
	}
	public ToolButton getBtnRemoveStud() {
		return btnRemoveStud;
	}
	public ToolButton getBtnCheck() {
		return btnCheck;
	}
	public ToolButton getBtnSaveResults() {
		return btnSaveResult;
	}
	public ToolButton getBtnSavePrograms() {
		return btnSaveProg;
	}	
	public JButton getBtnChangeDegree() {
		return btnChangeDegree;
	}
}
