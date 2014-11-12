/*
 * Class Name: Instructor.java
 * Description: Model object class for Instructor table
 * Author: Hao Vo
 */

package edu.csulb.android.cecs453project.db;

public class Instructor {
	
	String IdInstructor;
	String iPassword;
	String iFirstName;
	String iLastName;
	String iOfficeRoom;
	String iOfficeBuilding;
	String iOfficeDayTime;
	String iEmail;
	
	//constructors
	public Instructor(){
	}
	
	public Instructor(String id ){
		IdInstructor = id;
	}
	
	//getters
	public String getInsId(String id) { 
		return IdInstructor; }
	
	public String getInsPassword (String password) { 
		return iPassword; }
	
	public String getInsFirstName (String firstName) { 
		return iFirstName; }
	
	public String getInsLastName(String lastName) { 
		return iLastName; }
	
	public String getInsOfficeRoom(String room) { 
		return iOfficeRoom; }
	
	public String getInsOfficeBuilding(String building) { 
		return iOfficeBuilding; }
	
	public String getInsOfficeDayTime(String daytime) { 
		return iOfficeDayTime;}
	
	public String getInsEmail(String email) { 
		return iEmail;}
	
	//setters
	public void setInsId(String id) { 
		IdInstructor = id; }
	
	public void setInsPassword (String password) { 
		iPassword = password; }
	
	public void setInsFirstName (String firstName) { 
		iFirstName = firstName; }
	
	public void setInsLastName(String lastName) { 
		iLastName = lastName; }
	
	public void setInsOfficeRoom(String room) { 
		iOfficeRoom = room; }
	
	public void setInsOfficeBuilding(String building) { 
		iOfficeBuilding = building; }
	
	public void setInsOfficeDayTime(String daytime) { 
		iOfficeDayTime = daytime;}
	
	public void setInsEmail(String email) { 
		iEmail = email;}

}
