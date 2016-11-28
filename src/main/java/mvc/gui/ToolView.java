package main.java.mvc.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import main.java.insertion.InsertionController;
import main.java.insertion.InsertionFrame;
import main.java.insertion.InsertionModel;
import main.java.mvc.ToolModel;
import main.java.types.Curriculum;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;
import main.java.types.tree.JScrollPaneTree;
import main.java.types.tree.TreeMakerCurriculum;
import main.java.types.tree.TreeMakerResult;
import main.java.types.tree.TreeMakerStudent;

/**
*@author Martin Avagyan 
* */

public class ToolView extends JFrame{	
	
	
	private static final long serialVersionUID = 1L;
	
	public final static int SCALE = 35;
	private final static int WIDTH = SCALE * 40;
	private final static int HEIGHT = SCALE * 24;
	
	
	private final JPanel panel;
	
	private ToolMenu menu =  new ToolMenu();
	
	private ToolButton checkBtn = new ToolButton("Check");
	private ToolButton curriculumPathBtn = new ToolButton("Curriculum folder");
	private ToolButton studentPathBtn = new ToolButton("Student folder");
	private ToolButton saveBtn = new ToolButton("Save");
	
	private ToolBackText curriculumPathlbl = new ToolBackText("Curriculum");
	private ToolBackText studentPathlbl = new ToolBackText("Student programmes");
	private ToolBackText resultlbl = new ToolBackText("Result");
	
	private String studentDirectory;
	private String curriculumDirectory;
	private final ToolJToolBar toolBar = new ToolJToolBar(WIDTH);
	
	
	private static ArrayList<Curriculum> curriculums = new ArrayList<Curriculum>();
	private ArrayList<StudentProgram> studentPrograms = new ArrayList<StudentProgram>();
	private ArrayList<StudentProgram> resultStudentPrograms = new ArrayList<StudentProgram>();
	
	

	private TreeMakerCurriculum curriculumTree = new TreeMakerCurriculum();
	private TreeMakerStudent studentTree = new TreeMakerStudent();
	private TreeMakerResult resultTree = new TreeMakerResult();

	private  List resultList;

	private JScrollPaneTree curriculumScrollPane = new JScrollPaneTree();
	private JScrollPaneTree studentScrollPane = new JScrollPaneTree();
	private JScrollPaneTree resultScrollPane = new JScrollPaneTree();
	

	private static ImageIcon successIcon;
	
	/** Constructs a new instance of the program. */
	public ToolView ()
	{		
		
		this.setBackground(Color.decode("#F5F5F5"));		
		setLogo();
		
		this.panel = new ToolPanel();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setTitle("SPC");
        this.setFocusableWindowState( true );
        
        Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimen.width/2-this.getSize().width/2, dimen.height/2-this.getSize().height/2);
       
        
        initField(); 
        
        initText();
        initBox();
        initBtn();
        
