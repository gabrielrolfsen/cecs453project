/*
 * Class Name: Assignment.java
 * Description: Model object class for Assignment table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.db;

public class Assignment {
	
	String IdCourse;
	String aName;
	String aDescription;
	String aDuedate;
	String aURL;

	
	//constructors
	public Assignment(){
	}
	
	public Assignment(String id ){
		IdCourse = id;
	}
	
	//getters
	public String getIdCourse(String id) { 
		return IdCourse; }
	
	public String getAssignName(String name) { 
		return aName; }
	
	public String getAssignDescription(String description) { 
		return aDescription; }
	
	public String getAssignDueDate(String duedate) { 
		return aDuedate; }
	
	public String getAssignURL(String url) { 
		return aURL; }
		
	//setters
	public void setIdCourse(String id) { 
		IdCourse = id; }
	
	public void setAssignName(String name) { 
		aName = name; }
	
	public void setAssignDescription(String description) { 
		aDescription = description; }
	
	public void setAssignDueDate(String duedate) { 
		aDuedate = duedate; }
	
	public void setAssignURL(String url) { 
		aURL = url; }
	


}
