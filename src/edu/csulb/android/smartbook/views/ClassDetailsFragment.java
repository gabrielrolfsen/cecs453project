package edu.csulb.android.smartbook.views;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;

public class ClassDetailsFragment extends Fragment {

	SimpleCursorAdapter mAdapter;
	ListView listMyClasses;
	SQLiteDatabase db;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_my_classes,
				container, false);
		listMyClasses = (ListView) view.findViewById(R.id.lstMyclasses);

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

		final String[] fromColumns = { ContactsContract.Data.DISPLAY_NAME };
		final int[] toViews = { android.R.id.text1 };

		mAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, null, fromColumns,
				toViews, 0);

		listMyClasses.setAdapter(mAdapter);

		return view;
	}

	private void openAndQueryDatabase() {
		try {
			final DatabaseHandler dbHandle = DatabaseHandler
					.getInstance(getActivity().getApplicationContext());
			db = dbHandle.getWritableDatabase();

			final Cursor c = db.rawQuery("SELECT , ", null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						final String firstName = c.getString(c
								.getColumnIndex("FirstName"));
						final int age = c.getInt(c.getColumnIndex("Age"));

					} while (c.moveToNext());
				}
			}
		} catch (final SQLiteException se) {
			Log.e(getClass().getSimpleName(),
					"Could not create or Open the database");
		} finally {
			db.close();
		}

	}
}