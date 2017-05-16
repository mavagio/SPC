package main.java.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Martin Avagyan 
 */

public class Degree {
	private String mandatoryField;
	private String postMandatoryField;
	private String optionalField;
	private String projectField;
	private String extraCoursesField;
	
	public Degree(String mandatoryField, String postMandatoryField,
			String optionalField, String projectField, String extraCourses) {
		this.mandatoryField = mandatoryField;
		this.postMandatoryField = postMandatoryField;
		this.optionalField = optionalField;
		this.projectField = projectField;
		this.extraCoursesField = extraCourses;
	}

	public String getMandatoryField() {
		return mandatoryField;
	}

	public void setMandatoryField(String mandatoryField) {
		this.mandatoryField = mandatoryField;
	}

	public String getPostMandatoryField() {
		return postMandatoryField;
	}

	public void setPostMandatoryField(String postMandatoryField) {
		this.postMandatoryField = postMandatoryField;
	}

	public String getOptionalField() {
		return optionalField;
	}

	public void setOptionalField(String optionalField) {
		this.optionalField = optionalField;
	}

	public String getProjectField() {
		return projectField;
	}

	public void setProjectField(String projectField) {
		this.projectField = projectField;
	}
	
	public ArrayList<String> getAllFields(){
		return new ArrayList<String>(Arrays.asList(mandatoryField,optionalField,projectField));
	}

	public String getExtraCoursesField() {
		return extraCoursesField;
	}

	public void setExtraCoursesField(String extraCourses) {
		this.extraCoursesField = extraCourses;
	}
	
	
	
}
