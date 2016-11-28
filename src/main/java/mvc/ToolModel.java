package main.java.mvc;

import java.io.File;
import java.util.ArrayList;

import main.java.filehandler.inputhandler.StudentProgramInterpreter;
import main.java.filehandler.inputhandler.StudentProgramInterpreterMS;
import main.java.filehandler.inputhandler.StudyProgramInterpreter;
import main.java.filehandler.inputhandler.StudyProgramInterpreterMS;
import main.java.filehandler.outputhandler.ReportPDF;
import main.java.filehandler.outputhandler.ReportPDFMS;
import main.java.filehandler.outputhandler.StudyProgramPDF;
import main.java.filehandler.outputhandler.StudyProgramPDFMS;
import main.java.filehandler.outputhandler.StudyProgramWriterBS;
import main.java.filehandler.outputhandler.StudyProgramWriterMS;
import main.java.types.Curriculum;
import main.java.types.program.StudentProgram;
import main.java.types.program.StudyProgram;
import main.java.util.Degree;
import main.java.util.Department;
import main.java.util.Departments;
import main.java.util.ErrorMessage;
import main.java.util.Messages;

/**
*@author Martin Avagyan 
* */

/**
 * The model of the tool.
 * This class contains all the interaction between the input and output handlers
 * */

public class ToolModel {
	
	public static String degree = "BS";
	
	public static Departments depart = new Departments();
	
	//The identifying keyword for all students
	public static final String studentKey = "remark";
	
	//The identifying keyword for all students
	public static final String curricKey = "Study Curriculum";
	
	//The identifying keywords for BS program	
	private static String errorReading = Messages.getNA();
	
	private ArrayList<StudyProgram> programmes;
	private static ArrayList<StudentProgram> studentProgrames;	
	private static ArrayList<Curriculum> curriculums; 
	
	static String studentFolderPath = "";
	private static String programFolderPath = "data/programes";
	public  static String curriculumFolderPath = "";
	
	private String saveFolderPath = "";

	public ToolModel(){		
		programmes = new ArrayList<StudyProgram>();
		studentProgrames = new ArrayList<StudentProgram>();		
		curriculums = new ArrayList<Curriculum>();
	}
	
	public ToolModel(String studentFolderPath, String programFolderPath){
		this();
		ToolModel.studentFolderPath = studentFolderPath;
		ToolModel.programFolderPath = programFolderPath;

	}
	public String modelCalculate(int x){
		return x+" =  " +  programFolderPath;
	}
	
