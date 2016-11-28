package main.java.insertion;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import main.java.mvc.gui.ToolScrollBar;

/**
 * @author Martin Avagyan 
 */

public class InsertionTableScrollPane extends JScrollPane{
	private int scrollWidth;
	private int scrollHeight;
	
	public InsertionTableScrollPane(Component comp,int width, int height){
		super(comp);
		this.scrollWidth = (width/10)*9;
		this.scrollHeight = height;
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#B0BEC5")));
		this.setBounds((width-scrollWidth-20)/2, 75, scrollWidth , this.scrollHeight);
		this.getViewport().setBackground(Color.decode("#ECEFF1"));
		this.getHorizontalScrollBar().setUI(new ToolScrollBar());
		this.getVerticalScrollBar().setUI(new ToolScrollBar());	
	}	
}
