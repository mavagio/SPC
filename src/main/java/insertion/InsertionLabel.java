package main.java.insertion;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Martin Avagyan 
 */

public class InsertionLabel extends JLabel{

	private static final long serialVersionUID = 1L;

	private static final String backColor = "#F5F5F5";
	
	
	public InsertionLabel(String text){
		super(text,SwingConstants.CENTER);
		this.setForeground(Color.decode("#9E9E9E"));	
		this.setFont(new Font("Tahoma", Font.BOLD, 13));
	}	
}