/*
 * Class Name: DatabaseHandler.java
 * Description: Handle create sample database with sample data to use in the project
 * Author: Hao Vo
 */

package edu.csulb.android.smartbook.db;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.android.smartbook.models.Assignment;
import edu.csulb.android.smartbook.models.Attendance;
import edu.csulb.android.smartbook.models.Course;
import edu.csulb.android.smartbook.models.Instructor;
import edu.csulb.android.smartbook.models.Student;
import edu.csulb.android.smartbook.models.StudentAssignment;
import edu.csulb.android.smartbook.models.StudentCourse;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static DatabaseHandler mDatabase;
	
	//Database version and name
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "projectDemoDatabase";
		
	//Tables Names
	public static final String TABLE_STUDENT = "Student";
	public static final String TABLE_INSTRUCTOR = "Instructor";
	public static final String TABLE_COURSE = "Course";
	public static final String TABLE_STUDENT_COURSE = "StudentCourse";
	public static final String TABLE_ATTENDANCE = "Attendance";
	public static final String TABLE_ASSIGNMENT = "Assignment";
	public static final String TABLE_STUDENT_ASSIGNMENT = "StudentAssignment";
	
	// Column names for table Student
	public static final String KEY_STUDENT_ID = "IdStudent";
	public static final String KEY_STUDENT_PASS = "sPassword";
	public static final String KEY_STUDENT_FNAME = "sFirstName";
	public static final String KEY_STUDENT_LNAME = "sLastName";
	public static final String KEY_STUDENT_BIRTH = "sDateOfBirth";
	public static final String KEY_STUDENT_MAIL = "sEmail";
	public static final String KEY_STUDENT_PHONE = "sPhone";

	// Column names for table Instructor
	public static final String KEY_INSTRUCTOR_ID = "IdInstructor";
	public static final String KEY_INSTRUCTOR_PASS = "iPassword";
	public static final String KEY_INSTRUCTOR_FNAME = "iFirstName";
	public static final String KEY_INSTRUCTOR_LNAME = "iLastName";
	public static final String KEY_INSTRUCTOR_OFFICE_ROOM = "iOfficeRoom";
	public static final String KEY_INSTRUCTOR_OFFICE_BUILDING = "iOfficeBuilding";
	public static final String KEY_INSTRUCTOR_OFFICE_DAYTIME = "iOfficeDayTime";
	public static final String KEY_INSTRUCTOR_EMAIL = "iEmail";
	
	// Column names for table Course
	public static final String KEY_COURSE_ID = "IdCourse";
	public static final String KEY_COURSE_NAME = "cName";
	public static final String KEY_COURSE_TYPE = "cType";
	public static final String KEY_COURSE_DAYS = "cDays";
	public static final String KEY_COURSE_TIME = "cTime";
	public static final String KEY_COURSE_LOCATION = "cLocation";
	public static final String KEY_COURSE_SEMESTER = "cSemester";
	public static final String KEY_COURSE_YEAR = "cYear";	
	public static final String KEY_COURSE_INSTRUCTOR_ID = "IdInstructor";
	
	// Column names for table StudentCourse
	public static final String KEY_STUDENT_COURSE_ID = "IdStudentCourse";
	public static final String KEY_STUDENT_COURSE_IDCOURSE= "IdCourse";
	public static final String KEY_STUDENT_COURSE_IDSTUDENT = "IdStudent";
	public static final String KEY_STUDENT_COURSE_FINALGRADE = "scFinalGrade";
	
	// Column names for table Attendance
	public static final String KEY_ATTENDANCE_ID = "IdStudentCourse";
	public static final String KEY_ATTENDANCE_DATE = "aDate";
	public static final String KEY_ATTENDANCE_PRESENT = "aPresent";
	
	// Column names for table Assignment
	public static final String KEY_ASSIGNMENT_ID = "IdCourse";
	public static final String KEY_ASSIGNMENT_NAME = "aName";
	public static final String KEY_ASSIGNMENT_DESCRIPTION = "aDescription";
	public static final String KEY_ASSIGNMENT_DUEDATE = "aDueDate";
	public static final String KEY_ASSIGNMENT_URL = "aURL";
	
	// Column names for table StudentAssignment
	public static final String KEY_STUDENT_ASSIGNMENT_ID = "IdStudentCourse";
	public static final String KEY_STUDENT_ASSIGNMENT_NAME= "aName";
	public static final String KEY_STUDENT_ASSIGNMENT_GRADE = "saGrade";
	public static final String KEY_STUDENT_ASSIGNMENT_COMMENT = "saInstructorComment";

	/////////////////// Create statement for all tables ////////////////////////////////
	//Create table Student
	private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT +" (" +
														KEY_STUDENT_ID + " TEXT PRIMARY KEY NOT NULL," +
														KEY_STUDENT_PASS + " TEXT NOT NULL," +			
														KEY_STUDENT_FNAME + " TEXT NOT NULL," +
														KEY_STUDENT_LNAME + " TEXT," +
														KEY_STUDENT_BIRTH + " TEXT," +
														KEY_STUDENT_MAIL + " TEXT," +
														KEY_STUDENT_PHONE + " TEXT) ";
	//Create table Instructor
	private static final String CREATE_TABLE_INSTRUCTOR = "CREATE TABLE " + TABLE_INSTRUCTOR + " (" +
														KEY_INSTRUCTOR_ID + " TEXT PRIMARY KEY," +
														KEY_INSTRUCTOR_PASS + " TEXT," +
														KEY_INSTRUCTOR_FNAME + " TEXT," +
														KEY_INSTRUCTOR_LNAME + " TEXT," +
														KEY_INSTRUCTOR_OFFICE_ROOM + " TEXT," +
														KEY_INSTRUCTOR_OFFICE_BUILDING + " TEXT," +
														KEY_INSTRUCTOR_OFFICE_DAYTIME + " TEXT," +
														KEY_INSTRUCTOR_EMAIL + " TEXT) ";
	//Create table Course
	private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE + "(" +
													KEY_COURSE_ID + " TEXT PRIMARY KEY," +
													KEY_COURSE_NAME + " TEXT," + KEY_COURSE_TYPE + " TEXT," +
													KEY_COURSE_DAYS + " TEXT," + KEY_COURSE_TIME + " TEXT," +
													KEY_COURSE_LOCATION + " TEXT," + KEY_COURSE_SEMESTER + " TEXT," +
													KEY_COURSE_YEAR + " TEXT," + KEY_COURSE_INSTRUCTOR_ID + " TEXT," +
													"FOREIGN KEY (" + KEY_COURSE_INSTRUCTOR_ID + ") REFERENCES " + TABLE_INSTRUCTOR + "(" + KEY_INSTRUCTOR_ID + "))"; 
	//Create table StudentCourse
	private static final String CREATE_TABLE_STUDENT_COURSE = "CREATE TABLE " + TABLE_STUDENT_COURSE + "(" +
															KEY_STUDENT_COURSE_ID + " TEXT PRIMARY KEY," +
															KEY_STUDENT_COURSE_IDCOURSE + " TEXT NOT NULL," +
															KEY_STUDENT_COURSE_IDSTUDENT + " TEXT NOT NULL," +
															KEY_STUDENT_COURSE_FINALGRADE + " TEXT," +
															"FOREIGN KEY(" + KEY_STUDENT_COURSE_IDCOURSE + ") REFERENCES " + TABLE_COURSE + "(" + KEY_COURSE_ID + ")," +
															"FOREIGN KEY(" + KEY_STUDENT_COURSE_IDSTUDENT + ") REFERENCES " + TABLE_STUDENT + "(" + KEY_STUDENT_ID + "))";
	//Create table Attendance
	private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE " + TABLE_ATTENDANCE + "(" +
														KEY_ATTENDANCE_ID + " TEXT NOT NULL," +
														KEY_ATTENDANCE_DATE + " TEXT NOT NULL," +
														KEY_ATTENDANCE_PRESENT + " INT,"	+ //int type, 1 = true, 0 = false, 
														"PRIMARY KEY (" + KEY_ATTENDANCE_ID + "," + KEY_ATTENDANCE_DATE + ")," +
														"FOREIGN KEY (" + KEY_ATTENDANCE_ID + ") REFERENCES " + TABLE_STUDENT_COURSE + "(" + KEY_STUDENT_COURSE_ID + "))";
	//Create table Assignment
	private static final String CREATE_TABLE_ASSIGNMENT = "CREATE TABLE " + TABLE_ASSIGNMENT + "(" +
														KEY_ASSIGNMENT_ID + " TEXT NOT NULL," +
														KEY_ASSIGNMENT_NAME + " TEXT NOT NULL," +
														KEY_ASSIGNMENT_DESCRIPTION + " TEXT NOT NULL," +
														KEY_ASSIGNMENT_DUEDATE + " TEXT," +
														KEY_ASSIGNMENT_URL + " TEXT," +
														"FOREIGN KEY (" + KEY_ASSIGNMENT_ID + ") REFERENCES " + TABLE_COURSE + "(" + KEY_COURSE_ID + "))";
	//Create table student assignment
	private static final String CREATE_TABLE_STUDENT_ASSIGNMENT = "CREATE TABLE " + TABLE_STUDENT_ASSIGNMENT + "(" +
																KEY_STUDENT_ASSIGNMENT_ID + " TEXT NOT NULL," +
																KEY_STUDENT_ASSIGNMENT_NAME + " TEXT NOT NULL," +
																KEY_STUDENT_ASSIGNMENT_GRADE + " TEXT," +
																KEY_STUDENT_ASSIGNMENT_COMMENT + " TEXT," +
																"PRIMARY KEY (" + KEY_STUDENT_ASSIGNMENT_ID + "," + KEY_STUDENT_ASSIGNMENT_NAME + ")," +
																"FOREIGN KEY (" + KEY_STUDENT_ASSIGNMENT_NAME + ") REFERENCES " + TABLE_ASSIGNMENT + "(" + KEY_ASSIGNMENT_NAME + ")," +
																"FOREIGN KEY (" + KEY_STUDENT_ASSIGNMENT_ID + ") REFERENCES " + TABLE_STUDENT_COURSE + "(" + KEY_STUDENT_COURSE_ID + "))";
	
	private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Code to enable foreign key constraints but not work
		/*
		if (!db.isReadOnly()) {
		    // Enable foreign key constraints
		    db.execSQL("PRAGMA foreign_keys=ON;");
		    Log.d("Foreign keys", "enable");
		}
		*/
		
		Log.d("Create table", "Creating...");		//log for debug
		
		db.execSQL(CREATE_TABLE_STUDENT);
		db.execSQL(CREATE_TABLE_INSTRUCTOR);
		db.execSQL(CREATE_TABLE_COURSE);
		db.execSQL(CREATE_TABLE_STUDENT_COURSE);
		db.execSQL(CREATE_TABLE_ASSIGNMENT);
		db.execSQL(CREATE_TABLE_STUDENT_ASSIGNMENT);
		db.execSQL(CREATE_TABLE_ATTENDANCE);
		Log.d("Create table", "Done");			//log for debug
		
		
		///////// Sql statements to insert sample data ///////////
		Log.d("Insert to student","Inserting...");
		//Student table, insert demo data
		db.execSQL("INSERT INTO Student VALUES ('1111','1234','John','Kane','01/01/1963','john.kane@csulb.edu','1205434521')");
		db.execSQL("INSERT INTO Student VALUES ('2222','1234','James','Nort','21/01/1983','James.Nort@csulb.edu','120854314')");
		db.execSQL("INSERT INTO Student VALUES ('3333','1234','Kate','Line','15/06/1993','Kate.Line@csulb.edu','1204513784')");
		db.execSQL("INSERT INTO Student VALUES ('4444','1234','TempF4','TempL4','24/08/1984','Temp4@csulb.edu','1201234575')");
		db.execSQL("INSERT INTO Student VALUES ('5555','1234','TempF5','TempL5','16/12/1973','Temp5@csulb.edu','1201357512')");
		db.execSQL("INSERT INTO Student VALUES ('6666','1234','TempF6','TempL6','09/07/1986','Temp6@csulb.edu','1201675123')");
		
		Log.d("Insert to Instructor","Inserting...");
		//Instructor table, insert demo data
		db.execSQL("INSERT INTO Instructor VALUES ('E1111','1234','InsFN1','InsLN1','502','ECS','MonWed 1-3 pm','Instructor1@csulb.edu')");
		db.execSQL("INSERT INTO Instructor VALUES ('E2222','1234','InsFN2','InsLN2','531','ECS','Thurday 4-6 pm','')");
		db.execSQL("INSERT INTO Instructor VALUES ('E3333','1234','InsFN3','InsLN3','','','','')");
		
		Log.d("Insert to Course","Inserting...");
		//Course table, insert demo data
		db.execSQL("INSERT INTO Course VALUES ('C1235','Database','SEM','Mon Wed','1-1:50 pm','ECS 308','Fall', '2014','E1111')");
		db.execSQL("INSERT INTO Course VALUES ('C4457','Mobile Development','SEM','Tue Thu','3-3:50pm','ECS 302','Fall', '2014','E2222')");
		Log.d("Insert data","Done");

	}
	
	/////////////// query a row base on primary key/////////////////////////////////////////////////////////////
	public Student getStudent(String student_id){
		SQLiteDatabase db = mDatabase.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_STUDENT + " WHERE "
	            + KEY_STUDENT_ID + " = \"" + student_id + "\"";
		
		Cursor c = db.rawQuery(selectQuery, null);
		Student st = null;
		
		if (c.getCount() > 0){
	        c.moveToFirst();
	        st = new Student(c.getString(c.getColumnIndex(KEY_STUDENT_ID)),
							c.getString(c.getColumnIndex(KEY_STUDENT_PASS)),
							c.getString(c.getColumnIndex(KEY_STUDENT_FNAME)),
							c.getString(c.getColumnIndex(KEY_STUDENT_LNAME)),
							c.getString(c.getColumnIndex(KEY_STUDENT_BIRTH)),	
							c.getString(c.getColumnIndex(KEY_STUDENT_MAIL)),
							c.getString(c.getColumnIndex(KEY_STUDENT_PHONE)) );	
	        return st;
		}
		else
			return null;
	}

	public Assignment getAssignment(String assignmentID) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENT + " WHERE "
	            + KEY_ASSIGNMENT_ID + " = " + assignmentID;
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	    {
	        c.moveToFirst();
	 	    Assignment a = new Assignment((c.getString(c.getColumnIndex(KEY_ASSIGNMENT_ID))),
	    								(c.getString(c.getColumnIndex(KEY_ASSIGNMENT_NAME))),
	    								(c.getString(c.getColumnIndex(KEY_ASSIGNMENT_DESCRIPTION))),
	    								(c.getString(c.getColumnIndex(KEY_ASSIGNMENT_DUEDATE))),
	    								(c.getString(c.getColumnIndex(KEY_ASSIGNMENT_URL)))	);
	 	    return a;
	    }
	    else
	    	return null;
	}
	
	public Attendance getAttendance(String attendanceID, String attedanceDate) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_ATTENDANCE + " WHERE "
	            + KEY_ATTENDANCE_ID + " = " + attendanceID + "AND" 
	    		+ KEY_ATTENDANCE_DATE + " = " + attedanceDate;
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	    {
	        c.moveToFirst();
	        Attendance a = new Attendance((c.getString(c.getColumnIndex(KEY_ATTENDANCE_ID))),
	    								(c.getString(c.getColumnIndex(KEY_ATTENDANCE_DATE))),
	    								(c.getInt(c.getColumnIndex(KEY_ATTENDANCE_PRESENT))));
	 	    return a;
	    }
	    else
	    	return null;
	}
	
	public Course getCourse(String courseID) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_COURSE + " WHERE "
	            + KEY_COURSE_ID + " = " + courseID;
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	    {
	        c.moveToFirst();
	        Course course = new Course((c.getString(c.getColumnIndex(KEY_COURSE_ID))),
	    						(c.getString(c.getColumnIndex(KEY_COURSE_NAME))),
	    						(c.getString(c.getColumnIndex(KEY_COURSE_TYPE))),
	    						(c.getString(c.getColumnIndex(KEY_COURSE_DAYS))),
	    						(c.getString(c.getColumnIndex(KEY_COURSE_TIME))),	
	    						(c.getString(c.getColumnIndex(KEY_COURSE_LOCATION))),
	    						(c.getString(c.getColumnIndex(KEY_COURSE_SEMESTER))),
	    						(c.getString(c.getColumnIndex(KEY_COURSE_YEAR))),
	    						(c.getString(c.getColumnIndex(KEY_COURSE_INSTRUCTOR_ID))));
	 	    return course;
	    }
	    else
	    	return null;
	}
	
	public Instructor getInstructor(String instructorID) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_INSTRUCTOR + " WHERE "
	            + KEY_INSTRUCTOR_ID + " = " + instructorID;
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	    {
	        c.moveToFirst();
	        Instructor i = new Instructor((c.getString(c.getColumnIndex(KEY_INSTRUCTOR_ID))),
	    						(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_PASS))),
	    						(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_FNAME))),
	    						(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_LNAME))),
	    						(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_OFFICE_ROOM))),	
	    						(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_OFFICE_BUILDING))),
	    						(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_OFFICE_DAYTIME))),
	    						(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_EMAIL))));
	 	    return i;
	    }
	    else
	    	return null;
	}
	
	public StudentAssignment getStudentAssignment(String saID, String saName) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_STUDENT_ASSIGNMENT + " WHERE "
	            + KEY_STUDENT_ASSIGNMENT_ID + " = " + saID + "AND" 
	    		+ KEY_STUDENT_ASSIGNMENT_NAME + " = " + saName;
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	    {
	        c.moveToFirst();
	        StudentAssignment sa = new StudentAssignment((c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_ID))),
	    								(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_NAME))),
	    								(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_GRADE))),
	    								(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_COMMENT))));
	 	    return sa;
	    }
	    else
	    	return null;
	}
	
	public StudentCourse getStudentCourse(String scID) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_STUDENT_COURSE + " WHERE "
	            + KEY_STUDENT_COURSE_ID + " = " + scID;
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	    {
	        c.moveToFirst();
	        StudentCourse sc = new StudentCourse((c.getString(c.getColumnIndex(KEY_STUDENT_COURSE_ID))),
	    								(c.getString(c.getColumnIndex(KEY_STUDENT_COURSE_IDCOURSE))),
	    								(c.getString(c.getColumnIndex(KEY_STUDENT_COURSE_IDSTUDENT))),
	    								(c.getString(c.getColumnIndex(KEY_STUDENT_COURSE_FINALGRADE))));
	 	    return sc;
	    }
	    else
	    	return null;
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Student");
		db.execSQL("DROP TABLE IF EXISTS Instructor");
		db.execSQL("DROP TABLE IF EXISTS Course");
		db.execSQL("DROP TABLE IF EXISTS StudentCourse");
		db.execSQL("DROP TABLE IF EXISTS Assignment");
		db.execSQL("DROP TABLE IF EXISTS StudentAssignment");
		db.execSQL("DROP TABLE IF EXISTS Attendance");
		
		onCreate(db);
	}
	
	public static DatabaseHandler getInstance(Context context){
		if (mDatabase == null){
			mDatabase = new DatabaseHandler(context.getApplicationContext());
		}
		return mDatabase;
	}

}
