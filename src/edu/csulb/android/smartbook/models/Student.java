package edu.csulb.android.smartbook.models;

/**
 * 
 * Student : Model object class for database Student table
 * 
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 19, 2014
 */
public class Student {

	String IdStudent;
	String sPassword;
	String sFirstName;
	String sLastName;
	String sDateOfBirth;
	String sEmail;
	String sPhone;
	String sMajor;

	// constructors
	public Student() {
	}

	public Student(String idStudent, String sPassword, String sFirstName,
			String sLastName, String sDateOfBirth, String sEmail,
			String sPhone, String sMajor) {
		super();
		IdStudent = idStudent;
		this.sPassword = sPassword;
		this.sFirstName = sFirstName;
		this.sLastName = sLastName;
		this.sDateOfBirth = sDateOfBirth;
		this.sEmail = sEmail;
		this.sPhone = sPhone;
		this.sMajor = sMajor;
	}

	public Student(String id) {
		IdStudent = id;
	}

	// getters
	public String getIdStudent() {
		return IdStudent;
	}

	public String getsPassword() {
		return sPassword;
	}

	public String getsFirstName() {
		return sFirstName;
	}

	public String getsLastName() {
		return sLastName;
	}

	public String getsDateOfBirth() {
		return sDateOfBirth;
	}

	public String getsEmail() {
		return sEmail;
	}

	public String getsPhone() {
		return sPhone;
	}

	public String getsMajor() {
		return this.sMajor;
	}

	// setters
	public void setIdStudent(String idStudent) {
		IdStudent = idStudent;
	}

	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	public void setsFirstName(String sFirstName) {
		this.sFirstName = sFirstName;
	}

	public void setsLastName(String sLastName) {
		this.sLastName = sLastName;
	}

	public void setsDateOfBirth(String sDateOfBirth) {
		this.sDateOfBirth = sDateOfBirth;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public void setsMajor(String sMajor) {
		this.sMajor = sMajor;
	}

}
