/*
 * Class Name: Attendance.java
 * Description: Model object class for Attendance table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.models;

public class Attendance {
	
	String IdStudent;
	String IdCourse;
	String aDate;
	int aPresent;	//1 == true, 0 == false

	
	//constructors
	public Attendance(){
	}
	
	public Attendance(String idStudent, String idCourse, String aDate, int aPresent) {
		super();
		IdStudent = idStudent;
		IdCourse = idCourse;
		this.aDate = aDate;
		this.aPresent = aPresent;
	}

	//getters
	public String getIdStudent() {
		return IdStudent;
	}

	public String getIdCourse() {
		return IdCourse;
	}

	public String getaDate() {
		return aDate;
	}

	public int getaPresent() {
		return aPresent;
	}

	//setters
	public void setIdStudent(String idStudent) {
		IdStudent = idStudent;
	}

	public void setIdCourse(String idCourse) {
		IdCourse = idCourse;
	}

	public void setaDate(String aDate) {
		this.aDate = aDate;
	}

	public void setaPresent(int aPresent) {
		this.aPresent = aPresent;
	}


	
}
