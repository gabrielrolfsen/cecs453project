/*
 * Class Name: Attendance.java
 * Description: Model object class for Attendance table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.models;

public class Attendance {
	


	String IdStudentCourse;
	String aDate;
	int aPresent;	//1 == true, 0 == false

	
	//constructors
	public Attendance(){
	}
	
	public Attendance(String idStudentCourse, String aDate, int aPresent) {
		super();
		IdStudentCourse = idStudentCourse;
		this.aDate = aDate;
		this.aPresent = aPresent;
	}

	//getters
	public String getIdStudentCourse() {
		return IdStudentCourse;
	}

	public String getaDate() {
		return aDate;
	}

	public int getaPresent() {
		return aPresent;
	}

	//setters
	public void setIdStudentCourse(String idStudentCourse) {
		IdStudentCourse = idStudentCourse;
	}

	public void setaDate(String aDate) {
		this.aDate = aDate;
	}

	public void setaPresent(int aPresent) {
		this.aPresent = aPresent;
	}

	
}
