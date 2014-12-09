/**
 *  
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 29, 2014
 */
package edu.csulb.android.smartbook.views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.Assignment;
import edu.csulb.android.smartbook.models.StudentAssignment;

/**
 * AssignmentFragment <Description>
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 29, 2014
 */
public class AssignmentFragment extends Fragment {

	public StudentAssignment stuAssign;
	public Assignment assign;
	public String courseID;
	public String courseName;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_assignment,
				container, false);

		final TextView txtAssignName = (TextView) view
				.findViewById(R.id.txtAssignmentNameValue);
		final TextView txtAssignDue = (TextView) view
				.findViewById(R.id.txtAssignmentDueValue);
		final TextView txtAssignDescrip = (TextView) view
				.findViewById(R.id.txtAssignmentDescriptionValue);

		final TextView txtTitleCourseId = (TextView) view
				.findViewById(R.id.txtFragmentAssignCourseID);
		final TextView txtTitleCourseName = (TextView) view
				.findViewById(R.id.txtFragmentAssignCourseName);

		txtTitleCourseId.setText(courseID);
		txtTitleCourseName.setText(courseName);
		txtTitleCourseName.setTextColor((Color.parseColor("#000000")));
		txtTitleCourseId.setTextColor((Color.parseColor("#000000")));
		txtAssignName.setTextColor((Color.parseColor("#000000")));
		txtAssignDue.setTextColor((Color.parseColor("#000000")));
		txtAssignDescrip.setTextColor((Color.parseColor("#000000")));

		final DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		assign = databaseHandler.getAssignment(courseID, stuAssign.getaName());

		txtAssignName.setText(assign.getaName());
		txtAssignDue.setText(assign.getaDuedate());
		if (assign.getaDescription() == null) {
			txtAssignDescrip.setText("N/A");
		} else {
			txtAssignDescrip.setText(assign.getaDescription());
		}

		return view;
	}

}
