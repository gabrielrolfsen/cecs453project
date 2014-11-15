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
	public String getIdCourse() {
		return IdCourse;
	}

	public String getaName() {
		return aName;
	}

	public String getaDescription() {
		return aDescription;
	}

	public String getaDuedate() {
		return aDuedate;
	}

	public String getaURL() {
		return aURL;
	}

	//setters
	public void setIdCourse(String idCourse) {
		IdCourse = idCourse;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public void setaDescription(String aDescription) {
		this.aDescription = aDescription;
	}

	public void setaDuedate(String aDuedate) {
		this.aDuedate = aDuedate;
	}

	public void setaURL(String aURL) {
		this.aURL = aURL;
	}

	

}
