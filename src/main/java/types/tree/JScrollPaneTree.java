package main.java.types.tree;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import main.java.mvc.gui.ToolScrollBar;

/**
 * @author Martin Avagyan 
 */

public class JScrollPaneTree extends JScrollPane{

	private static final long serialVersionUID = 1L;
	private String colorBtn = "#B0BEC5";
	public JScrollPaneTree(){
	    Dimension d =  this.getPreferredSize();	    
	    d.width  = 200;
	    d.height  = 200;     
	    this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode(colorBtn)));
	    this.getViewport().setBackground(Color.decode("#ECEFF1"));
	    this.setPreferredSize(d);	
	    getVerticalScrollBar().setUI(new ToolScrollBar());
	    getHorizontalScrollBar().setUI(new ToolScrollBar());
	}
}