package main.java.insertion.alternative;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class AlternativeTextField extends JTextField{

	private static final long serialVersionUID = 1L;
	private String textCol = "#607D8B";
	
	public AlternativeTextField(int count){
		super(count);		
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setBackground(Color.decode("#fafafa"));
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode(textCol)));
		this.setFont(new Font("Verdana", Font.BOLD, 16));
		this.setForeground(Color.decode("#424242"));		
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode(textCol)));
	}
	

}
