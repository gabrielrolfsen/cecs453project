/*
 * Class Name: Student.java
 * Description: Model object class for Student table
 * Author: Hao Vo
 */

package edu.csulb.android.cecs453project.db;

public class Student {
	
	String IdStudent;
	String sPassword;
	String sFirstName;
	String sLastName;
	String sDateOfBirth;
	String sEmail;
	String sPhone;
	
	//constructors
	public Student(){
	}
	
	public Student(String id ){
		IdStudent = id;
	}
	
	//getters
	public String getIdStudent(String id) { 
		return IdStudent; }
	
	public String getStuPassword(String password) { 
		return sPassword; }
	
	public String getStuFirstName(String firstName) { 
		return sFirstName; }
	
	public String getStuLastName(String lastName) { 
		return sLastName; }
	
	public String getStuDOB(String dob) { 
		return sDateOfBirth; }
	
	public String getStuEmail(String email) { 
		return sEmail; }
	
	public String getStuPhone(String phone) { 
		return sPhone;}
	
	//setters
	public void setIdStudent(String id) { 
		IdStudent = id; }
	
	public void setStuPassword(String password) { 
		sPassword = password; }
	
	public void setStuFirstName(String firstName) { 
		sFirstName = firstName; }
	
	public void setStuLastName(String lastName) { 
		sLastName = lastName; }
	
	public void setStuDOB(String dob) { 
		sDateOfBirth = dob; }
	
	public void setStuEmail(String email) { 
		sEmail = email; }
	
	public void setStuPhone(String phone) { 
		sPhone = phone;}

}
