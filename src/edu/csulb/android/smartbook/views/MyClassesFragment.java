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
import edu.csulb.android.cecs453project.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;

public class MyClassesFragment extends Fragment {

	ArrayAdapter<String> mAdapter;
	ListView listMyClasses;
	SQLiteDatabase db;
	ArrayList<String> toAdd = new ArrayList<String>();

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_my_classes,
				container, false);
		listMyClasses = (ListView) view.findViewById(R.id.lstMyClasses);

		// final ProgressBar progressBar = new ProgressBar(getActivity());
		// progressBar.setLayoutParams(new
		// LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT, Gravity.CENTER));
		// progressBar.setIndeterminate(true);
		// listMyClasses.setEmptyView(progressBar);
		//
		// final ViewGroup root = (ViewGroup) view
		// .findViewById(android.R.id.content);
		// root.addView(progressBar);

		mAdapter = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(), android.R.layout.simple_list_item_1);
		queryData();
		mAdapter.addAll(toAdd);
		listMyClasses.setAdapter(mAdapter);

		return view;
	}

	private void queryData() {
		try {
			final SharedPreferences pref = getActivity()
					.getApplicationContext().getSharedPreferences(
							LoginActivity.SESSION_PREF, 0);
			final DatabaseHandler dbHandler = DatabaseHandler
					.getInstance(getActivity().getApplicationContext());
			db = dbHandler.getWritableDatabase();

			// Not working (why?)
			final Cursor c = db.rawQuery(
					"SELECT IdCourse FROM StudentCourse "
							+ "WHERE IdStudentCourse = " + "\""
							+ pref.getString(LoginActivity.USER_ID, "") + "\"",
					null);
			Log.d("DEBUUUUG", pref.getString(LoginActivity.USER_ID, "")
					.toString());

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						toAdd.add(c.getString(c.getColumnIndex("IdStudent")));

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
