package main.java.insertion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.java.mvc.ToolModel;
import main.java.util.Degree;

/**
 * @author Martin Avagyan 
 */

public class InsertionFrame extends JFrame{

	//Customization
	private static final long serialVersionUID = 1L;
	private final static int WIDTH = 600;
	private final static int HEIGHT = 600;	
	Degree currentDegree;
	
	//Current table
	private static InsertionTable currentTable;
	
	//Table panels
	private InsertionTablePanel inputPanelProp;
	private InsertionTablePanel inputPanelPostProp;
	private InsertionTablePanel inputPanel1Minor;
	private InsertionTablePanel inputPanel1Proj;
	
	private InsertionInfoPanel infoPanel;
	
	private InsertionToolBar toolBar;
	
	//Array with tables
	private ArrayList<JPanel> tablePanels;
	
	//Main panel for the insert box
	private JPanel mainPanel;
	
	//Split the main panel
	private JSplitPane sp;
	
	//footer panel
	private InsertionFooterPanel footer;
	
	//Iterator for tables
	private int cursor;
	
	//Year components
	private JTextField yearStart;
	private JTextField yearEnd;
	
	//if it is in the last step
	private boolean isLastStep = false;
	
	public InsertionFrame(){
		
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Add program (beta)");
		this.setBackground(Color.decode("#F5F5F5"));	
		this.setLocationRelativeTo(null);
		
		
		setLogo();
		this.setResizable(false);
		initFrame();
		
		add(mainPanel);
		this.setVisible(true);
		
	}

	private void initFrame(){
		currentDegree = ToolModel.getCurrentDegreeFields();
		//Tables for insertion
		this.inputPanelProp = new InsertionTablePanel(WIDTH,12,currentDegree.getMandatoryField());
		this.inputPanelPostProp = new InsertionTablePanel(WIDTH,15,currentDegree.getPostMandatoryField());
		this.inputPanel1Minor = new InsertionTablePanel(WIDTH,6,currentDegree.getOptionalField());
		this.inputPanel1Proj = new InsertionTablePanel(WIDTH,1,currentDegree.getProjectField());
		this.infoPanel = new InsertionInfoPanel(WIDTH,HEIGHT);
		
		//Initialize the array of tables
		this.tablePanels = new ArrayList<JPanel>();
		this.tablePanels.add(this.inputPanelProp);
		this.tablePanels.add(this.inputPanelPostProp);
		this.tablePanels.add(this.inputPanel1Minor);
		this.tablePanels.add(this.inputPanel1Proj);
		this.tablePanels.add(this.infoPanel);
		
		
		//Navigation footer
		this.footer = new InsertionFooterPanel(WIDTH);

		//Main panel of Insertion box		
		this.mainPanel = new JPanel();		
		this.mainPanel.setLayout(new BorderLayout());

		//Split the insertion box 1 to 9
        sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);        
        sp.setResizeWeight(0.9);
        sp.setEnabled(false);
        sp.setDividerSize(0);

        cursor = 0;
        currentTable = ((InsertionTablePanel)tablePanels.get(cursor)).getTable();
        sp.add(tablePanels.get(0));
       
        setActiveBtn();
        sp.add(footer);
        
        toolBar = new InsertionToolBar(WIDTH);
        
        this.mainPanel.add(toolBar, BorderLayout.CENTER);         
        this.mainPanel.add(sp, BorderLayout.CENTER);
	}
		
	public boolean nextTable(){
		if(cursor ==tablePanels.size()-1 ){
			//Submit button is clicked			
			return displayQuestion("Sure, you want to submit?");
		}
		cursor++;	
		setActiveBtn();
		sp.removeAll();			
		sp.add(tablePanels.get(cursor), 0);
		sp.add(footer, 1);
		
		if(cursor  == tablePanels.size()-1){			
			this.toolBar.initializeSecond();	
		}else{
			currentTable = ((InsertionTablePanel)tablePanels.get(cursor)).getTable();
		}
		revalidate();
		repaint();
		return false;
	}
	public void backTable(){
		if(cursor == 0){
			return;
		}
		cursor--;
		setActiveBtn();
		sp.removeAll();
		sp.add(tablePanels.get(cursor), 0);
		sp.add(footer, 1);
		if(cursor  != tablePanels.size()-1){			
			this.toolBar.initializeFirst();	
			currentTable = ((InsertionTablePanel)tablePanels.get(cursor)).getTable();
		}
		revalidate();
		repaint();

	}
	
	private void setActiveBtn(){		
		if(tablePanels.size()-1 == cursor){
			footer.getNextBtn().setText("Submit");
		}else{
			footer.getNextBtn().setText("Next");
		}
		
		if(0 == cursor){
			footer.getBackBtn().setEnabled(false);
		}else{
			footer.getBackBtn().setEnabled(true);
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
	
	

	public HashMap<String,DefaultTableModel> generateTableModels() {
		HashMap<String,DefaultTableModel> tableModels = new   HashMap<String,DefaultTableModel>();
		tableModels.put(currentDegree.getMandatoryField(), (DefaultTableModel) inputPanelProp.getTable().getModel());
		tableModels.put(currentDegree.getPostMandatoryField(), (DefaultTableModel) inputPanelPostProp.getTable().getModel());
		tableModels.put(currentDegree.getOptionalField(), (DefaultTableModel) inputPanel1Minor.getTable().getModel());
		tableModels.put(currentDegree.getProjectField(), (DefaultTableModel) inputPanel1Proj.getTable().getModel());
		return tableModels;
	}
	
	public String insertNewCurriculumInfo(){		
		yearStart = new JTextField(5);
		yearEnd = new JTextField(5);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Year: "));
		myPanel.add(yearStart);
		myPanel.add(new JLabel("-"));
		myPanel.add(yearEnd);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"New curriculum details", JOptionPane.OK_CANCEL_OPTION);
		
		if (result != JOptionPane.OK_OPTION) {
			return "0";
		} 		
		//Create new curriculum 
		String newCurric  = "curriculum "+yearStart.getText() + "-" + yearEnd.getText() +" " +ToolModel.getDegree() + " Cs";
		
		return newCurric;		
	}
	
	public String getYear() {
		return yearStart.getText() + yearEnd.getText();
	}
	
	public void exitFrame(){
		this.dispose();
	}

	//Footer Listeners 
	public void addCancelListener(ActionListener listener){
		this.footer.getCancelBtn().addActionListener(listener);
	}
	public void addNextListener(ActionListener listener){
		this.footer.getNextBtn().addActionListener(listener);
	}
	public void addBackListener(ActionListener listener){
		this.footer.getBackBtn().addActionListener(listener);
	}
	
	//Header Listeners 
	public void addAddRowListener(ActionListener listener){
		this.toolBar.setAddActionListernet(listener);
	}
	
	public void addRemoveRowListener(ActionListener listener){
		this.toolBar.setRemoveActionListernet(listener);
	}
	
	public void addAlternativeListener(ActionListener listener){
		this.toolBar.setAlternativeActionListernet(listener);
	}
	
	public void addCurriculumListener(ActionListener listener){
		this.toolBar.setCurriculumActionListernet(listener);
	}
	

	public InsertionInfoPanel getInfoPanel() {		
		return infoPanel;
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

	public InsertionTable getCurrentTable() {
		return currentTable;
	}
	
	
	
}