        panel.add(toolBar);   
        setJMenuBar(menu);
        getContentPane().add(panel); 
        this.setVisible(true);
        //inputDialog();
	}
	
	private void setLogo(){
		Image logo;		
		successIcon = new ImageIcon(getClass().getResource("/main/resources/img/successIco.png"));
		URL input =  getClass().getResource("/main/resources/img/logo8.png");
		if(input!=null){
			try {			
				 logo = ImageIO.read(input);
				 this.setIconImage(logo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initField(){
		//TODO intinialize all sizes here

		curriculumPathlbl.setBounds(2*SCALE, SCALE, 10*SCALE, SCALE);        
        studentPathlbl.setBounds(14*SCALE, SCALE, 9*SCALE, SCALE);        
        resultlbl.setBounds(25*SCALE, SCALE, 13*SCALE, SCALE);
		

		curriculumPathBtn.setBounds(2*SCALE, 20*SCALE, 10*SCALE, SCALE*9/10);
        studentPathBtn.setBounds(14*SCALE, 20*SCALE, 9*SCALE, SCALE*9/10);
        checkBtn.setBounds(25*SCALE, 20*SCALE, 7*SCALE, SCALE*9/10);
        saveBtn.setBounds(32*SCALE, 20*SCALE, 6*SCALE, SCALE*9/10);
        
        curriculumScrollPane.setBounds(new Rectangle(2*SCALE, 2*SCALE, 10*SCALE, 18*SCALE));  
    	studentScrollPane.setBounds(new Rectangle(14*SCALE, 2*SCALE, 9*SCALE, 18*SCALE));
    	resultScrollPane.setBounds(new Rectangle(25*SCALE, 2*SCALE, 13*SCALE, 18*SCALE));
	}
	
	private void initText(){        
        panel.add(curriculumPathlbl);
        panel.add(resultlbl);
        panel.add(studentPathlbl);
	}
	
	private void initBtn(){		
              
        panel.add(checkBtn); 
        panel.add(saveBtn); 
        panel.add(curriculumPathBtn);
        panel.add(studentPathBtn);
	}
	
	public void initBox(){
        
		
		initialBoxStyle();
		
		panel.add(curriculumScrollPane);
		panel.add(studentScrollPane);
		panel.add(resultScrollPane);

	}
	
	private void initialBoxStyle(){
		curriculumScrollPane.getViewport().add(new ToolBackTextInitial("No " +ToolModel.degree+ " curriculums"));
		
		studentScrollPane.getViewport().add(new ToolBackTextInitial("No " +ToolModel.degree+ " students"));
		
		resultScrollPane.getViewport().add(new ToolBackTextInitial("No results"));
	}
	
	public void updateResults(){
		
		resultTree.setPrograms(resultStudentPrograms);
		resultScrollPane.getViewport().removeAll();
		resultScrollPane.getViewport().add(resultTree.genTree());		
		
		panel.revalidate();
		validate();
	    repaint();
	}
	
	
	public void updateStudents(){
		
		studentTree.setPrograms(studentPrograms);
		studentScrollPane.getViewport().removeAll();
		studentScrollPane.getViewport().add(studentTree.genTree());
		
		
		panel.revalidate();
		validate();
	    repaint();
	}
	
	public void updateCurriculums(){
				
		curriculumTree.setPrograms(curriculums);
		curriculumScrollPane.getViewport().removeAll();
		curriculumScrollPane.getViewport().add(curriculumTree.genTree());
		
				
		panel.revalidate();
		validate();
	    repaint();
	}
	
	
	public void addProgramInsertListener(ActionListener addProgListener){
		this.toolBar.getBtnAddProg().addActionListener(addProgListener);
	}

	public void editProgramInsertListener(ActionListener editProgListener){
		this.toolBar.getBtnEditProg().addActionListener(editProgListener);
	}
	
	public void addChangeDegreeListener(ActionListener changeDegreeListener){
		this.toolBar.getBtnChangeDegree().addActionListener(changeDegreeListener);
	}
	
	public void addCheckListener(ActionListener listenForCheckButton){
		checkBtn.addActionListener(listenForCheckButton);
		this.toolBar.getBtnCheck().addActionListener(listenForCheckButton);
	}

	public void addSaveResultsListener(ActionListener listenForSaveButton){
		saveBtn.addActionListener(listenForSaveButton);
		this.toolBar.getBtnSaveResults().addActionListener(listenForSaveButton);
	}
	public void addSaveProgramsListener(ActionListener listenForSaveButton){
		this.toolBar.getBtnSavePrograms().addActionListener(listenForSaveButton);
	}
	
	public void addCurriculumChooseListener(ActionListener listenForCurric){
		curriculumPathBtn.addActionListener(listenForCurric);
	}
	
	public void addStudentChooseListener(ActionListener listenForCurric){
		studentPathBtn.addActionListener(listenForCurric);
	}
	
	
	public void setStudentDirectory(String path){
		this.studentDirectory = path;		
	}	
	
	public String getStudentDirectory(){
		return this.studentDirectory;
	}
	
	public void setCurriculumDirectory(String path){
		this.curriculumDirectory = path;		
	}	
	
	public String getCurriculumDirectory(){
		return this.curriculumDirectory;
	}
	
	public ArrayList<StudentProgram> getStudentProgram() {
		return studentPrograms;
	}

	public void setStudentProgram(ArrayList<StudentProgram> studentProgram) {
		this.studentPrograms.clear();
		this.studentPrograms = studentProgram;
	}
	

	public static ArrayList<Curriculum> getCurriculums() {
		return curriculums;
	}

	public void setCurriculums(ArrayList<Curriculum> curriculums) {
		ToolView.curriculums = curriculums;
	}
	
	public JScrollPane getCurriculumScrollPane() {
		return curriculumScrollPane;
	}

	public void setCurriculumScrollPane(JScrollPaneTree curriculumScrollPane) {
		this.curriculumScrollPane = curriculumScrollPane;
	}

	public JScrollPane getStudentScrollPane() {
		return studentScrollPane;
	}

	public void setStudentScrollPane(JScrollPaneTree studentScrollPane) {
		this.studentScrollPane = studentScrollPane;
	}
	

	public TreeMakerCurriculum getCurriculumTree() {
		return curriculumTree;
	}

	public void setCurriculumTree(TreeMakerCurriculum curriculumTree) {
		this.curriculumTree = curriculumTree;
	}

	public TreeMakerStudent getStudentTree() {
		return studentTree;
	}

	public void setStudentTree(TreeMakerStudent studentTree) {
		this.studentTree = studentTree;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		panel.remove(this.resultList);
		this.resultList = resultList;
	}
	
	public ArrayList<StudentProgram> getResultStudentPrograms() {
		return resultStudentPrograms;
	}

	public void setResultStudentPrograms(
			ArrayList<StudentProgram> resultStudentPrograms) {
		this.resultStudentPrograms.clear();
		this.resultStudentPrograms = resultStudentPrograms;
	}

	public TreeMakerResult getResultTree() {
		return resultTree;
	}

	public void setResultTree(TreeMakerResult resultTree) {
		this.resultTree = resultTree;
	}	
	
	public static void displayWarningMessage(String errorMessage){
		JOptionPane.showMessageDialog(null,errorMessage , "Message", JOptionPane.INFORMATION_MESSAGE); 	
	}
	

	public void displayErrorMessage(String errorMessage){
		ImageIcon icon = new ImageIcon(getClass().getResource("/main/resources/img/errorIco.png"));
		JOptionPane.showMessageDialog(null,errorMessage , "Error", JOptionPane.ERROR_MESSAGE,icon); 	
	}
	
	public void displaySuccessMessage(String successMessage){
		ImageIcon icon = successIcon;
		JOptionPane.showMessageDialog(null,successMessage , "Success", JOptionPane.NO_OPTION, icon); 		
	}
	
	public static void displaySuccessMessageTime(String successMessage){	

		ImageIcon icon = successIcon;
		JOptionPane pane = new JOptionPane();
		pane.setMessage(successMessage);
		pane.setIcon(icon);
		
		final JDialog dialog = pane.createDialog("Success");
		dialog.setIconImage(icon.getImage());
		
		
		Timer timer = new Timer(3000, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        dialog.setVisible(false);
		        dialog.dispose();
		    }
		});
		timer.setRepeats(false);
		timer.start();
		dialog.setVisible(true);		
	}
	
	public void insertionDialog(){				 
		InsertionFrame newInsertView = new InsertionFrame();
		InsertionModel newInsertModel = new InsertionModel();
		new InsertionController(newInsertView,newInsertModel);
	}
	
	public void editDialog(StudyProgram editProg){			
		System.out.println("Edit program called");
		InsertionFrame newInsertView = new InsertionFrame();
		InsertionModel newInsertModel = new InsertionModel();
		InsertionController newController = new InsertionController(newInsertView,newInsertModel,editProg);
	}
	
	
	
	public void resetAll(){
		curriculums.clear();
		studentPrograms.clear();
		resultStudentPrograms.clear();
		
		updateResults();
		updateStudents();
		updateCurriculums();
		
		initialBoxStyle();
	}
}

