package main.java.insertion.alternative;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import main.java.insertion.InsertionTable;
import main.java.mvc.gui.ToolButton;
import main.java.mvc.gui.ToolView;
import main.java.util.Messages;

/**
 * @author Martin Avagyan 
 */

public class AlternativeToolBar extends JToolBar{

	private static final long serialVersionUID = 1L;
	protected ToolButton addRowBtn = new ToolButton("Add row");
	protected ToolButton removeRowBtn = new ToolButton("Remove row");
	
	public AlternativeToolBar(int width){
		this.setBounds(0, 0, width, 28);
		
		this.setFloatable(false);
		this.setBackground(Color.decode("#FAFAFA"));
		this.setBorderPainted(false);
		
		initLogos();
        addComponents();
	}
	
	private void addComponents() {
		this.add(addRowBtn);
        this.add(removeRowBtn);
	}
	
	
	protected void initLogos(){		
		URL addLine,removeLine;
		
		addLine =  getClass().getResource("/main/resources/img/insert/add.png");
		removeLine =  getClass().getResource("/main/resources/img/insert/remove.png");
		if(addLine!=null && removeLine!=null){
			try {						 
				
				addRowBtn.setIcon(new ImageIcon(ImageIO.read(addLine)));
				removeRowBtn.setIcon(new ImageIcon(ImageIO.read(removeLine)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public  void setAddActionListernet(ActionListener listerner){
		 addRowBtn.addActionListener(listerner);		
	}
	
	public void setRemoveActionListernet(ActionListener listerner){
		removeRowBtn.addActionListener(listerner);
	}
	
}
