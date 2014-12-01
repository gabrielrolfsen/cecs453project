package edu.csulb.android.smartbook.views;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;

/**
 * ClassViewFragment: Shows the info about determined class, info is gathered in
 * the database by giving the class ID.
 * 
 * Hao Vo - implement buttons functions, fix layout xml,
 *
 * @author Gabriel Franzoni
 * @author Hao Vo
 * @version 1.0
 * @since Nov 19, 2014
 */
public class ClassViewFragment extends Fragment implements OnClickListener {

	public static final String LIST_TYPE = "listType";
	public static final int TYPE_ASSIGNMENT = 0;
	public static final int TYPE_STUDENT = 1;
	public static final int TYPE_ATTENDANCE = 2;

	String mClassCode;
	TextView className;
	TextView classCode;
	TextView profName;
	TextView profContact;
	TextView profDep;
	ImageView profImg;
	TextView classInfo;

	Button btnAllAssignments;
	Button btnStudent;
	Button btnAttendance;

	/**
	 * @param classCode
	 *            class Unique ID that is used to query data from the database.
	 */
	public ClassViewFragment(final String classCode) {
		this.mClassCode = classCode;
	}

	public ClassViewFragment() {

	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_class_view,
				container, false);
		className = (TextView) view.findViewById(R.id.txtClassName);
		classCode = (TextView) view.findViewById(R.id.txtClassCode);
		profName = (TextView) view.findViewById(R.id.txtProfName);
		profContact = (TextView) view.findViewById(R.id.txtProfContact);
		profDep = (TextView) view.findViewById(R.id.txtProfDep);
		classInfo = (TextView) view.findViewById(R.id.txtSectionInfo);
		profImg = (ImageView) view.findViewById(R.id.imgProfessor);
		queryData();

		btnAllAssignments = (Button) view.findViewById(R.id.btnAssignmentList);
		btnAllAssignments.setOnClickListener(this);

		btnStudent = (Button) view.findViewById(R.id.btnStudentList);
		btnStudent.setOnClickListener(this);

		btnAttendance = (Button) view.findViewById(R.id.btnAttendanceList);
		btnAttendance.setOnClickListener(this);
		return view;
	}

	public void queryData() {

		final DatabaseHandler db = DatabaseHandler.getInstance(getActivity());
		final Cursor c = db.getCourseAllInfo(mClassCode);

		if (c.getCount() > 0) {
			if (c.moveToFirst()) {
				className.setText(c.getString(c
						.getColumnIndex(DatabaseHandler.KEY_COURSE_NAME)));
				classCode.setText(c.getString(c
						.getColumnIndex(DatabaseHandler.KEY_COURSE_ID)));
				profName.setText(c.getString(c
						.getColumnIndex(DatabaseHandler.KEY_INSTRUCTOR_FNAME)));
				profContact.setText(c.getString(c
						.getColumnIndex(DatabaseHandler.KEY_INSTRUCTOR_EMAIL)));
				profDep.setText(c.getString(c
						.getColumnIndex(DatabaseHandler.KEY_INSTRUCTOR_OFFICE_BUILDING)));

			}
		}

	}

	/**
	 * @author: Hao Vo
	 */
	@Override
	public void onClick(final View v) {

		final AssignmentStudentAttendanceFragmentList frag = new AssignmentStudentAttendanceFragmentList();
		final Bundle args = new Bundle();
		args.putString(DatabaseHandler.KEY_COURSE_ID,
				(String) classCode.getText());
		args.putString(DatabaseHandler.KEY_COURSE_NAME,
				(String) className.getText());
		switch (v.getId()) {
		case R.id.btnAssignmentList:
			args.putInt(LIST_TYPE, TYPE_ASSIGNMENT);
			break;
		case R.id.btnStudentList:
			args.putInt(LIST_TYPE, TYPE_STUDENT);
			break;
		case R.id.btnAttendanceList:
			args.putInt(LIST_TYPE, TYPE_ATTENDANCE);
			break;
		}
		frag.setArguments(args);

		final FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack so the user can navigate
		// back
		transaction.replace(R.id.content_layout, frag);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

}
