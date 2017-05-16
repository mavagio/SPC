package main.java.util;

import java.util.HashMap;

/**
 * @author Martin Avagyan
 */

/**
 * The department class is meant to provide all predefined departments in the faculty
 * Later the user will be able to add their own department
 * This is done to avoid hard-code inside an input reader
 * */

public class Departments {
	
	//
	private static HashMap<String,Department> departs;
	
	public Departments(){
		departs = new HashMap<String,Department>();
		genDepartments();
	}
	
	private static  void  genDepartments(){	
		
		// Include computing science department
		departs.put("Computing Science", new Department(new Degree(
				"Compulsory courses / Major",
				"Electives / Minor", "Electives / Minor",
				"Bachelorproject including essay","Extracurricular courses"), new Degree("Mandatory courses",
				"Guided choice", "Free choice", "Master thesis","Additional courses"), "Courses",
				new Interval("Account Date",
						"Study programme is under consideration"),
				new Interval("", "Process:"), new Interval("", "Process:"),new Interval("Process:", "You are applied")));

		// Include mathematics department
		departs.put("Mathematic", new Department(new Degree(
				"Mandatory courses propaedeutic",
				"Mandatory courses post-propaedeutic", "Minor/ electives",
				"Bachelor's project","Extra curriculair courses"), new Degree("Mandatory courses",
				"Guided choice", "Free choice", "Master thesis","Additional courses"), "Courses",
				new Interval("Account Date",
						"Study programme is under consideration"),
				new Interval("", "Process:"), new Interval("", "Process:"),new Interval("Process:", "Let er op")));

		// Include chemistry department
		departs.put("Chemistry", new Department(new Degree(
				"Mandatory courses propaedeutic",
				"Mandatory courses post-propaedeutic", "Minor/ electives",
				"Bachelor's project","Extra curriculair courses"), new Degree("Mandatory courses",
				"Guided choice", "Free choice", "Master thesis","Additional courses"), "Courses",
				new Interval("Account Date",
						"Study programme is under consideration"),
				new Interval("", "Process:"), new Interval("", "Process:"),new Interval("Process:", "Let er op")));
	} 
	
	public static Department getDepartment(String name){			
		return departs.get(name);		
	}
	
	public HashMap<String,Department> getAllDepartments(){			
		return departs;		
	}
}
