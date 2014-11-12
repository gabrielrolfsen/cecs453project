/*
 * Class Name: Course.java
 * Description: Model object class for Course table
 * Author: Hao Vo
 */

package edu.csulb.android.cecs453project.db;

public class Course {
	
	String IdCourse;
	String cName;
	String cType;
	String cDays;
	String cTime;
	String cLocation;
	String cSemester;
	String cYear;
	String IdInstructor;
	
	
	//constructors
	public Course(){
	}
	
	public Course(String id ){
		IdCourse = id;
	}
	
	//getters
	public String getIdSCourse(String id) { 
		return IdCourse; }
	
	public String getCourseName(String name) { 
		return cName; }
	
	public String getCourseType(String type) { 
		return cType; }
	
	public String getCourseDays(String day) { 
		return cDays; }
	
	public String getCourseTime(String time) { 
		return cTime; }
	
	public String getCourseLocation(String location) { 
		return cLocation; }
	
	public String getCourseSemester(String semester) { 
		return cSemester;}
	
	public String getCourseYear(String year) { 
		return cYear; }
	
	public String getCourseIdInstructor(String idInstructor) { 
		return IdInstructor;}
	
	//setters
	public void setIdSCourse(String id) { 
		IdCourse = id; }
	
	public void setCourseName(String name) { 
		cName = name; }
	
	public void setCourseType(String type) { 
		cType = type; }
	
	public void setCourseDays(String day) { 
		cDays = day; }
	
	public void setCourseTime(String time) { 
		cTime = time; }
	
	public void setCourseLocation(String location) { 
		cLocation = location; }
	
	public void setCourseSemester(String year) { 
		cSemester = year;}
	
	public void setCourseYear(String idInstructor) { 
		cYear = idInstructor; }
	
	public void setCourseIdInstructor(String phone) { 
		IdInstructor = phone;}


}
