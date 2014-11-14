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
	public String getIdStudentCourse(String id) { 
		return IdStudentCourse; }
	
	public String getIdCourse(String courseID) { 
		return IdCourse; }
	
	public String getIdStudent(String studentID) { 
		return IdStudent; }
	
	public String getStuCourseFinalGrade(String grade) { 
		return scFinalGrade; }
	
	
	//setters
	public void setIdStudentCourse(String id) { 
		IdStudentCourse = id; }
	
	public void setIdCourse(String courseID) { 
		IdCourse = courseID; }
	
	public void setIdStudent(String studentID) { 
		IdStudent = studentID; }
	
	public void setStuCourseFinalGrade(String grade) { 
		scFinalGrade = grade; }

}
