package edu.csulb.android.smartbook.views;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.adapters.ClassItemAdapter;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.ClassItem;

public class MyClassesFragment extends Fragment {

	ArrayAdapter<String> mAdapter;
	ClassItemAdapter classItemAdapter;
	ArrayList<ClassItem> classList = new ArrayList<ClassItem>();
	ListView listMyClasses;
	SQLiteDatabase db;
	ArrayList<String> toAdd = new ArrayList<String>();

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_my_classes,
				container, false);
		listMyClasses = (ListView) view.findViewById(R.id.lstMyClasses);
		queryData();

		classItemAdapter = new ClassItemAdapter(classList);
		listMyClasses.setAdapter(classItemAdapter);

		return view;
	}

	private void queryData() {
		Log.d("MyClassesFragment queryData()", "SELECT "
				+ DatabaseHandler.KEY_STUDENT_ASSIGNMENT_IDCOURSE + " FROM "
				+ DatabaseHandler.TABLE_STUDENT_COURSE + "WHERE "
				+ DatabaseHandler.KEY_STUDENT_ASSIGNMENT_IDSTUDENT + " = "
				+ "\"" + "\"");
		try {
			final SharedPreferences pref = getActivity()
					.getApplicationContext().getSharedPreferences(
							LoginActivity.SESSION_PREF, 0);
			final DatabaseHandler dbHandler = DatabaseHandler
					.getInstance(getActivity().getApplicationContext());
			db = dbHandler.getWritableDatabase();

			// Not working (why?)
			final Cursor c = db.rawQuery("SELECT "
					+ DatabaseHandler.KEY_STUDENT_COURSE_IDCOURSE + " FROM "
					+ DatabaseHandler.TABLE_STUDENT_COURSE + " WHERE "
					+ DatabaseHandler.KEY_STUDENT_COURSE_IDSTUDENT + " = "
					+ "\"" + pref.getString(LoginActivity.USER_ID, "") + "\"",
					null);

			if (c.getCount() > 0) {
				if (c.moveToFirst()) {
					do {
						classList
								.add(new ClassItem(
										c.getString(c
												.getColumnIndex(DatabaseHandler.KEY_STUDENT_COURSE_IDCOURSE)),
										"Name", "Num", new ClassViewFragment()));

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
