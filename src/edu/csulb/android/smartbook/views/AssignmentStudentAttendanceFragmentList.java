/**
 *  
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 29, 2014
 */
package edu.csulb.android.smartbook.views;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.Assignment;
import edu.csulb.android.smartbook.models.Attendance;
import edu.csulb.android.smartbook.models.Student;
import edu.csulb.android.smartbook.models.StudentAssignment;
import edu.csulb.android.smartbook.models.StudentCourse;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * ClassAllAssignmentsFragment
 * <Description> 
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 29, 2014
 */
public class AssignmentStudentAttendanceFragmentList extends Fragment implements OnItemClickListener {

	ListView list;
	int lstType; 	//0 = Assignments, 1 = Student, 2 = Attendance
	String courseID;
	String courseName;
	String studentID;
	List<StudentAssignment> listSA;
	List<Student> listS;
	List<Attendance> listA;
	ArrayAdapter<?> adapter;
	TextView txtListTitle;
	TextView txtCourseName;

	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState){
		

		View view = inflater.inflate(R.layout.fragment_list_layout,
				container, false);
		list = (ListView) view.findViewById(R.id.lstFragmentListLayout);
		txtListTitle = (TextView) view.findViewById(R.id.txtFragmentListLayoutTitle);
		txtCourseName = (TextView) view.findViewById(R.id.txtFragmentListLayoutCourseName);
		Bundle args = getArguments();
		courseID = args.getString(DatabaseHandler.KEY_COURSE_ID);
		courseName = args.getString(DatabaseHandler.KEY_COURSE_NAME);
		lstType = args.getInt(ClassViewFragment.LIST_TYPE);
		
		final SharedPreferences pref = getActivity()
				.getApplicationContext().getSharedPreferences(
						LoginActivity.SESSION_PREF, 0);
		studentID = pref.getString(LoginActivity.USER_ID, "");
		
		switch (lstType){
			case ClassViewFragment.TYPE_ASSIGNMENT:
				txtListTitle.setText(courseID + " - Assignment");
				txtCourseName.setText(courseName);
				getAssignmentList(studentID, courseID);
				break;
			case ClassViewFragment.TYPE_STUDENT:
				txtListTitle.setText(courseID + " - Student");
				txtCourseName.setText(courseName);
				getAssignmentList(studentID, courseID);
				getStudentList(courseID);
				break;
			case ClassViewFragment.TYPE_ATTENDANCE:
				txtListTitle.setText(courseID + " - Attendace");
				txtCourseName.setText(courseName);
				getAttendanceList(studentID, courseID);
				break;
		}

		if(list.getAdapter() == null)
			list.setAdapter(adapter);
		
		list.setOnItemClickListener(this);
		
		return view;
		
	}
	
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (lstType){
			case ClassViewFragment.TYPE_ASSIGNMENT:
			
				AssignmentFragment frag = new AssignmentFragment();
				frag.stuAssign = listSA.get(position);
				frag.courseID = courseID;
				frag.courseName = courseName;
				
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				transaction.replace(R.id.content_layout, frag);
				transaction.addToBackStack(null);
				transaction.commit();
				break;
				
			default:
				break;
		}
		
	}
	
	private void getAssignmentList(String sID, String cID) {
		DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		listSA = databaseHandler.getAssignmentsUseCourseIDAndStudentID(sID, cID);
		
		
		adapter = new ArrayAdapter<StudentAssignment>(getActivity(),android.R.layout.simple_list_item_2,android.R.id.text1,listSA) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView txt1 = (TextView) view.findViewById(android.R.id.text1);
				TextView txt2 = (TextView) view.findViewById(android.R.id.text2);
				txt1.setText(listSA.get(position).getaName());
				if (listSA.get(position).getSaGrade() == null)
					txt2.setText("Grade: N/A" );	
				else
					txt2.setText("Grade: " + listSA.get(position).getSaGrade());	

				return view;
			}
		};
	}
	
	private void getStudentList(String cID) {
		DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		listS = databaseHandler.getStudentsInCourse(cID);
		
		adapter = new ArrayAdapter<Student>(getActivity(),android.R.layout.simple_list_item_2,android.R.id.text1,listS) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView txt1 = (TextView) view.findViewById(android.R.id.text1);
				TextView txt2 = (TextView) view.findViewById(android.R.id.text2);
				txt1.setText(listS.get(position).getsLastName() + ", " + listS.get(position).getsFirstName());
				if (listS.get(position).getsEmail() == null)
					txt2.setText("Email: N/A" );	
				else
					txt2.setText("Email: " + listS.get(position).getsEmail());	
				
				return view;
			}
		};
	}
	
	private void getAttendanceList(String sID, String cID) {
		DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		listA = databaseHandler.getStudentAttendanceInCourse(sID, cID);
		
		adapter = new ArrayAdapter<Attendance>(getActivity(),android.R.layout.simple_list_item_2,android.R.id.text1,listA) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView txt1 = (TextView) view.findViewById(android.R.id.text1);
				TextView txt2 = (TextView) view.findViewById(android.R.id.text2);
				txt1.setText("Date:" + listA.get(position).getaDate());
				if(listA.get(position).getaPresent() == 2)
					txt2.setText("Present: YES" );	
				else if (listA.get(position).getaPresent() == 1)
					txt2.setText("Present: NO" );	
				else if (listA.get(position).getaPresent() == 0)
					txt2.setText("Present: N/A");
				
				return view;
			}
		};
	}

}
