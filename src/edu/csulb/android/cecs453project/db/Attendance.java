/*
 * Class Name: Attendance.java
 * Description: Model object class for Attendance table
 * Author: Hao Vo
 */

package edu.csulb.android.cecs453project.db;

public class Attendance {
	
	String IdStudentCourse;
	String aDate;
	int aPresent;	//1 == true, 0 == false

	
	//constructors
	public Attendance(){
	}
	
	public Attendance(String id ){
		IdStudentCourse = id;
	}
	
	//getters
	public String getAttId(String id) { 
		return IdStudentCourse; }
	
	public String getAttDate(String date) { 
		return aDate; }
	
	public int getAttPresent(int present) { 
		return aPresent; }
	
	
	//setters
	public void setAttId(String id) { 
		IdStudentCourse = id; }
	
	public void setAttDate(String date) { 
		aDate = date; }
	
	public void setAttPresent(int present) { 
		aPresent = present; }
	

}
