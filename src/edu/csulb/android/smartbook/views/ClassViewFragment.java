package edu.csulb.android.smartbook.views;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;

public class ClassViewFragment extends Fragment {

	String mClassCode;
	TextView className;
	TextView classCode;
	TextView profName;
	TextView profContact;
	TextView profDep;
	ImageView profImg;
	TextView classInfo;

	public ClassViewFragment() {

	}

	public ClassViewFragment(final String classCode) {
		this.mClassCode = classCode;
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
		// queryData();

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

}
