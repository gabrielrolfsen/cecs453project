package edu.csulb.android.smartbook.views;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.adapters.ClassItemAdapter;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.ClassItem;

/**
 * MyClassesFragment: Shows all classes that the current logged in user is
 * enrolled.
 *
 * Hao Vo, modifier - change some stuff to fix a bug that cause list on ListView
 * keep adding same items to the list when use back button or call from 
 * slide menu
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 18, 2014
 */
public class MyClassesFragment extends Fragment {

//	ArrayAdapter<String> mAdapter;
	ClassItemAdapter classItemAdapter;
	ArrayList<ClassItem> classList;
	ListView listMyClasses;
	SQLiteDatabase db;
//	ArrayList<String> toAdd = new ArrayList<String>();
	View view;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		
		classList = new ArrayList<ClassItem>();
//		if (view == null) {
			view = inflater.inflate(R.layout.fragment_my_classes, container,
					false);

			listMyClasses = (ListView) view.findViewById(R.id.lstMyClasses);
			queryData();

			classItemAdapter = new ClassItemAdapter(classList);
			if (listMyClasses.getAdapter() == null) {
				listMyClasses.setAdapter(classItemAdapter);
			}
			listMyClasses.setOnItemClickListener(new ClassItemClickListener());

		return view;
	}
/*
	@Override
	public void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);

	}
*/
	/**
	 * ClassItemClickListener <Description>
	 *
	 * @author Gabriel Franzoni
	 * @version 1.0
	 * @since Nov 18, 2014
	 */
	private class ClassItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(final AdapterView parent, final View view,
				final int position, final long id) {

			// Insert the fragment by replacing any existing fragment
			final FragmentManager fragmentManager = getFragmentManager();
			fragmentManager

			.beginTransaction()
					.replace(R.id.content_layout,
					classList.get(position).getFragment())
					.addToBackStack(null).commit();
		}
	}

	private void queryData() {
		try {
			final SharedPreferences pref = getActivity()
					.getApplicationContext().getSharedPreferences(
							LoginActivity.SESSION_PREF, 0);
			final DatabaseHandler dbHandler = DatabaseHandler
					.getInstance(getActivity().getApplicationContext());
			final Cursor c = dbHandler.getAllCourseEnrollByStudent(pref
					.getString(LoginActivity.USER_ID, ""));

			if (c.getCount() > 0) {
				if (c.moveToFirst()) {
					do {
						final String classId = c
								.getString(c
										.getColumnIndex(DatabaseHandler.KEY_STUDENT_COURSE_IDCOURSE));
						final String className = c
								.getString(c
										.getColumnIndex(DatabaseHandler.KEY_COURSE_NAME));
						// TODO: Change to classNum
						final String classNum = c
								.getString(c
										.getColumnIndex(DatabaseHandler.KEY_COURSE_DAYS));
						classList.add(new ClassItem(classId, className,
								classNum, new ClassViewFragment(classId)));

					} while (c.moveToNext());
				}
			}
		} catch (final SQLiteException se) {
			Log.e(getClass().getSimpleName(),
					"EXCEPTION: Could not create or open the database.");
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
