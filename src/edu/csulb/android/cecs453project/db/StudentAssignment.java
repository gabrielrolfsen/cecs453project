/*
 * Class Name: StudentAssignment.java
 * Description: Model object class for StudentAssignment table
 * Author: Hao Vo
 */

package edu.csulb.android.cecs453project.db;

public class StudentAssignment {
	
	String IdStudentCourse;
	String aName;
	String saGrade;
	String saInstructorComment;


	//constructors
	public StudentAssignment(){
	}
	
	public StudentAssignment(String id, String name ){
		IdStudentCourse = id;
		aName = name;
	}
	
	//getters
	public String getIdStudentAssignment(String id) { 
		return IdStudentCourse; }
	
	public String getStuAssignName(String name) { 
		return aName; }
	
	public String getStuAssignGrade(String grade) { 
		return saGrade; }
	
	public String getStuAssignComment(String comment) { 
		return saInstructorComment; }
	
	
	//setters
	public void setIdStudentAssignment(String id) { 
		IdStudentCourse = id; }
	
	public void setStuAssignName(String name) { 
		aName = name; }
	
	public void setStuAssignGrade(String grade) { 
		saGrade = grade; }
	
	public void setStuAssignComment(String comment) { 
		saInstructorComment = comment; }

}
 