/*
 * Class Name: StudentCourse.java
 * Description: Model object class for StudentCourse table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.models;
/**
 * 
 * StudentCourse : Model object class for database StudentCourse table
 * 
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 19, 2014
 */
public class StudentCourse {

	String IdStudent;
	String IdCourse;
	String scFinalGrade;

	// constructors
	public StudentCourse() {
	}

	public StudentCourse(final String idStudent, final String idCourse,
			final String scFinalGrade) {
		super();
		IdStudent = idStudent;
		IdCourse = idCourse;
		this.scFinalGrade = scFinalGrade;
	}

	// getters
	public String getIdStudent() {
		return IdStudent;
	}

	public String getIdCourse() {
		return IdCourse;
	}

	public String getScFinalGrade() {
		return scFinalGrade;
	}

	// setters
	public void setIdStudent(final String idStudent) {
		IdStudent = idStudent;
	}

	public void setIdCourse(final String idCourse) {
		IdCourse = idCourse;
	}

	public void setScFinalGrade(final String scFinalGrade) {
		this.scFinalGrade = scFinalGrade;
	}

}
