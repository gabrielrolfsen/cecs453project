/*
 * Class Name: StudentAssignment.java
 * Description: Model object class for StudentAssignment table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.db;

public class StudentAssignment {
	
	String IdStudentCourse;
	String aName;
	String saGrade;
	String saInstructorComment;


	//constructors
	public StudentAssignment(){
	}
	
	public StudentAssignment(String idStudentCourse, String aName,
			String saGrade, String saInstructorComment) {
		super();
		IdStudentCourse = idStudentCourse;
		this.aName = aName;
		this.saGrade = saGrade;
		this.saInstructorComment = saInstructorComment;
	}

	//getters
	public String getIdStudentCourse() {
		return IdStudentCourse;
	}

	public String getaName() {
		return aName;
	}

	public String getSaGrade() {
		return saGrade;
	}

	public String getSaInstructorComment() {
		return saInstructorComment;
	}

	//setters
	public void setIdStudentCourse(String idStudentCourse) {
		IdStudentCourse = idStudentCourse;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public void setSaGrade(String saGrade) {
		this.saGrade = saGrade;
	}

	public void setSaInstructorComment(String saInstructorComment) {
		this.saInstructorComment = saInstructorComment;
	}
	
	
}
 