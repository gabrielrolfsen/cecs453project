
package edu.csulb.android.smartbook.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.csulb.android.smartbook.models.Assignment;
import edu.csulb.android.smartbook.models.Attendance;
import edu.csulb.android.smartbook.models.Course;
import edu.csulb.android.smartbook.models.Instructor;
import edu.csulb.android.smartbook.models.Student;
import edu.csulb.android.smartbook.models.StudentAssignment;
import edu.csulb.android.smartbook.models.StudentCourse;
/**
 * 
 * DatabaseHandler :Handle create sample database with sample data to use in the project
 *  
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 19, 2014
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	private static DatabaseHandler mDatabase;

	// Database version and name
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "projectDemoDatabase";

	// Tables Names
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
	public static final String KEY_STUDENT_MAJOR = "sMajor";

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
	public static final String KEY_STUDENT_COURSE_IDSTUDENT = "IdStudent";
	public static final String KEY_STUDENT_COURSE_IDCOURSE = "IdCourse";
	public static final String KEY_STUDENT_COURSE_FINALGRADE = "scFinalGrade";

	// Column names for table Attendance
	public static final String KEY_ATTENDANCE_IDSTUDENT = "IdStudent";
	public static final String KEY_ATTENDANCE_IDCOURSE = "IdCourse";
	public static final String KEY_ATTENDANCE_DATE = "aDate";
	public static final String KEY_ATTENDANCE_PRESENT = "aPresent";

	// Column names for table Assignment
	public static final String KEY_ASSIGNMENT_ID = "IdCourse";
	public static final String KEY_ASSIGNMENT_NAME = "aName";
	public static final String KEY_ASSIGNMENT_DESCRIPTION = "aDescription";
	public static final String KEY_ASSIGNMENT_DUEDATE = "aDueDate";
	public static final String KEY_ASSIGNMENT_URL = "aURL";

	// Column names for table StudentAssignment
	public static final String KEY_STUDENT_ASSIGNMENT_IDSTUDENT = "IdStudent";
	public static final String KEY_STUDENT_ASSIGNMENT_IDCOURSE = "IdCourse";
	public static final String KEY_STUDENT_ASSIGNMENT_NAME = "aName";
	public static final String KEY_STUDENT_ASSIGNMENT_GRADE = "saGrade";
	public static final String KEY_STUDENT_ASSIGNMENT_COMMENT = "saInstructorComment";

	// ///////////////// Create statement for all tables
	// ////////////////////////////////
	// Create table Student
	private static final String CREATE_TABLE_STUDENT = "CREATE TABLE "
			+ TABLE_STUDENT + " (" + KEY_STUDENT_ID
			+ " TEXT PRIMARY KEY NOT NULL," + KEY_STUDENT_PASS
			+ " TEXT NOT NULL," + KEY_STUDENT_FNAME + " TEXT NOT NULL,"
			+ KEY_STUDENT_LNAME + " TEXT," + KEY_STUDENT_BIRTH + " TEXT,"
			+ KEY_STUDENT_MAIL + " TEXT," + KEY_STUDENT_PHONE + " TEXT,"
			+ KEY_STUDENT_MAJOR + " TEXT) ";
	// Create table Instructor
	private static final String CREATE_TABLE_INSTRUCTOR = "CREATE TABLE "
			+ TABLE_INSTRUCTOR + " (" + KEY_INSTRUCTOR_ID
			+ " TEXT PRIMARY KEY," + KEY_INSTRUCTOR_PASS + " TEXT,"
			+ KEY_INSTRUCTOR_FNAME + " TEXT," + KEY_INSTRUCTOR_LNAME + " TEXT,"
			+ KEY_INSTRUCTOR_OFFICE_ROOM + " TEXT,"
			+ KEY_INSTRUCTOR_OFFICE_BUILDING + " TEXT,"
			+ KEY_INSTRUCTOR_OFFICE_DAYTIME + " TEXT," + KEY_INSTRUCTOR_EMAIL
			+ " TEXT) ";
	// Create table Course
	private static final String CREATE_TABLE_COURSE = "CREATE TABLE "
			+ TABLE_COURSE + " (" + KEY_COURSE_ID + " TEXT PRIMARY KEY,"
			+ KEY_COURSE_NAME + " TEXT," + KEY_COURSE_TYPE + " TEXT,"
			+ KEY_COURSE_DAYS + " TEXT," + KEY_COURSE_TIME + " TEXT,"
			+ KEY_COURSE_LOCATION + " TEXT," + KEY_COURSE_SEMESTER + " TEXT,"
			+ KEY_COURSE_YEAR + " TEXT," + KEY_COURSE_INSTRUCTOR_ID + " TEXT,"
			+ "FOREIGN KEY (" + KEY_COURSE_INSTRUCTOR_ID + ") REFERENCES "
			+ TABLE_INSTRUCTOR + "(" + KEY_INSTRUCTOR_ID + "))";
	// Create table StudentCourse
	private static final String CREATE_TABLE_STUDENT_COURSE = "CREATE TABLE "
			+ TABLE_STUDENT_COURSE + " (" + KEY_STUDENT_COURSE_IDSTUDENT
			+ " TEXT NOT NULL," + KEY_STUDENT_COURSE_IDCOURSE
			+ " TEXT NOT NULL," + KEY_STUDENT_COURSE_FINALGRADE + " TEXT,"
			+ "FOREIGN KEY (" + KEY_STUDENT_COURSE_IDSTUDENT + ") REFERENCES "
			+ TABLE_STUDENT + "(" + KEY_STUDENT_ID + ")," + "FOREIGN KEY ("
			+ KEY_STUDENT_COURSE_IDCOURSE + ") REFERENCES " + TABLE_COURSE
			+ "(" + KEY_COURSE_ID + ")," + "PRIMARY KEY ("
			+ KEY_STUDENT_COURSE_IDCOURSE + "," + KEY_STUDENT_COURSE_IDSTUDENT
			+ "))";
	// Create table Attendance
	private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE "
			+ TABLE_ATTENDANCE + " (" + KEY_ATTENDANCE_IDSTUDENT
			+ " TEXT NOT NULL,"
			+ KEY_ATTENDANCE_IDCOURSE
			+ " TEXT NOT NULL,"
			+ KEY_ATTENDANCE_DATE
			+ " TEXT NOT NULL,"
			+ KEY_ATTENDANCE_PRESENT
			+ " INT,"
			+ // int type, 1 = true, 0 = false,
			"PRIMARY KEY (" + KEY_ATTENDANCE_IDSTUDENT + ","
			+ KEY_ATTENDANCE_IDCOURSE + "," + KEY_ATTENDANCE_DATE + "),"
			+ "FOREIGN KEY (" + KEY_ATTENDANCE_IDSTUDENT + ","
			+ KEY_ATTENDANCE_IDCOURSE + ") REFERENCES " + TABLE_STUDENT_COURSE
			+ "(" + KEY_STUDENT_COURSE_IDSTUDENT + ","
			+ KEY_STUDENT_COURSE_IDCOURSE + "))";

	// Create table Assignment
	private static final String CREATE_TABLE_ASSIGNMENT = "CREATE TABLE "
			+ TABLE_ASSIGNMENT + " (" + KEY_ASSIGNMENT_ID + " TEXT NOT NULL,"
			+ KEY_ASSIGNMENT_NAME + " TEXT NOT NULL,"
			+ KEY_ASSIGNMENT_DESCRIPTION + " TEXT," + KEY_ASSIGNMENT_DUEDATE
			+ " TEXT," + KEY_ASSIGNMENT_URL + " TEXT," + "PRIMARY KEY ("
			+ KEY_ASSIGNMENT_ID + "," + KEY_ASSIGNMENT_NAME + "),"
			+ "FOREIGN KEY (" + KEY_ASSIGNMENT_ID + ") REFERENCES "
			+ TABLE_COURSE + "(" + KEY_COURSE_ID + "))";
	// Create table student assignment
	private static final String CREATE_TABLE_STUDENT_ASSIGNMENT = "CREATE TABLE "
			+ TABLE_STUDENT_ASSIGNMENT
			+ " ("
			+ KEY_STUDENT_ASSIGNMENT_IDSTUDENT
			+ " TEXT NOT NULL,"
			+ KEY_STUDENT_ASSIGNMENT_IDCOURSE
			+ " TEXT NOT NULL,"
			+ KEY_STUDENT_ASSIGNMENT_NAME
			+ " TEXT NOT NULL,"
			+ KEY_STUDENT_ASSIGNMENT_GRADE
			+ " TEXT,"
			+ KEY_STUDENT_ASSIGNMENT_COMMENT
			+ " TEXT,"
			+ "PRIMARY KEY ("
			+ KEY_STUDENT_ASSIGNMENT_IDSTUDENT
			+ ","
			+ KEY_STUDENT_ASSIGNMENT_IDCOURSE
			+ ","
			+ KEY_STUDENT_ASSIGNMENT_NAME
			+ "),"
			+ "FOREIGN KEY ("
			+ KEY_STUDENT_ASSIGNMENT_NAME
			+ ") REFERENCES "
			+ TABLE_ASSIGNMENT
			+ "("
			+ KEY_ASSIGNMENT_NAME
			+ "),"
			+ "FOREIGN KEY ("
			+ KEY_ATTENDANCE_IDSTUDENT
			+ ","
			+ KEY_ATTENDANCE_IDCOURSE
			+ ") REFERENCES "
			+ TABLE_STUDENT_COURSE
			+ "("
			+ KEY_STUDENT_COURSE_IDSTUDENT
			+ ","
			+ KEY_STUDENT_COURSE_IDCOURSE
			+ "))";

	private DatabaseHandler(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		// Code to enable foreign key constraints but not work
		/*
		 * if (!db.isReadOnly()) { // Enable foreign key constraints
		 * db.execSQL("PRAGMA foreign_keys=ON;"); Log.d("Foreign keys",
		 * "enable"); }
		 */

		Log.d("Create table", "Creating..."); // log for debug

		db.execSQL(CREATE_TABLE_STUDENT);
		db.execSQL(CREATE_TABLE_INSTRUCTOR);
		db.execSQL(CREATE_TABLE_COURSE);
		db.execSQL(CREATE_TABLE_STUDENT_COURSE);
		db.execSQL(CREATE_TABLE_ASSIGNMENT);
		db.execSQL(CREATE_TABLE_STUDENT_ASSIGNMENT);
		db.execSQL(CREATE_TABLE_ATTENDANCE);
		Log.d("Create table", "Done"); // log for debug

		// /////// Sql statements to insert sample data ///////////
		Log.d("Insert to student", "Inserting...");
		// Student table, insert demo data
		db.execSQL("INSERT INTO Student VALUES ('1111','1234','John','Kane','01/01/1963','john.kane@csulb.edu','1205434521','Computer Science')");
		db.execSQL("INSERT INTO Student VALUES ('2222','1234','James','Nort','21/01/1983','James.Nort@csulb.edu','120854314','Computer Science')");
		db.execSQL("INSERT INTO Student VALUES ('3333','1234','Kate','Line','15/06/1993','Kate.Line@csulb.edu','1204513784','Computer Science')");
		db.execSQL("INSERT INTO Student VALUES ('4444','1234','TempF4','TempL4','24/08/1984','Temp4@csulb.edu','1201234575','Computer Engineering')");
		db.execSQL("INSERT INTO Student VALUES ('5555','1234','TempF5','TempL5','16/12/1973','Temp5@csulb.edu','1201357512','Computer Engineering')");
		db.execSQL("INSERT INTO Student VALUES ('6666','1234','TempF6','TempL6','09/07/1986','Temp6@csulb.edu','1201675123','Computer Engineering')");

		Log.d("Insert to Instructor", "Inserting...");
		// Instructor table, insert demo data
		db.execSQL("INSERT INTO Instructor VALUES ('E1111','1234','John Hopkins','InsLN1','502','ECS','MonWed 1-3 pm','Instructor1@csulb.edu')");
		db.execSQL("INSERT INTO Instructor VALUES ('E2222','1234','Albert Einstein','InsLN2','531','ECS','Thurday 4-6 pm','')");
		db.execSQL("INSERT INTO Instructor VALUES ('E3333','1234','Erwin Schrödinger','InsLN3','','','','')");

		Log.d("Insert to Course", "Inserting...");
		// Course table, insert demo data
		db.execSQL("INSERT INTO Course VALUES ('CECS 521','Database','SEM','Mon Wed','1-1:50 pm','ECS 308','Fall', '2014','E1111')");
		db.execSQL("INSERT INTO Course VALUES ('CECS 200','Web Development','SEM','Mon Wed','4-4:50 pm','ECS 311','Fall', '2014','E1111')");
		db.execSQL("INSERT INTO Course VALUES ('CECS 453','Mobile Development','SEM','Tue Thu','3-3:50pm','ECS 302','Fall', '2014','E2222')");
		db.execSQL("INSERT INTO Course VALUES ('CECS 475','Graphics Programming','SEM','Tue Thu','1-1:50 pm','ECS 308','Fall', '2014',NULL)");

		Log.d("Insert to StudentCourse", "Inserting...");
		// Course table, insert demo data
		db.execSQL("INSERT INTO StudentCourse VALUES ('1111','CECS 521',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('1111','CECS 200',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('1111','CECS 453',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('1111','CECS 475',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('2222','CECS 521',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('2222','CECS 453',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('2222','CECS 475',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('3333','CECS 521',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('3333','CECS 200',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('4444','CECS 521',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('4444','CECS 475',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('5555','CECS 521',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('5555','CECS 200',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('5555','CECS 453',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('6666','CECS 521',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('6666','CECS 200',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('6666','CECS 453',NULL)");
		db.execSQL("INSERT INTO StudentCourse VALUES ('6666','CECS 475',NULL)");

		Log.d("Insert to Attendance", "Inserting...");
		// Attendance table, insert demo data
		db.execSQL("INSERT INTO Attendance VALUES ('1111','CECS 521','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('1111','CECS 200','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('1111','CECS 453','09/11/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('1111','CECS 475','09/11/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('2222','CECS 521','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('2222','CECS 453','09/11/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('2222','CECS 475','09/11/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('3333','CECS 521','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('3333','CECS 200','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('4444','CECS 521','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('4444','CECS 475','09/11/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('5555','CECS 521','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('5555','CECS 200','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('5555','CECS 453','09/11/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('6666','CECS 521','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('6666','CECS 200','09/10/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('6666','CECS 453','09/11/2014',NULL)");
		db.execSQL("INSERT INTO Attendance VALUES ('6666','CECS 475','09/11/2014',NULL)");

		Log.d("Insert to Assignment", "Inserting...");
		// Assignment table, insert demo data
		db.execSQL("INSERT INTO Assignment VALUES ('CECS 521','Assignment 1',NULL,NULL,NULL)");
		db.execSQL("INSERT INTO Assignment VALUES ('CECS 521','Assignment 2',NULL,NULL,NULL)");
		db.execSQL("INSERT INTO Assignment VALUES ('CECS 200','Assignment 1',NULL,NULL,NULL)");
		db.execSQL("INSERT INTO Assignment VALUES ('CECS 453','Assignment 1','This is assignment 1','09/20/2014',NULL)");
		db.execSQL("INSERT INTO Assignment VALUES ('CECS 453','Assignment 2','This is assignment 2','10/20/2014',NULL)");
		db.execSQL("INSERT INTO Assignment VALUES ('CECS 453','Assignment 3',NULL,NULL,NULL)");
		
		Log.d("Insert to StudentAssignment", "Inserting...");
		// StudentAssignment table, insert demo data
		db.execSQL("INSERT INTO StudentAssignment VALUES ('1111','CECS 521','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('2222','CECS 521','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('3333','CECS 521','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('4444','CECS 521','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('5555','CECS 521','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('1111','CECS 453','Assignment 1','A',NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('2222','CECS 453','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('3333','CECS 453','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('4444','CECS 453','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('5555','CECS 453','Assignment 1',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('1111','CECS 453','Assignment 2',NULL,NULL)");
		db.execSQL("INSERT INTO StudentAssignment VALUES ('1111','CECS 453','Assignment 3',NULL,NULL)");

		Log.d("Insert data", "Done");

	}

	// ///////////// query a row base on primary
	// key/////////////////////////////////////////////////////////////
	public Student getStudent(final String student_id) {
		final SQLiteDatabase db = mDatabase.getReadableDatabase();

		final String selectQuery = "SELECT * FROM " + TABLE_STUDENT + " WHERE "
				+ KEY_STUDENT_ID + " = \"" + student_id + "\"";

		final Cursor c = db.rawQuery(selectQuery, null);
		Student st = null;

		if (c.getCount() > 0) {
			c.moveToFirst();
			st = new Student(c.getString(c.getColumnIndex(KEY_STUDENT_ID)),
					c.getString(c.getColumnIndex(KEY_STUDENT_PASS)),
					c.getString(c.getColumnIndex(KEY_STUDENT_FNAME)),
					c.getString(c.getColumnIndex(KEY_STUDENT_LNAME)),
					c.getString(c.getColumnIndex(KEY_STUDENT_BIRTH)),
					c.getString(c.getColumnIndex(KEY_STUDENT_MAIL)),
					c.getString(c.getColumnIndex(KEY_STUDENT_PHONE)),
					c.getString(c.getColumnIndex(KEY_STUDENT_MAJOR)));
			return st;
		} else {
			return null;
		}
	}

	public Assignment getAssignment(final String assignmentID,
			final String assignmentName) {
		final SQLiteDatabase db = this.getReadableDatabase();

		final String selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENT
				+ " WHERE " + KEY_ASSIGNMENT_ID + " = " + "'" + assignmentID + "'"
				+ " AND " + KEY_ASSIGNMENT_NAME + " = " + "'" + assignmentName + "'";

		final Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			c.moveToFirst();
			final Assignment a = new Assignment((c.getString(c
					.getColumnIndex(KEY_ASSIGNMENT_ID))), (c.getString(c
					.getColumnIndex(KEY_ASSIGNMENT_NAME))), (c.getString(c
					.getColumnIndex(KEY_ASSIGNMENT_DESCRIPTION))),
					(c.getString(c.getColumnIndex(KEY_ASSIGNMENT_DUEDATE))),
					(c.getString(c.getColumnIndex(KEY_ASSIGNMENT_URL))));
			return a;
		} else {
			return null;
		}
	}

	public Attendance getAttendance(final String studentID,
			final String courseID, final String attedanceDate) {
		final SQLiteDatabase db = this.getReadableDatabase();

		final String selectQuery = "SELECT  * FROM " + TABLE_ATTENDANCE
				+ " WHERE " + KEY_ATTENDANCE_IDSTUDENT + " = " + "'" + studentID + "'" 
				+ " AND " + KEY_ATTENDANCE_IDCOURSE + " = " + "'" + courseID + "'" 
				+ " AND " + KEY_ATTENDANCE_DATE + " = " + "'" + attedanceDate + "'";

		final Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			c.moveToFirst();
			final Attendance a = new Attendance((c.getString(c
					.getColumnIndex(KEY_ATTENDANCE_IDSTUDENT))), (c.getString(c
					.getColumnIndex(KEY_ATTENDANCE_IDCOURSE))), (c.getString(c
					.getColumnIndex(KEY_ATTENDANCE_DATE))), (c.getInt(c
					.getColumnIndex(KEY_ATTENDANCE_PRESENT))));
			return a;
		} else {
			return null;
		}
	}

	public Course getCourse(final String courseID) {
		final SQLiteDatabase db = this.getReadableDatabase();

		final String selectQuery = "SELECT  * FROM " + TABLE_COURSE + " WHERE "
				+ KEY_COURSE_ID + " = " + "'" + courseID + "'";

		final Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			c.moveToFirst();
			final Course course = new Course((c.getString(c
					.getColumnIndex(KEY_COURSE_ID))), (c.getString(c
					.getColumnIndex(KEY_COURSE_NAME))), (c.getString(c
					.getColumnIndex(KEY_COURSE_TYPE))), (c.getString(c
					.getColumnIndex(KEY_COURSE_DAYS))), (c.getString(c
					.getColumnIndex(KEY_COURSE_TIME))), (c.getString(c
					.getColumnIndex(KEY_COURSE_LOCATION))), (c.getString(c
					.getColumnIndex(KEY_COURSE_SEMESTER))), (c.getString(c
					.getColumnIndex(KEY_COURSE_YEAR))), (c.getString(c
					.getColumnIndex(KEY_COURSE_INSTRUCTOR_ID))));
			return course;
		} else {
			return null;
		}
	}

	public Instructor getInstructor(final String instructorID) {
		final SQLiteDatabase db = this.getReadableDatabase();

		final String selectQuery = "SELECT  * FROM " + TABLE_INSTRUCTOR
				+ " WHERE " + KEY_INSTRUCTOR_ID + " = " + "'" + instructorID + "'";

		final Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			c.moveToFirst();
			final Instructor i = new Instructor((c.getString(c
					.getColumnIndex(KEY_INSTRUCTOR_ID))), (c.getString(c
					.getColumnIndex(KEY_INSTRUCTOR_PASS))), (c.getString(c
					.getColumnIndex(KEY_INSTRUCTOR_FNAME))), (c.getString(c
					.getColumnIndex(KEY_INSTRUCTOR_LNAME))), (c.getString(c
					.getColumnIndex(KEY_INSTRUCTOR_OFFICE_ROOM))),
					(c.getString(c
							.getColumnIndex(KEY_INSTRUCTOR_OFFICE_BUILDING))),
					(c.getString(c
							.getColumnIndex(KEY_INSTRUCTOR_OFFICE_DAYTIME))),
					(c.getString(c.getColumnIndex(KEY_INSTRUCTOR_EMAIL))));
			return i;
		} else {
			return null;
		}
	}

	public StudentAssignment getStudentAssignment(final String studentID,
			final String courseID, final String saName) {
		final SQLiteDatabase db = this.getReadableDatabase();

		final String selectQuery = "SELECT  * FROM " + TABLE_STUDENT_ASSIGNMENT
				+ " WHERE " + KEY_STUDENT_ASSIGNMENT_IDSTUDENT + " = "
				+ "'" + studentID + "'" + " AND " + KEY_STUDENT_ASSIGNMENT_IDCOURSE + " = "
				+ "'" + courseID + "'" + " AND " + KEY_STUDENT_ASSIGNMENT_NAME + " = "
				+ "'" + saName;

		final Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			c.moveToFirst();
			final StudentAssignment sa = new StudentAssignment(
					(c.getString(c
							.getColumnIndex(KEY_STUDENT_ASSIGNMENT_IDSTUDENT))),
					(c.getString(c
							.getColumnIndex(KEY_STUDENT_ASSIGNMENT_IDCOURSE))),
					(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_NAME))),
					(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_GRADE))),
					(c.getString(c
							.getColumnIndex(KEY_STUDENT_ASSIGNMENT_COMMENT))));
			return sa;
		} else {
			return null;
		}
	}

	public StudentCourse getStudentCourse(final String studentID,
			final String courseID) {
		final SQLiteDatabase db = this.getReadableDatabase();

		final String selectQuery = "SELECT  * FROM " + TABLE_STUDENT_COURSE
				+ " WHERE " + KEY_STUDENT_COURSE_IDSTUDENT + " = " + "'" + studentID + "'"
				+ " AND " + KEY_STUDENT_COURSE_IDCOURSE + " = " + "'" + courseID + "'";

		final Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			c.moveToFirst();
			final StudentCourse sc = new StudentCourse(
					(c.getString(c.getColumnIndex(KEY_STUDENT_COURSE_IDSTUDENT))),
					(c.getString(c.getColumnIndex(KEY_STUDENT_COURSE_IDCOURSE))),
					(c.getString(c
							.getColumnIndex(KEY_STUDENT_COURSE_FINALGRADE))));
			return sc;
		} else {
			return null;
		}
	}

	// ///////////// Specific query that need for the project //////////////
	// get all info of a course include data from instructor table base on
	// IdCourse
	public Cursor getCourseAllInfo(final String courseID) {
		final SQLiteDatabase db = this.getReadableDatabase();
		final String selectQuery = "SELECT * FROM " + TABLE_COURSE
				+ " LEFT JOIN " + TABLE_INSTRUCTOR + " USING ("
				+ KEY_COURSE_INSTRUCTOR_ID + ")" + " WHERE " + KEY_COURSE_ID
				+ " = " + "'" + courseID + "'";

		final Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			return c;
		} else {
			return null;
		}
	}

	// get all course enrolled by a given studentID
	public Cursor getAllCourseEnrollByStudent(final String studentID) {
		final SQLiteDatabase db = this.getReadableDatabase();
		final String selectQuery = "SELECT * FROM " + TABLE_STUDENT_COURSE
				+ " NATURAL JOIN " + TABLE_COURSE + " WHERE "
				+ KEY_STUDENT_COURSE_IDSTUDENT + " = " + "'" + studentID + "'";
		final Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			return c;
		} else {
			return null;
		}
	}
	
	// get all assignments based on studentID and courseID from StudentAssignment table
	public List<StudentAssignment> getAssignmentsUseCourseIDAndStudentID( String studentID,  String courseID) {
		List<StudentAssignment> sas = new ArrayList<StudentAssignment>();
		final SQLiteDatabase db = this.getReadableDatabase();
		final String selectQuery = "SELECT * FROM " + TABLE_STUDENT_ASSIGNMENT
					+ " WHERE " + KEY_STUDENT_ASSIGNMENT_IDSTUDENT + " = " + "'" + studentID + "'"
					+ " AND " + KEY_STUDENT_ASSIGNMENT_IDCOURSE + " = " + "'" + courseID + "'";
		final Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				StudentAssignment sa = new StudentAssignment();
				sa.setIdStudent(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_IDSTUDENT)));
				sa.setIdCourse(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_IDCOURSE)));
				sa.setaName(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_NAME)));
				sa.setSaGrade(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_GRADE)));
				sa.setSaInstructorComment(c.getString(c.getColumnIndex(KEY_STUDENT_ASSIGNMENT_COMMENT)));
				
				sas.add(sa);
			} while (c.moveToNext());
		}	
		return sas;
	}
	
	public List<Student> getStudentsInCourse(final String courseID) {
		List<Student> students = new ArrayList<Student>();
		final SQLiteDatabase db = this.getReadableDatabase();
		final String selectQuery = "SELECT * FROM " + TABLE_STUDENT_COURSE
					+ " NATURAL JOIN " + TABLE_STUDENT 
					+ " WHERE " + KEY_STUDENT_COURSE_IDCOURSE + " = " + "'" + courseID + "'";
		final Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Student s = new Student();
				s.setIdStudent(c.getString(c.getColumnIndex(KEY_STUDENT_ID)));
//				s.setsPassword(c.getString(c.getColumnIndex(KEY_STUDENT_PASS)));
				s.setsFirstName(c.getString(c.getColumnIndex(KEY_STUDENT_FNAME)));
				s.setsLastName(c.getString(c.getColumnIndex(KEY_STUDENT_LNAME)));
//				s.setsDateOfBirth(c.getString(c.getColumnIndex(KEY_STUDENT_BIRTH)));
				s.setsEmail(c.getString(c.getColumnIndex(KEY_STUDENT_MAIL)));
//				s.setsPhone(c.getString(c.getColumnIndex(KEY_STUDENT_PHONE)));
				s.setsMajor(c.getString(c.getColumnIndex(KEY_STUDENT_MAJOR)));
				
				students.add(s);
			} while (c.moveToNext());
		}	
		return students;
	}
	
	public List<Attendance> getStudentAttendanceInCourse( String studentID,  String courseID) {
		List<Attendance> attendances = new ArrayList<Attendance>();
		final SQLiteDatabase db = this.getReadableDatabase();
		final String selectQuery = "SELECT * FROM " + TABLE_ATTENDANCE
				+ " WHERE " + KEY_STUDENT_ID + " = " + "'" + studentID + "'"
				+ " AND " + KEY_COURSE_ID + " = " + "'" + courseID + "'";
				
		final Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Attendance a = new Attendance((c.getString(c
						.getColumnIndex(KEY_ATTENDANCE_IDSTUDENT))), (c.getString(c
						.getColumnIndex(KEY_ATTENDANCE_IDCOURSE))), (c.getString(c
						.getColumnIndex(KEY_ATTENDANCE_DATE))), (c.getInt(c
						.getColumnIndex(KEY_ATTENDANCE_PRESENT))));

				Log.d("Attendance", a.getaDate());
				attendances.add(a);
			} while (c.moveToNext());
		}	
		return attendances;
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Student");
		db.execSQL("DROP TABLE IF EXISTS Instructor");
		db.execSQL("DROP TABLE IF EXISTS Course");
		db.execSQL("DROP TABLE IF EXISTS StudentCourse");
		db.execSQL("DROP TABLE IF EXISTS Assignment");
		db.execSQL("DROP TABLE IF EXISTS StudentAssignment");
		db.execSQL("DROP TABLE IF EXISTS Attendance");

		onCreate(db);
	}

	public static DatabaseHandler getInstance(final Context context) {
		if (mDatabase == null) {
			mDatabase = new DatabaseHandler(context.getApplicationContext());
		}
		return mDatabase;
	}

}