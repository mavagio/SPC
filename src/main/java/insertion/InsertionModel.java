package main.java.insertion;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableModel;

import main.java.mvc.ToolModel;
import main.java.types.Course;
import main.java.types.program.StudyProgram;
import main.java.util.Degree;

/**
 * @author Martin Avagyan 
 */

public class InsertionModel {
	
	
	private HashMap<String, DefaultTableModel> tableModels = new HashMap<String, DefaultTableModel> ();
	//Alternative courses
	protected HashMap<String,String> alternativeCourses;
	
	//Name of the program
	private String name = "No name";
	//Year of the program
	private String year = "NA";
	
	public InsertionModel(){
		alternativeCourses = new HashMap<String,String>();
	}

	// Method for building a StudyProgram
	public StudyProgram generateStudyProgram() {
		Degree currentDegree = ToolModel.getCurrentDegreeFields();
		StudyProgram prog = new StudyProgram(name);
		prog.setStudyYear(year);
				
		//Insert propaedeutic
		prog.setPropadeuic(convertTabletoHash(tableModels.get(currentDegree.getMandatoryField())));
		//Insert post-propaedeutic
		prog.setPostPropadeuic(convertTabletoHash(tableModels.get(currentDegree.getPostMandatoryField())));
		//Insert minors/optional
		prog.setMinorElectives(convertTabletoHash(tableModels.get(currentDegree.getOptionalField())));
		//Insert Bs/Ms project
		prog.setBachelorProject(convertTabletoHash(tableModels.get(currentDegree.getProjectField())));
	
		//Set the alternative courses
		prog.setAlternativeCourses(alternativeCourses);
		return prog;
	}
	//Method for converting a JTable to HashTable
	public HashMap<String,Course> convertTabletoHash(DefaultTableModel model){
		HashMap<String,Course> programSection = new HashMap<String,Course>();
		
		int nRow = model.getRowCount();
		
		for (int i = 0; i < nRow; i++) {
			if(model.getValueAt(i, 0)!=null && model.getValueAt(i, 1)!=null && model.getValueAt(i, 2)!=null){
				String code = model.getValueAt(i, 0).toString();
				String name = model.getValueAt(i, 1).toString();
				double credits = Double.parseDouble(model.getValueAt(i, 2).toString());
				programSection.put(code, new Course(code, name, credits));
			}			
		}
		
		return programSection;
	}
	
	// Method for building the tables
	public void generateStudyProgramTables(StudyProgram editProg) {
		Degree currentDegree = ToolModel.getCurrentDegreeFields();
				
		//Insert propaedeutic
		convertHashtoTable(tableModels.get(currentDegree.getMandatoryField()),editProg.getPropadeuic());
		//Insert post-propaedeutic
		convertHashtoTable(tableModels.get(currentDegree.getPostMandatoryField()),editProg.getPostPropadeuic());
		//Insert minors/optional
		convertHashtoTable(tableModels.get(currentDegree.getOptionalField()),editProg.getMinorElectives());
		//Insert Bs/Ms project
		convertHashtoTable(tableModels.get(currentDegree.getProjectField()),editProg.getBachelorProject());
	
		//Set the alternative courses
		alternativeCourses = editProg.getAlternativeCourses();
		//return prog;
	}
	
	//Method for converting a HashTable to JTable
	public void convertHashtoTable(DefaultTableModel model,HashMap<String,Course> courseList){		
		int nRow = model.getRowCount();
		int index  = 0;
		
		for (Entry<String, Course> entry : courseList.entrySet()) {
			if(index>=nRow-1){
				model.setRowCount(index+1);
			}
			Course course = entry.getValue();
			model.setValueAt(course.getCourseCode(), index, 0);
			model.setValueAt(course.getCourseName(), index, 1);
			model.setValueAt(course.getCourseCredits(), index, 2);			
			index++;
		}		
	}
	
	

	public void setTableModels(HashMap<String, DefaultTableModel> models) {
		tableModels = models;		
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAlternativeCourses(HashMap<String, String> alternativeCourses) {
		this.alternativeCourses = alternativeCourses;
	}
	
	public void setYear(String year){
		this.year = year;
	}

	public HashMap<String, String> getAlternativeCourses() {
		return alternativeCourses;
	}
	
	
	
}
