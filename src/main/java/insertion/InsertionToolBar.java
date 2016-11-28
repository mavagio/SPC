package main.java.insertion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import main.java.insertion.alternative.AlternativeController;
import main.java.insertion.alternative.AlternativeFrame;
import main.java.insertion.alternative.AlternativeModel;
import main.java.mvc.gui.ToolButton;
import main.java.mvc.gui.ToolView;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class InsertionToolBar extends JToolBar{

	private static final long serialVersionUID = 1L;
	protected ToolButton addRowBtn = new ToolButton("Add row");
	protected ToolButton removeRowBtn = new ToolButton("Remove row");
	protected ToolButton addAlternativeBtn = new ToolButton("Add alternative");
	protected ToolButton addCurricBtn = new ToolButton("Add curriculum");	
	
	public InsertionToolBar(int width){
		this.setBounds(0, 0, width, 28);
		
		this.setFloatable(false);
		this.setBackground(Color.decode("#FAFAFA"));
		this.setBorderPainted(false);
		
		initLogos();
        addComponents();
	}
	
	private void addComponents() {
		initializeFirst();
	}
	
	public void initializeFirst(){
		this.removeAll();
		this.add(addRowBtn);
        this.add(removeRowBtn);
        this.add(addAlternativeBtn);
	}
	public void initializeSecond(){
		this.removeAll();
        this.add(addCurricBtn);
	}
	
	protected void initLogos(){		
		URL addIcon,removeLine,alternative;
		
		addIcon =  getClass().getResource("/main/resources/img/insert/add.png");
		removeLine =  getClass().getResource("/main/resources/img/insert/remove.png");
		alternative =  getClass().getResource("/main/resources/img/insert/alternative.png");
		
		if(addIcon!=null && removeLine!=null){
			try {						 
				
				addRowBtn.setIcon(new ImageIcon(ImageIO.read(addIcon)));
				removeRowBtn.setIcon(new ImageIcon(ImageIO.read(removeLine)));
				addAlternativeBtn.setIcon(new ImageIcon(ImageIO.read(alternative)));
				addCurricBtn.setIcon(new ImageIcon(ImageIO.read(addIcon)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void setAlternativeActionListernet(ActionListener listerner){
		addAlternativeBtn.addActionListener(listerner);
	}
	
	public void setAddActionListernet(ActionListener listerner){
        addRowBtn.addActionListener(listerner);		
	}
	
	public void setRemoveActionListernet(ActionListener listerner){
		removeRowBtn.addActionListener(listerner);
	}
	public void setCurriculumActionListernet(ActionListener listerner){
		addCurricBtn.addActionListener(listerner);
	}
}
