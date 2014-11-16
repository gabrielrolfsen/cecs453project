/*
 * Class Name: Course.java
 * Description: Model object class for Course table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.db;

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
	
	public Course(String idCourse, String cName, String cType, String cDays,
			String cTime, String cLocation, String cSemester, String cYear,
			String idInstructor) {
		super();
		IdCourse = idCourse;
		this.cName = cName;
		this.cType = cType;
		this.cDays = cDays;
		this.cTime = cTime;
		this.cLocation = cLocation;
		this.cSemester = cSemester;
		this.cYear = cYear;
		IdInstructor = idInstructor;
	}

	//getters
	public String getIdCourse() {
		return IdCourse;
	}

	public String getcName() {
		return cName;
	}

	public String getcType() {
		return cType;
	}

	public String getcDays() {
		return cDays;
	}

	public String getcTime() {
		return cTime;
	}

	public String getcLocation() {
		return cLocation;
	}

	public String getcSemester() {
		return cSemester;
	}

	public String getcYear() {
		return cYear;
	}

	public String getIdInstructor() {
		return IdInstructor;
	}

	//setters
	public void setIdCourse(String idCourse) {
		IdCourse = idCourse;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public void setcDays(String cDays) {
		this.cDays = cDays;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public void setcLocation(String cLocation) {
		this.cLocation = cLocation;
	}

	public void setcSemester(String cSemester) {
		this.cSemester = cSemester;
	}

	public void setcYear(String cYear) {
		this.cYear = cYear;
	}

	public void setIdInstructor(String idInstructor) {
		IdInstructor = idInstructor;
	}

	

}