	public void readAllStudents(){		
		File folder = new File(studentFolderPath);
		File[] listOfFiles = folder.listFiles();
		File file;
		studentProgrames = new ArrayList<StudentProgram>();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			file = listOfFiles[i];
			if (file.isFile() && file.getName().endsWith(".txt")) {
				StudentProgram tempProg;
				if(ToolModel.degree.equals("BS")){
					 tempProg = new StudentProgramInterpreter(studentFolderPath.trim()+"\\"+ file.getName().trim()).interpreteProgram();
				}else{
					 tempProg = new StudentProgramInterpreterMS(studentFolderPath.trim()+"\\"+ file.getName().trim()).interpreteProgram();	
				}
				if(tempProg == null){
					studentProgrames = null;
					return;
				}
				studentProgrames.add(tempProg);
			}
		}		
	}
	
	public boolean saveInterpretedStuds(ArrayList<StudentProgram> studentProgrames) {
		for (StudentProgram stud : studentProgrames) {
			if(ToolModel.degree.equals("BS")){
				ReportPDF newStudPdf = new ReportPDF(stud,saveFolderPath);
				if (!newStudPdf.isSuccessfullyCreated()){
					return false;
				}
			}else{
				ReportPDFMS newStudPdf = new ReportPDFMS(stud,saveFolderPath);
				if (!newStudPdf.isSuccessfullyCreated()){
					return false;
				}
			}			
		}
		return true;
	}
	
	public boolean saveInterpretedProgs(ArrayList<StudyProgram> studyProgrames) {
		for (StudyProgram prog : studyProgrames) {
			if(ToolModel.degree.equals("BS")){
				StudyProgramPDF newProgPdf = new StudyProgramPDF(prog,saveFolderPath);
				if (!newProgPdf.isSuccessfullyCreated()){
					return false;
				}
			}else{
				StudyProgramPDFMS newProgPdf = new StudyProgramPDFMS(prog,saveFolderPath);
				if (!newProgPdf.isSuccessfullyCreated()){
					return false;
				}
			}			
		}
		return true;
	}
	
	public void readAllCurriculums(){
		File folder = new File(curriculumFolderPath);
		File[] listOfFiles = folder.listFiles();
		curriculums = new ArrayList<Curriculum>();
		Curriculum tempCurriculum;
		
		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isDirectory()) {
				tempCurriculum = new Curriculum();				
				tempCurriculum.setName(file.getName());
				programFolderPath = curriculumFolderPath+File.separator +file.getName();
				readAllProgrammes();	
				if(programmes == null){
					curriculums = null;
					return;
				}
				tempCurriculum.setProgrames(programmes);
				curriculums.add(tempCurriculum);
				programmes = new ArrayList<StudyProgram>();
			}
		}
	}
	
	public void readAllProgrammes() {
		File folder = new File(programFolderPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			File file = listOfFiles[i];
			if (file.isFile() && file.getName().endsWith(".txt")) {
				StudyProgram tempProg;
				if (ToolModel.degree.equals("BS")) {
					tempProg = new StudyProgramInterpreter(programFolderPath + File.separator + file.getName().trim()).interpreteProgram();
									
				} else {
					tempProg = new StudyProgramInterpreterMS(programFolderPath + File.separator	+ file.getName().trim()).interpreteProgram();					
				}
				if(tempProg == null){
					ErrorMessage.getInstance().setMessage("\nFile name: "+file.getName().trim()+ " " + ErrorMessage.getInstance().getMessage());
					programmes = null;
					return;
				}
				programmes.add(tempProg);	
			}
		}
	}
		
	
	/**
	 * Save an ArrayList of study programs into txt file
	 * */
	public void saveStudyPrograms(ArrayList<StudyProgram> studyProg){
		for (StudyProgram prog : studyProg) {
			saveStudyProgram(prog,saveFolderPath);
		}		
	}
	/**
	 * Save a study programs into txt file
	 * */
	public static boolean saveStudyProgram(StudyProgram prog,String path) {
		if(ToolModel.degree.equals("BS")){
			new StudyProgramWriterBS(prog,path);				
		}else{
			new StudyProgramWriterMS(prog,path);
		}		
		return true;
	}


	public static String getStudentFolderPath() {
		return studentFolderPath;
	}

	public void setStudentFolderPath(String studentFolderPath) {
		ToolModel.studentFolderPath = studentFolderPath;
	}

	public String getProgramFolderPath() {
		return programFolderPath;
	}

	public void setProgramFolderPath(String programFolderPath) {
		ToolModel.programFolderPath = programFolderPath;
	}
	

	public static String getCurriculumFolderPath() {
		return curriculumFolderPath;
	}

	public void setCurriculumFolderPath(String curriculumFolderPath) {
		ToolModel.curriculumFolderPath = curriculumFolderPath;
	}
	
	public ArrayList<StudyProgram> getProgrammes() {
		return programmes;
	}

	public void setProgrammes(ArrayList<StudyProgram> programmes) {
		this.programmes = programmes;
	}

	public ArrayList<StudentProgram> getStudentProgrames() {
		return studentProgrames;
	}

	public void setStudentProgrames(ArrayList<StudentProgram> studentProgrames) {
		ToolModel.studentProgrames = studentProgrames;
	}
	
	public ArrayList<Curriculum> getCurriculums() {
		return curriculums;
	}

	public void setCurriculums(ArrayList<Curriculum> curriculums) {
		ToolModel.curriculums = curriculums;
	}
	
	public String getSaveFolderPath() {
		return saveFolderPath;
	}

	public void setSaveFolderPath(String saveFolderPath) {
		this.saveFolderPath = saveFolderPath;
	}

	public static String getDegree() {
		return degree;
	}

	public static void setDegree(String degree) {
		ToolModel.degree = degree;
	}
	
	public void resetAll(){
		programmes = new ArrayList<StudyProgram>();
		studentProgrames = new ArrayList<StudentProgram>();		
		curriculums = new ArrayList<Curriculum>();
	}
	

	public static String getErrorReading() {
		return errorReading;
	}

	public static void setErrorReading(String errorReading) {
		ToolModel.errorReading = errorReading;
	}

	//TODO change hardcode, at the moment Computing Science is hardcoded 
	public static Department getDepartment() {
		return Departments.getDepartment("Computing Science");
	}
	
	public static Degree getCurrentDegreeFields(){
		Department depart = getDepartment();		
	    return (degree.equals("BS")?depart.getBsFields():depart.getMsFields());
	}
	
}
	
