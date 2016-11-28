package main.java.insertion.alternative;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.java.insertion.InsertionLabel;
import main.java.insertion.InsertionTable;
import main.java.insertion.InsertionTableScrollPane;

/**
*@author Martin Avagyan 
* */

public class AlternativeTablePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int MIN_TABLE_SIZE= 200;
	

	private String lblName = "Alternative to a course";	
	private static final String backColor = "#F5F5F5";
	public AlternativeTable	table;
	
	
	
	public AlternativeTablePanel (int width,int rowCount, AlternativeTable table)
	{		
		this.width = width;
		int tableSize = rowCount * AlternativeTable.getRowheight()+22;
		this.height = ((tableSize< MIN_TABLE_SIZE)?MIN_TABLE_SIZE:tableSize);
		this.table = table;
		 
		
        this.setLayout(null);
        this.setBackground(Color.decode("#F5F5F5"));
     	
		InsertionTableScrollPane scrollTable = new InsertionTableScrollPane(this.table,this.width,this.height);
		
	
		
		InsertionLabel nameLabel = new InsertionLabel(this.lblName);
		nameLabel.setBounds(0, 40, width, 20);
	
		add(nameLabel);	 
		add(scrollTable);
	}

	
	
	public AlternativeTable getTable() {
		return table;
	}

	public void setTable(AlternativeTable table) {
		this.table = table;
}
}

