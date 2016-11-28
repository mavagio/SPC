package main.java.insertion.alternative;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.java.insertion.InsertionTable;

/**
 * @author Martin Avagyan 
 */

public class AlternativeTable extends InsertionTable{
	
	public AlternativeTable(DefaultTableModel model) {
		super(model);
	}

	protected void setSizes(){
		this.getColumnModel().getColumn(0).setMinWidth(50);
		this.getColumnModel().getColumn(0).setMaxWidth(100);
		
		this.getColumnModel().getColumn(1).setMinWidth(100);
		this.getColumnModel().getColumn(1).setMaxWidth(150);
		
		int lastColumnIndex = this.getColumnModel().getColumnCount()-1;
		
		this.getColumnModel().getColumn(lastColumnIndex).setMinWidth(50);
		this.getColumnModel().getColumn(lastColumnIndex).setMaxWidth(100);
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return column == 0 ? false : true;
    }
	
}

