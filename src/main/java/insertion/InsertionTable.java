package main.java.insertion;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * @author Martin Avagyan 
 */

public class InsertionTable extends JTable{
	
	private static final long serialVersionUID = 1L;
	private String textCol = "#607D8B";
	private  final static int rowHeight = 25;
	
	public InsertionTable(DefaultTableModel model){			
		super(model);
		
		setBackground(Color.decode("#FAFAFA"));
		this.setFont( new Font("monospaced", Font.BOLD, 13) );
		
		setSizes();
		this.getTableHeader().setOpaque(false);

		this.getTableHeader().setBackground(Color.decode("#78909C"));
		this.getTableHeader().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode(textCol)));
		this.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
		this.getTableHeader().setForeground(Color.decode("#ffffff"));
		this.getTableHeader().setReorderingAllowed(false);
		
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode(textCol)));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		for(int i =  0; i<this.getColumnCount();i++){
			this.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
		}
		for(int i =  0; i<this.getRowCount();i++){
			this.setRowHeight(i, rowHeight);
		}
	}
	
	protected void setSizes(){
		this.getColumnModel().getColumn(0).setMinWidth(100);
		this.getColumnModel().getColumn(0).setMaxWidth(150);
		
		int lastColumnIndex = this.getColumnModel().getColumnCount()-1;
		
		this.getColumnModel().getColumn(lastColumnIndex).setMinWidth(50);
		this.getColumnModel().getColumn(lastColumnIndex).setMaxWidth(100);
	}

	public static int getRowheight() {
		return rowHeight;
	}
		
	
}

