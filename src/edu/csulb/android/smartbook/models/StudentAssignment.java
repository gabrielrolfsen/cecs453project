/*
 * Class Name: StudentAssignment.java
 * Description: Model object class for StudentAssignment table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.models;
/**
 * 
 * StudentAssignment : Model object class for database StudentAssignment table
 * 
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 19, 2014
 */
public class StudentAssignment {
	
	String IdStudent;
	String IdCourse;
	String aName;
	String saGrade;
	String saInstructorComment;


	//constructors
	public StudentAssignment(){
	}
	
	public StudentAssignment(String idStudent, String idCourse, String aName,
			String saGrade, String saInstructorComment) {
		super();
		IdStudent = idStudent;
		IdCourse = idCourse;
		this.aName = aName;
		this.saGrade = saGrade;
		this.saInstructorComment = saInstructorComment;
	}

	//getters
	public String getIdStudent() {
		return IdStudent;
	}

	public String getIdCourse() {
		return IdCourse;
	}

	public String getaName() {
		return aName;
	}

	public String getSaGrade() {
		return saGrade;
	}

	//setters
	public String getSaInstructorComment() {
		return saInstructorComment;
	}

	public void setIdStudent(String idStudent) {
		IdStudent = idStudent;
	}

	public void setIdCourse(String idCourse) {
		IdCourse = idCourse;
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
 