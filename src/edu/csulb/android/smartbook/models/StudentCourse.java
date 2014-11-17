/*
 * Class Name: StudentCourse.java
 * Description: Model object class for StudentCourse table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.models;

public class StudentCourse {
	
	String IdStudent;
	String IdCourse;
	String scFinalGrade;

	
	//constructors
	public StudentCourse(){
	}
	
	public StudentCourse(String idStudent,
			String idCourse, String scFinalGrade) {
		super();
		IdStudent = idStudent;
		IdCourse = idCourse;
		this.scFinalGrade = scFinalGrade;
	}

	//getters
	public String getIdStudent() {
		return IdStudent;
	}

	public String getIdCourse() {
		return IdCourse;
	}

	public String getScFinalGrade() {
		return scFinalGrade;
	}

	//setters
	public void setIdStudent(String idStudent) {
		IdStudent = idStudent;
	}

	public void setIdCourse(String idCourse) {
		IdCourse = idCourse;
	}
	
	public void setScFinalGrade(String scFinalGrade) {
		this.scFinalGrade = scFinalGrade;
	}
	
}
