/*
 * Class Name: Instructor.java
 * Description: Model object class for Instructor table
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.db;

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
	
	public Instructor(String idInstructor, String iPassword, String iFirstName,
			String iLastName, String iOfficeRoom, String iOfficeBuilding,
			String iOfficeDayTime, String iEmail) {
		super();
		IdInstructor = idInstructor;
		this.iPassword = iPassword;
		this.iFirstName = iFirstName;
		this.iLastName = iLastName;
		this.iOfficeRoom = iOfficeRoom;
		this.iOfficeBuilding = iOfficeBuilding;
		this.iOfficeDayTime = iOfficeDayTime;
		this.iEmail = iEmail;
	}

	public String getIdInstructor() {
		return IdInstructor;
	}

	//getters
	public String getiPassword() {
		return iPassword;
	}

	public String getiFirstName() {
		return iFirstName;
	}

	public String getiLastName() {
		return iLastName;
	}

	public String getiOfficeRoom() {
		return iOfficeRoom;
	}

	public String getiOfficeBuilding() {
		return iOfficeBuilding;
	}

	public String getiOfficeDayTime() {
		return iOfficeDayTime;
	}

	public String getiEmail() {
		return iEmail;
	}

	//setters
	public void setIdInstructor(String idInstructor) {
		IdInstructor = idInstructor;
	}

	public void setiPassword(String iPassword) {
		this.iPassword = iPassword;
	}

	public void setiFirstName(String iFirstName) {
		this.iFirstName = iFirstName;
	}

	public void setiLastName(String iLastName) {
		this.iLastName = iLastName;
	}

	public void setiOfficeRoom(String iOfficeRoom) {
		this.iOfficeRoom = iOfficeRoom;
	}

	public void setiOfficeBuilding(String iOfficeBuilding) {
		this.iOfficeBuilding = iOfficeBuilding;
	}

	public void setiOfficeDayTime(String iOfficeDayTime) {
		this.iOfficeDayTime = iOfficeDayTime;
	}

	public void setiEmail(String iEmail) {
		this.iEmail = iEmail;
	}

		

}
