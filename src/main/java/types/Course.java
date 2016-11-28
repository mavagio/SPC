package main.java.types;

/**
*@author Martin Avagyan 
* */

public class Course {
	/**
	 * @param courseCode is the key code of each course in a program
	 * @param courseName the given name of the course in a program
	 * @param courseCredits amount of course credits
	 * */
	private String courseCode;
	private String courseName;
	private double courseCredits;
	private double grade;
	private int matched =0;
	private String courseGradeAsString = "V";
	
	
	/**
	 * constructor without credits: default amount of credits is 5
	 * */
	public Course(String courseCode,String courseName){
		this(courseCode,courseName,5);
	}
	
	/**
	 * constructor without given grade
	 * */
	public Course(String courseCode,String courseName, double courseCredits){
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseCredits = courseCredits;
	}
	public Course(String courseCode,String courseName,double grade, double courseCredits){
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseCredits = courseCredits;
		this.grade = grade;
	}

	/**
	 * Setters & getters
	 * */
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getCourseCredits() {
		return courseCredits;
	}

	public void setCourseCredits(int courseCredits) {
		this.courseCredits = courseCredits;
	}
	public Object getGrade() {
		return (Double.compare(grade,10.1)==-1)? grade :courseGradeAsString ;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
	

	public int isMatched() {
		return matched;
	}

	public void setMatched(int matched) {
		this.matched = matched;
	}

	public String getCourseGradeAsString() {
		return courseGradeAsString;
	}

	public void setCourseGradeAsString(String courseGradeAsString) {
		this.courseGradeAsString = courseGradeAsString;
	}

	
	@Override
	public String toString() {
		return ("(" + this.getCourseCode() + "\t" + 
				     this.getCourseName() + "\t" + 
				     (int)this.getCourseCredits()  + ")").trim();
	}
	
	
	public Course copy(){
		Course cCourse = new Course(new String(courseCode),new String(courseName),courseCredits);
		cCourse.setGrade(grade);
		cCourse.setMatched(matched);
		
		return cCourse;
	}
}
