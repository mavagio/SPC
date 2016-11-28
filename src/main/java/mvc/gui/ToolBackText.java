package main.java.mvc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Martin Avagyan 
 */

public class ToolBackText extends JLabel{
	
	public ToolBackText(String text){
		super(text, SwingConstants.CENTER);
		
		this.setMaximumSize(new Dimension(300, 50));
		this.setForeground(Color.decode("#9E9E9E"));
		this.setFont(new Font("Tahoma", Font.BOLD, 13));
	}
}
