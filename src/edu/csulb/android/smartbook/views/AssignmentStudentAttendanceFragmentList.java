/**
 *  
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 29, 2014
 */
package edu.csulb.android.smartbook.views;

import java.util.List;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.Attendance;
import edu.csulb.android.smartbook.models.Student;
import edu.csulb.android.smartbook.models.StudentAssignment;

/**
 * ClassAllAssignmentsFragment <Description>
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 29, 2014
 */
public class AssignmentStudentAttendanceFragmentList extends Fragment implements
		OnItemClickListener {

	ListView list;
	int lstType; // 0 = Assignments, 1 = Student, 2 = Attendance
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
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list_layout,
				container, false);
		list = (ListView) view.findViewById(R.id.lstFragmentListLayout);
		txtListTitle = (TextView) view
				.findViewById(R.id.txtFragmentListLayoutTitle);
		txtCourseName = (TextView) view
				.findViewById(R.id.txtFragmentListLayoutCourseName);
		final Bundle args = getArguments();
		courseID = args.getString(DatabaseHandler.KEY_COURSE_ID);
		courseName = args.getString(DatabaseHandler.KEY_COURSE_NAME);
		lstType = args.getInt(ClassViewFragment.LIST_TYPE);
		// Re-do it in XML
		txtListTitle.setTextColor((Color.parseColor("#000000")));
		txtCourseName.setTextColor((Color.parseColor("#000000")));

		final SharedPreferences pref = getActivity().getApplicationContext()
				.getSharedPreferences(LoginActivity.SESSION_PREF, 0);
		studentID = pref.getString(LoginActivity.USER_ID, "");

		switch (lstType) {
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
			txtListTitle.setText(courseID + " - Attendance");
			txtCourseName.setText(courseName);
			getAttendanceList(studentID, courseID);
			break;
		}

		if (list.getAdapter() == null) {
			list.setAdapter(adapter);
		}

		list.setOnItemClickListener(this);

		return view;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view,
			final int position, final long id) {
		switch (lstType) {
		case ClassViewFragment.TYPE_ASSIGNMENT:

			final AssignmentFragment frag = new AssignmentFragment();
			frag.stuAssign = listSA.get(position);
			frag.courseID = courseID;
			frag.courseName = courseName;

			final FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

			transaction.replace(R.id.content_layout, frag);
			transaction.addToBackStack(null);
			transaction.commit();
			break;

		default:
			break;
		}

	}

	private void getAssignmentList(final String sID, final String cID) {
		final DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		listSA = databaseHandler
				.getAssignmentsUseCourseIDAndStudentID(sID, cID);

		adapter = new ArrayAdapter<StudentAssignment>(getActivity(),
				android.R.layout.simple_list_item_2, android.R.id.text1, listSA) {
			@Override
			public View getView(final int position, final View convertView,
					final ViewGroup parent) {
				final View view = super.getView(position, convertView, parent);
				final TextView txt1 = (TextView) view
						.findViewById(android.R.id.text1);
				final TextView txt2 = (TextView) view
						.findViewById(android.R.id.text2);
				txt1.setText(listSA.get(position).getaName());
				if (listSA.get(position).getSaGrade() == null) {
					txt2.setText("Grade: N/A");
				} else {
					txt2.setText("Grade: " + listSA.get(position).getSaGrade());
				}

				return view;
			}
		};
	}

	private void getStudentList(final String cID) {
		final DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		listS = databaseHandler.getStudentsInCourse(cID);

		adapter = new ArrayAdapter<Student>(getActivity(),
				android.R.layout.simple_list_item_2, android.R.id.text1, listS) {
			@Override
			public View getView(final int position, final View convertView,
					final ViewGroup parent) {
				final View view = super.getView(position, convertView, parent);
				final TextView txt1 = (TextView) view
						.findViewById(android.R.id.text1);
				final TextView txt2 = (TextView) view
						.findViewById(android.R.id.text2);
				txt1.setText(listS.get(position).getsLastName() + ", "
						+ listS.get(position).getsFirstName());
				if (listS.get(position).getsEmail() == null) {
					txt2.setText("Email: N/A");
				} else {
					txt2.setText("Email: " + listS.get(position).getsEmail());
				}

				return view;
			}
		};
	}

	private void getAttendanceList(final String sID, final String cID) {
		final DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		listA = databaseHandler.getStudentAttendanceInCourse(sID, cID);

		adapter = new ArrayAdapter<Attendance>(getActivity(),
				android.R.layout.simple_list_item_2, android.R.id.text1, listA) {
			@Override
			public View getView(final int position, final View convertView,
					final ViewGroup parent) {
				final View view = super.getView(position, convertView, parent);
				final TextView txt1 = (TextView) view
						.findViewById(android.R.id.text1);
				final TextView txt2 = (TextView) view
						.findViewById(android.R.id.text2);
				txt1.setText("Date:" + listA.get(position).getaDate());
				if (listA.get(position).getaPresent() == 2) {
					txt2.setText("Present: YES");
				} else if (listA.get(position).getaPresent() == 1) {
					txt2.setText("Present: NO");
				} else if (listA.get(position).getaPresent() == 0) {
					txt2.setText("Present: N/A");
				}

				return view;
			}
		};
	}

}
