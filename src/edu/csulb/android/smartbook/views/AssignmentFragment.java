/**
 *  
 *
 * @author Hao Vo
 * @version 1.0
 * @since Nov 29, 2014
 */
package edu.csulb.android.smartbook.views;

import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.Assignment;
import edu.csulb.android.smartbook.models.StudentAssignment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * AssignmentFragment
 * <Description> 
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
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState){

		View view = inflater.inflate(R.layout.fragment_assignment, container, false);
		
		TextView txtAssignName = (TextView) view.findViewById(R.id.txtAssignmentNameValue);
		TextView txtAssignDue = (TextView) view.findViewById(R.id.txtAssignmentDueValue);
		TextView txtAssignDescrip = (TextView) view.findViewById(R.id.txtAssignmentDescriptionValue);
		
		TextView txtTitleCourseId = (TextView) view.findViewById(R.id.txtFragmentAssignCourseID);
		TextView txtTitleCourseName = (TextView) view.findViewById(R.id.txtFragmentAssignCourseName);
		
		txtTitleCourseId.setText(courseID);
		txtTitleCourseName.setText(courseName);

		DatabaseHandler databaseHandler = DatabaseHandler
				.getInstance(getActivity().getApplicationContext());
		assign = databaseHandler.getAssignment(courseID, stuAssign.getaName());
		
		txtAssignName.setText(assign.getaName());
		txtAssignDue.setText(assign.getaDuedate());
		if(assign.getaDescription() == null)
			txtAssignDescrip.setText("N/A");
		else
			txtAssignDescrip.setText(assign.getaDescription());
		
		return view;
	}

}
