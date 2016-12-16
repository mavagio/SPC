package main.java.filehandler.inputhandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import main.java.types.Course;
import main.java.types.program.Program;
import main.java.types.program.StudentProgram;
import main.java.util.Degree;
import main.java.util.Department;

/**
*@author Martin Avagyan 
* */

public abstract class ProgramInterpreter {

	protected Degree degree;
	protected Department dept;
	
	
	protected ArrayList<String> courseText;
	protected String fileName;
	private static final Pattern DOUBLE_PATTERN = Pattern.compile(
		    "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
		    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
		    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
		    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

	public static boolean isFloat(String s)
	{
	    return DOUBLE_PATTERN.matcher(s).matches();
	}	
	
	protected void initialize(){}
	
	public ProgramInterpreter(String finleName){
		this.fileName = finleName.substring(finleName.lastIndexOf(File.separator) + 1);
		this.fileName = this.fileName.substring(0,this.fileName.indexOf(".txt"));
		courseText = new ProgramReader(finleName).read();		
	}	
	
	public abstract Program interpreteProgram();	
	
	/**
	 * Interpret the Year student is enrolled
	 * ArrayList in assumed to have 1 line, containing the student year
	 * */
	protected String interpreteYear(ArrayList<String> lines){		
		String lastWord = "";
		String[] parts;
		
		if(lines.size() >= 1){
			lastWord = lines.get(0);
			parts = lastWord.split(" ");
			parts = parts[parts.length - 1].split("\t");
			lastWord = parts[parts.length - 1];
		}else {
			//TODO add error message here connected to the UI
			//ErrorMessage();
		}
		return lastWord;
	}
	
	/**
	 * Finalizes the interpretations for a section (propadeutic or post-progadeutic...),
	 * combines them into a HashMap, each entery has a key the course code, and the course itself.
	 * */
	protected HashMap<String,Course> interpreteSection(ArrayList<String> section){
		ArrayList<String> interpretedLine = new ArrayList<String>();
		HashMap<String,Course> returnHash = new HashMap<String,Course>();
		for(String line : section){			
			interpretedLine = lineInterpreter(line);
			Course newCourse = new Course(interpretedLine.get(0),interpretedLine.get(1),
											  interpreteCredits(interpretedLine.get(2)));
			
			returnHash.put(interpretedLine.get(0), newCourse);
		}
		return returnHash;
	}
	
	/**
	 * Each line has credits, this function interpretes those credits
	 * The credit is how many ECT's is a certain course
	 * */
	protected double interpreteCredits(String credits){
		credits = credits.trim().toLowerCase();
		credits = credits.replaceAll(",",".");
		double returnCredit = 0;
		String temp;
		
		if(isNumeric(credits)){
			returnCredit =  Integer.parseInt(credits);
		}else if (credits.indexOf("/") != -1) {
			temp = credits.substring(0 , credits.indexOf("/")).trim();			
			returnCredit = (isFloat(temp)) ? Double.parseDouble(temp) : 0;
		} 
		
		return returnCredit;
	}
	
	/**
	 * The following function checks whether the string is numeric.
	 * */
	protected static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	/**
	 * Finds an interval given @param upperKeyWord and @param lowerKeyWord
	 * Used in all *.txt files
	 * */
	protected ArrayList<String> findInterval(String upperKeyWord, String lowerKeyWord){
		ArrayList<String> newLines = new ArrayList<String>();
		boolean upperFound = false;
		
		for (String line : this.courseText) {	
			if(upperFound && !(line.toLowerCase().indexOf(lowerKeyWord.toLowerCase()) != -1)){
				if(!(line.length() ==0)) newLines.add(line);
			}else if (upperFound && line.toLowerCase().indexOf(lowerKeyWord.toLowerCase()) != -1){
				break;
			}
			
			if(line.toLowerCase().indexOf(upperKeyWord.toLowerCase()) != -1){
				upperFound = true;
			}			
		}	
		
		return newLines;
	}
	
	/**Boolean function for checking if the keyword exists*/
	protected boolean keyWordExists(String keyWord){	
		for (String line : this.courseText) {	
			if(line.toLowerCase().indexOf(keyWord.toLowerCase()) != -1){
				return true;
			}		
		}
		return false;
	}
	
	
	/**
	 * Interprets student remarks, the remarks are the last chain of 
	 * strings in the student file.
	 * */
	protected String interpreteRemark( ){
		String finalString = " ";
		
		//Take all after Average rating
		ArrayList<String> remarkLines = findInterval("Average rating:","$unreachablehash$");		
		
		for(String line:remarkLines){
			finalString += line + " "; 
		}
		
		return finalString;
	}
	
	/**
	 * The following function structures each line in the interval
	 * It makes sure that the lines have 3 components, or
	 * in case the student has taken the course 4 components
	 * the course code, course name, credits or
	 * the course code, course name, grade and credits
	 * */
	protected ArrayList<String> structureLines(ArrayList<String> lines){
		ArrayList<String> lineParts;
		int tempSize;
		int counter = 0;
		boolean skipNext = false;
		ArrayList<String> returnLines= new ArrayList<String>();		
		
		for (String line : lines) {	
			if(skipNext){
				skipNext = false;
				continue;
			}
			lineParts = lineInterpreter(line);
			tempSize = lineParts.size();
			if(tempSize==4){
				returnLines.add(line);
			}
			else if(tempSize==3){
				if(creditFormat(lineParts.get(2))){
					returnLines.add(line);
				}else if(gradeFormat(lineParts.get(2))){
					returnLines.add(line + lines.get(counter+1));
					skipNext = true;
				}
			}
			else if(tempSize<3){
				returnLines.add(line + lines.get(counter+1));
				skipNext = true;
			}
			counter++;
		}	
		return returnLines;
	}
	
	/**
	 * Determines if a given string is a course code
	 * all the course codes have upper case letters only with numbers
	 * */
	private boolean codeFormat(String text){ 
		return text.equals(text.toUpperCase());
	}
	
	/**
	 * Checks if the string has a credit format
	 * */
	private boolean creditFormat(String text){ 
		int countComma = text.length() - text.replaceAll(",","").length();
		int countDash = text.length() - text.replaceAll("/","").length();
		return countComma == 2 && countDash == 1;
	}
	
	/**
	 * Checks if the string has grade format
	 * */
	private boolean gradeFormat(String text){ 		
		return isFloat(text);
	}
	
	
	/**
	 * Interprets given string by dividing between '\t' character
	 * */
	protected ArrayList<String> lineInterpreter(String line){
		
		ArrayList<String> retrunInterpreated = new ArrayList<String>();		
		
		line  = line.replace("*", " ");
		
		String[] parts =  line.split("\t");
		for(String x: parts){
			if(x.trim().length()>=1 && x !=null && !(x.trim().indexOf(":") != -1 && x.trim().length() == 1)){
				retrunInterpreated.add(x.trim());
			}
		}
		return retrunInterpreated;
	}
	
	//TODO remove this section after test
	protected void printTest(StudentProgram newProgram){
		for(String coruseCodes: newProgram.getPropadeuic().keySet()){
			System.out.println("key is: "+newProgram.getPropadeuic().get(coruseCodes));
		}
	}
}
