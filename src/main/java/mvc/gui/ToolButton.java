package main.java.mvc.gui;

import java.awt.Color;
import java.awt.Font;



import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * @author Martin Avagyan 
 */

public class ToolButton extends JButton{

	private  JButton thisBtn = this;
	private String colorBtn = "#607D8B";
	
	public ToolButton (String text){
		super(text);

		this.setBorder(BorderFactory.createMatteBorder(2, 10, 2, 10, Color.decode(colorBtn)));
		//this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		setStyle();
		setInteraction();
	}
	
	
	
	private void setStyle(){
		this.setBorderPainted(true);
        this.setFocusPainted(true);
        this.setContentAreaFilled(true);
        this.setBackground(Color.decode(colorBtn));
        this.setForeground(Color.decode("#FAFAFA"));
        this.setRolloverEnabled(true);
        this.setFont(new Font("Arial", Font.BOLD, 13));
	}
	
	private void setInteraction(){
		thisBtn.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	thisBtn.setBackground(Color.decode("#546E7A"));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	thisBtn.setBackground(Color.decode(colorBtn));
		    }
		});
	}
}
