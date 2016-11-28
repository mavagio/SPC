package main.java.insertion.alternative;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

import main.java.types.Course;

/**
 * @author Martin Avagyan 
 */

public class AlternativeModel {
	
	//For initializtion
	private HashMap<Character,Course> courseLabelMap;
	
	private String alternatives;
	char c;
	int rowCount;
	
	public AlternativeModel(String alternatives){
		courseLabelMap =  new HashMap<Character,Course>();
		this.alternatives = alternatives;
		this.c = 'A';
		rowCount = 2;
	}
	
	//Converts the table into the string with the expression
	public String tableToAlternativeString(String expr, DefaultTableModel model){
		HashMap<String,Course> programSection = new HashMap<String,Course>();

		int nRow = model.getRowCount();
		
		for (int i = 0; i < nRow; i++) {
			if(model.getValueAt(i, 0)!=null && model.getValueAt(i, 1)!=null && model.getValueAt(i, 2)!=null && !model.getValueAt(i, 3).equals("") && model.getValueAt(i, 3)!=null){
				String label = model.getValueAt(i, 0).toString();
				String code = model.getValueAt(i, 1).toString();
				String name = model.getValueAt(i, 2).toString();
				double credits = Double.parseDouble(model.getValueAt(i, 3).toString());
				programSection.put(label, new Course(code, name, credits));
			}			
		}
		
		
		//Place tab after each character
		expr = expr.replaceAll("\\p{Upper}(?!$)", "\\$$0\\$");
		//Replace space with tabs
		expr = expr.replaceAll("\\s+","\t");	
		
		//Replace all characters with courses
		for (Map.Entry<String, Course> course : programSection.entrySet()) {
			String courseToString = course.getValue().toString();
			String label = course.getKey().trim();
			expr = expr.replaceAll("\\$" + label + "\\$", courseToString);
		}	
		
		expr = expr.replaceAll("\\$", "");
		this.alternatives = expr.trim();
		
		System.out.println("the expression is: " + expr);
		return expr;
	}
	
	
	
	//Converts the string into an expression with labels and saves corresponding label and value in the HashMap
	public void altStringToAltExpression(){
		 System.out.println("Putting inside the map! " + alternatives);
		//String value = "(IABIG-02\tComputer Graphics\t5) or ((AIBJH88\tComputer Vision\t5) and (AIKKH10\tComputer Architecture\t5))";
		Pattern pattern = Pattern.compile("\\([^(][\\S|-]+\\t\\w+( \\w+)*\\t\\d+\\)");		
		//Pattern.compile("\\((\\w|-)+\\t\\w+( \\w+)*\\t\\d+\\)");
		Matcher matcher = pattern.matcher(alternatives);
		StringBuilder builder = new StringBuilder(alternatives.length());
		Map<String, Character> replacements = new HashMap<>();
		int index = 0;
		while (matcher.find(index)) {
			System.out.println("one found");
		    builder.append(alternatives, index, matcher.start());
		    
		    //Generate a new course from the string
		    Course newCourse = stringToCourse(matcher.group());
		    
		    String found = matcher.group();
		    Character replacement = replacements.get(found);
		    if (replacement == null) {
		        replacement = c++;
		        replacements.put(found, replacement);
		        
		        //This is a new course, put the label as a key and course as a value		       
		        courseLabelMap.put(replacement, newCourse);
		    }
		   
		    builder.append(replacement);
		    index = matcher.end();
		}
		if (index < alternatives.length()) {
		    builder.append(alternatives, index, alternatives.length());
		}

		
		//Replace the existing expression
		alternatives = builder.toString();
		alternatives = alternatives.replaceAll("\t", " ").trim();
	}
	
	//Course to table converter
	void courseToTable(AlternativeTable table){		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Character label;
		Course tempCourse;
		
		for (Map.Entry<Character,Course> course : courseLabelMap.entrySet()) {
			tempCourse = course.getValue();
			label = course.getKey();
			model.addRow(new Object[]{label, tempCourse.getCourseCode(), tempCourse.getCourseName(),tempCourse.getCourseCredits()});
			table.setRowHeight(table.getRowCount()-1, 25);
		}		
	}
	
	private Course stringToCourse(String str){
		
		String [] courseArray = ((str.replace("(","")).replace(")","")).split("\t");
		String code = courseArray[0];
		String name = courseArray[1];
		double credit =  Double.parseDouble(courseArray[2].trim());
		
		
		return new Course (code,name,credit);
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}
	
	void initTable(AlternativeTable table){		
		DefaultTableModel model = (DefaultTableModel) table.getModel();		
		//If the set is empty the course does not contain any alternatives, then generate 2 empty rows of alternative
		if(courseLabelMap.entrySet().isEmpty()){
			System.out.println("Entry set is empty");
			for(int i = 0;i<rowCount;i++){
				model.addRow(new Object[]{c++, "", "",""});
				table.setRowHeight(table.getRowCount()-1, 25);
			}
		}else{ //The course has alternatives
			System.out.println("Entry set is not empty");
			courseToTable(table);
		}
	}

	public String getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(String alternatives) {
		this.alternatives = alternatives;
	}

	
	
	
}
