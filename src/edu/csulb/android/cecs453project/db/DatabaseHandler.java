/*
 * Class Name: DatabaseHandler.java
 * Description: Handle create sample database with sample data to use in the project
 * Author: Hao Vo
 */

package edu.csulb.android.cecs453project.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static DatabaseHandler mDatabase;
	public static final String TABLE_STUDENT = "Student";
	
	// Column Names
	public static final String KEY_STUDENT_ID = "IdStudent";
	public static final String KEY_STUDENT_PASS = "sPassword";
	public static final String KEY_STUDENT_FNAME = "sFirstName";
	public static final String KEY_STUDENT_LNAME = "sLastName";
	public static final String KEY_STUDENT_BIRTH = "sDateOfBirth";
	public static final String KEY_STUDENT_MAIL = "sEmail";
	public static final String KEY_STUDENT_PHONE = "sPhone";

	//Database version and name
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "projectDemoDatabase";

	//Create table Student
	private static final String CREATE_TABLE_STUDENT = "CREATE TABLE "+ TABLE_STUDENT +" (" +
														KEY_STUDENT_ID + " TEXT PRIMARY KEY NOT NULL," +
														KEY_STUDENT_PASS + " TEXT NOT NULL," +			
														KEY_STUDENT_FNAME + " TEXT NOT NULL," +
														KEY_STUDENT_LNAME + " TEXT," +
														KEY_STUDENT_BIRTH + " TEXT," +
														KEY_STUDENT_MAIL + " TEXT," +
														KEY_STUDENT_PHONE + " TEXT) ";
	//Create table Instructor
	private static final String CREATE_TABLE_INSTRUCTOR = "CREATE TABLE Instructor (" +
														"IdInstructor TEXT PRIMARY KEY," +
														"iPassword TEXT," +
														"iFirstName TEXT," +
														"iLastName TEXT," +
														"iOfficeRoom TEXT," +
														"iOfficeBuilding TEXT," +
														"iOfficeDayTime TEXT," +
														"iEmail TEXT) ";
	//Create table Course
	private static final String CREATE_TABLE_COURSE = "CREATE TABLE Course (" +
													"IdCourse TEXT PRIMARY KEY," +
													"cName TEXT, cType TEXT," +
													"cDays TEXT, cTime TEXT," +
													"cLocation TEXT, cSemester TEXT," +
													"cYear TEXT, IdInstructor TEXT," +
													"FOREIGN KEY (IdInstructor) REFERENCES Instructor(IdInstructor))";
	//Create table StudentCourse
	private static final String CREATE_TABLE_STUDENT_COURSE = "CREATE TABLE StudentCourse (" +
															"IdStudentCourse TEXT PRIMARY KEY," +
															"IdCourse TEXT NOT NULL," +
															"IdStudent TEXT NOT NULL," +
															"scFinalGrade TEXT," +
															"FOREIGN KEY(IdCourse) REFERENCES Course(IdCourse)," +
															"FOREIGN KEY(IdStudent) REFERENCES Student(IdStudent))";
	
	//Create table Attendance
	private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE Attendance (" +
														"IdStudentCourse TEXT NOT NULL," +
														"aDATE TEXT NOT NULL," +
														"aPresent BOOLEAN,"	+ //int type, 1 = true, 0 = false, 
														"PRIMARY KEY (IdStudentCourse, aDate)," +
														"FOREIGN KEY (IdStudentCourse) REFERENCES StudentCourse(IdStudentCourse))";
	//Create table Assignment
	private static final String CREATE_TABLE_ASSIGNMENT = "CREATE TABLE Assignment (" +
														"IdCourse TEXT NOT NULL," +
														"aName TEXT NOT NULL," +
														"aDescription TEXT NOT NULL," +
														"aDueDate TEXT," +
														"aURL TEXT," +
														"FOREIGN KEY (IdCourse) REFERENCES Course(IdCourse))";
	//Create table student assignment
	private static final String CREATE_TABLE_STUDENT_ASSIGNMENT = "CREATE TABLE StudentAssignment (" +
																"IdStudentCourse TEXT NOT NULL," +
																"aName TEXT NOT NULL," +
																"saGrade TEXT," +
																"saInstructorComment TEXT," +
																"PRIMARY KEY (IdStudentCourse, aName)," +
																"FOREIGN KEY (aName) REFERENCES Assignment(aName)," +
																"FOREIGN KEY (IdStudentCourse) REFERENCES StudentCourse(IdStudentCourse))";
	
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
							c.getString(c.getColumnIndex(KEY_STUDENT_PHONE))
							);	
		}
		
		return st;
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
