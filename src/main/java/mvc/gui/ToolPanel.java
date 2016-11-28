package main.java.mvc.gui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
*@author Martin Avagyan 
* */

public class ToolPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public ToolPanel ()
	{
		super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground( Color.decode("#F5F5F5"));
        setLayout(null);
	}
	
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent (g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//this.setBackground (Color.gray);
	}	
}

