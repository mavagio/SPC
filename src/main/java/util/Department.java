package main.java.util;

/**
 * @author Martin Avagyan 
 */

public class Department {
	
	/**
	 * The following properties are identifers for each program
	 * */
	
	private Degree bsFields;
	private Degree msFields;
	
	private String endKey;
	
	private Interval year;
	
	private Interval studentName;
	
	private Interval studentNumber;
	
	private Interval studentVariant;
	
	
	
	public Department(Degree bsFields, Degree msFields, String endKey,Interval year,Interval studentName,Interval studentNumber,Interval studentVariant) {
		this.bsFields = bsFields;
		this.msFields = msFields;
		this.endKey = endKey;
		this.year = year;
		this.studentName = studentName;
		this.studentNumber = studentNumber;
		this.studentVariant = studentVariant;
	}

	public Degree getBsFields() {
		return bsFields;
	}

	public void setBsFields(Degree bsFields) {
		this.bsFields = bsFields;
	}

	public Degree getMsFields() {
		return msFields;
	}

	public void setMsFields(Degree msFields) {
		this.msFields = msFields;
	}

	public String getEndKey() {
		return endKey;
	}

	public void setEndKey(String endKey) {
		this.endKey = endKey;
	}

	public Interval getYear() {
		return year;
	}

	public void setYear(Interval year) {
		this.year = year;
	}

	public Interval getStudentName() {
		return studentName;
	}

	public void setStudentName(Interval studentName) {
		this.studentName = studentName;
	}

	public Interval getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(Interval studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Interval getStudentVariant() {
		return studentVariant;
	}

	public void setStudentVariant(Interval studentVariant) {
		this.studentVariant = studentVariant;
	}
	
	
}
