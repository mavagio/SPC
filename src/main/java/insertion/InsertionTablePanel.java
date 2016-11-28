package main.java.insertion;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.java.insertion.alternative.AlternativeTable;
import main.java.mvc.gui.ToolButton;

/**
*@author Martin Avagyan 
* */

public class InsertionTablePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int MIN_TABLE_SIZE = 200;
	
	private String lblName;	
	private static final String backColor = "#F5F5F5";
	public InsertionTable	table;
	
	JPanel inputPanel = new JPanel();
	
	public InsertionTablePanel (int width, int rowCount,String lblName)
	{		
		this.width = width;
		int tableSize = rowCount * AlternativeTable.getRowheight()+22;
		this.height = (tableSize< MIN_TABLE_SIZE)?MIN_TABLE_SIZE:tableSize;
		this.lblName = lblName;
		       
        this.setLayout(null);
        this.setBackground(Color.decode("#F5F5F5"));
       
                		     		
        String columnNames[] = { "Code", "Name", "Credits" }; 
     	table = new InsertionTable(new DefaultTableModel(columnNames,rowCount));
     	
		InsertionTableScrollPane scrollTable = new InsertionTableScrollPane(table,this.width,this.height);
		
		
		
		InsertionLabel newLabel = new InsertionLabel(this.lblName);
		newLabel.setBounds(0, 40, width, 20);
			
		add(newLabel);	 
		add(scrollTable);
	}

	
	public InsertionTable getTable() {
		return table;
	}

	public void setTable(InsertionTable table) {
		this.table = table;
    }
	
}

