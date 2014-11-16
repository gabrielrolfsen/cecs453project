package edu.csulb.android.smartbook.views;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.csulb.android.smartbook.R;

public class ProfileFragment extends Fragment implements
LoaderManager.LoaderCallbacks<Cursor> {

	static final String[] PROJECTION = new String[] {
		ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME };

	// This is the select criteria
	static final String SELECTION = "((" + ContactsContract.Data.DISPLAY_NAME
			+ " NOTNULL) AND (" + ContactsContract.Data.DISPLAY_NAME
			+ " != '' ))";

	SimpleCursorAdapter mAdapter;
	ListView listMyClasses;

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

		getLoaderManager().initLoader(0, null, this);

		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.
		return new CursorLoader(getActivity(),
				ContactsContract.Data.CONTENT_URI, PROJECTION, SELECTION, null,
				null);
	}

	@Override
	public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
		// Swap the new cursor in. (The framework will take care of closing the
		// old cursor once we return.)
		mAdapter.swapCursor(data);
	}

	// Called when a previously created loader is reset, making the data
	// unavailable
	@Override
	public void onLoaderReset(final Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed. We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		// Do something when a list item is clicked
	}

}
