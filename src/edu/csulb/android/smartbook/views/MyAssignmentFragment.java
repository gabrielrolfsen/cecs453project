/**
 *  
 *
 * @author Hao Vo
 * @version 1.0
 * @since Dec 7, 2014
 */
package edu.csulb.android.smartbook.views;

import java.util.ArrayList;
import java.util.List;

import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.Assignment;
import edu.csulb.android.smartbook.models.Course;
import edu.csulb.android.smartbook.models.StudentAssignment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * MyAssignmentFragment
 * Fragment view to display all assignments from all enrolled courses 
 *
 * @author Hao Vo
 * @version 1.0
 * @since Dec 7, 2014
 */
public class MyAssignmentFragment extends Fragment implements OnItemClickListener{

	ListView list;
	String studentID;
	
	List<StudentAssignment> lstStuAssignment;
	ArrayAdapter<StudentAssignment> adapter;
	
	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState){
		
		final View view = inflater.inflate(R.layout.fragment_my_assignments,
				container, false);
		list = (ListView) view.findViewById(R.id.lstMyAssignments);
		final SharedPreferences pref = getActivity().getApplicationContext()
				.getSharedPreferences(LoginActivity.SESSION_PREF, 0);
		studentID = pref.getString(LoginActivity.USER_ID, "");
		
		final DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		Cursor c = databaseHandler.getAllCourseEnrollByStudent(studentID);
		lstStuAssignment = new ArrayList<StudentAssignment>();
		if (c.moveToFirst()){
			do {
				String cID = new String();
				cID = c.getString(c.getColumnIndex(DatabaseHandler.KEY_COURSE_ID));

				lstStuAssignment.addAll(databaseHandler.getAssignmentsUseCourseIDAndStudentID(studentID, cID));
			} while (c.moveToNext());
		}
		
		adapter = new ArrayAdapter<StudentAssignment>(getActivity(),
				android.R.layout.simple_list_item_2, android.R.id.text1, lstStuAssignment) {
			@Override
			public View getView(final int position, final View convertView,
					final ViewGroup parent) {
				final View view = super.getView(position, convertView, parent);
				final TextView txt1 = (TextView) view
						.findViewById(android.R.id.text1);
				final TextView txt2 = (TextView) view
						.findViewById(android.R.id.text2);
				txt1.setText(lstStuAssignment.get(position).getIdCourse() + " - " + lstStuAssignment.get(position).getaName());
				if (lstStuAssignment.get(position).getSaGrade() == null) {
					txt2.setText("Grade: N/A");
				} else {
					txt2.setText("Grade: " + lstStuAssignment.get(position).getSaGrade());
				}

				txt1.setTextColor(Color.BLACK);
				txt2.setTextColor(Color.BLACK);
				return view;
			}
		};
		
		if (list.getAdapter() == null) {
			list.setAdapter(adapter);
		}
		list.setOnItemClickListener(this);
		
		return view;
	}
	
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view,
			final int position, final long id) {

		final DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		
		final AssignmentFragment frag = new AssignmentFragment();
		frag.stuAssign = lstStuAssignment.get(position);
		frag.courseID = lstStuAssignment.get(position).getIdCourse();
		frag.courseName = databaseHandler.getCourse(frag.courseID).getcName();
		final FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

		transaction.replace(R.id.content_layout, frag);
		transaction.addToBackStack(null);
		transaction.commit();

	}
}
