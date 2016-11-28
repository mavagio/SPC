package main.java.insertion.alternative;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;

import main.java.insertion.InsertionTablePanel;

/**
 * @author Martin Avagyan 
 */

public class AlternativeFrame extends JFrame{

	//Customization
	private static final long serialVersionUID = 1L;
	private final static int WIDTH = 650;
	private final static int HEIGHT = 500;
	
	//Table panels
	private AlternativeTablePanel inputPanelProp;	
	
	//Alternative table
	private AlternativeTable table;
	
	//Array with tables
	private ArrayList<JPanel> tablePanels;
	
	//Main panel for the insert box
	private JPanel mainPanel;
	
	//Split the main panel
	private JSplitPane sp;
	
	private AlternativeToolBar toolBar;
	
	//footer panel
	private AlternativeFooterPanel footer;
		
	public AlternativeFrame(){
		
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Add alternative to course (beta)");
		this.setBackground(Color.decode("#F5F5F5"));	
		this.setLocationRelativeTo(null);
				
		setLogo();
		this.setResizable(false);
		initFrame();
		
		add(mainPanel);
		this.setVisible(true);
		
		//System.out.println(inputPanel.getTable().getModel().getValueAt(1, 1));	
	}

	private void initFrame(){
		
		//Init the table
		String columnNames[] = { "Label","Code", "Name", "Credits" };    
		DefaultTableModel model = new DefaultTableModel(columnNames,0); 
		this.table = new AlternativeTable(model);
		
		//Tables for insertion
		//TODO add a course code at the end
		this.inputPanelProp = new AlternativeTablePanel(WIDTH,2,table);
		
		//Initialize the array of tables
		this.tablePanels = new ArrayList<JPanel>();
		this.tablePanels.add(this.inputPanelProp);
		
	
		
		
		//Navigation footer
		this.footer = new AlternativeFooterPanel(AlternativeFrame.WIDTH);

		//Main panel of Insertion box		
		this.mainPanel = new JPanel();		
		this.mainPanel.setLayout(new BorderLayout());

	    this.toolBar = new AlternativeToolBar(WIDTH);
		
		//Split the insertion box 1 to 9
        sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);        
        sp.setResizeWeight(0.65);
        sp.setEnabled(false);
        sp.setDividerSize(0);

        sp.add(tablePanels.get(0));
        sp.add(footer);
        
        this.mainPanel.add(toolBar, BorderLayout.CENTER);
        this.mainPanel.add(sp, BorderLayout.CENTER);
	}
	
	
	
	public boolean submit(){
		return displayQuestion("Sure, you want to submit?");
	}	
		
	private void setLogo(){
		Image logo;		
		URL input =  getClass().getResource("/main/resources/img/insert/add.png");
		if(input!=null){
			try {			
				 logo = ImageIO.read(input);
				 this.setIconImage(logo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getWidth() {
		return WIDTH;
	}
	public int getHeight() {
		return HEIGHT;
	}

	public void displayWarningMessage(String errorMessage){
		JOptionPane.showMessageDialog(null,errorMessage , "Message", JOptionPane.INFORMATION_MESSAGE); 	
	}
	
	public boolean displayQuestion(String question){
		int n = JOptionPane.showConfirmDialog(null,question,"Question",JOptionPane.YES_NO_OPTION);
		return (n == 0)? true:false;
	}
	
	public void exitFrame(){
		this.dispose();
	}

	public void addCancelListener(ActionListener listener){
		this.footer.getCancelBtn().addActionListener(listener);
	}
	public void addNextListener(ActionListener listener){
		this.footer.getNextBtn().addActionListener(listener);
	}
	
	
	
	//HeaderListeners
	public void addAddRowListener(ActionListener listener){
		this.toolBar.setAddActionListernet(listener);
	}
	
	public void addRemoveRowListener(ActionListener listener){
		this.toolBar.setRemoveActionListernet(listener);
	}

	public void setTable(AlternativeTable table) {
		this.table = table;
	}
	public AlternativeTable getTable(){
		return table;
	}

	public AlternativeFooterPanel getFooter() {
		return footer;
	}
	
	
	
}
