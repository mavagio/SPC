package main.java.mvc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Martin Avagyan 
 */

public class ToolBackTextInitial extends JLabel{
	private String textCol = "#B0BEC5";
	
	public ToolBackTextInitial(String text){
		
		super(text, SwingConstants.CENTER);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.decode(textCol)));
		this.setMaximumSize(new Dimension(300, 50));
		this.setForeground(Color.decode(textCol));
		this.setFont(new Font("Tahoma", Font.BOLD, 20));
	}
}
