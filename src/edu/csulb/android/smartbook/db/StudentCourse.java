/*
 * Class Name: StudentCourse.java
 * Description: Model object class for StudentCourse table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.db;

public class StudentCourse {
	
	String IdStudentCourse;
	String IdCourse;
	String IdStudent;
	String scFinalGrade;

	
	//constructors
	public StudentCourse(){
	}
	
	public StudentCourse(String idStuCourse, String idCourse, String idStudent ){
		IdStudentCourse = idStuCourse;
		IdCourse = idCourse;
		IdStudent = idStudent;
	}

	//getters
	public String getIdStudentCourse() {
		return IdStudentCourse;
	}

	public String getIdCourse() {
		return IdCourse;
	}

	public String getIdStudent() {
		return IdStudent;
	}

	public String getScFinalGrade() {
		return scFinalGrade;
	}

	//setters
	public void setIdStudentCourse(String idStudentCourse) {
		IdStudentCourse = idStudentCourse;
	}

	public void setIdCourse(String idCourse) {
		IdCourse = idCourse;
	}

	public void setIdStudent(String idStudent) {
		IdStudent = idStudent;
	}

	public void setScFinalGrade(String scFinalGrade) {
		this.scFinalGrade = scFinalGrade;
	}
	
	
}
