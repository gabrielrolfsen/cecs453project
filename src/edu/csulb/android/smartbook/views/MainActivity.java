package edu.csulb.android.smartbook.views;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.adapters.DrawerAdapter;
import edu.csulb.android.smartbook.models.DrawerItem;

public class MainActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;
	ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();

	private NfcAdapter mNfcAdapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		final SharedPreferences pref = getSharedPreferences(
				LoginActivity.SESSION_PREF, 0);
		
		//ClassViewFragment firstFragment = new ClassViewFragment();
		//getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();

		// Set the adapter for the list view
		drawerItems.add(new DrawerItem(R.drawable.ic_profile, pref.getString(
				LoginActivity.USER_ID, ""), new MyClassesFragment()));
		drawerItems.add(new DrawerItem(R.drawable.ic_my_classes, "My Classes",
				new MyClassesFragment()));
		drawerItems.add(new DrawerItem("Header"));
		drawerItems.add(new DrawerItem(R.drawable.ic_my_grades, "My Grades",
				new MyClassesFragment()));
		mDrawerList.setAdapter(new DrawerAdapter(drawerItems));

		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		if (mNfcAdapter == null) {
			Toast.makeText(this, "This device does not support NFC",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		if (!mNfcAdapter.isEnabled()) {
			Toast.makeText(this,
					"NFC is disabled. Please go to settings to enable NFC",
					Toast.LENGTH_LONG).show();
		}

		handleNfcIntent(getIntent());
	}

	@Override
	protected void onResume() {
		super.onResume();
		setupForegroundDispatch(this, mNfcAdapter);
	}

	@Override
	protected void onPause() {
		stopForegroundDispatch(this, mNfcAdapter);
		super.onPause();
	}

	@Override
	protected void onNewIntent(final Intent intent) {
		/**
		 * This method gets called, when a new Intent gets associated with the
		 * current activity instance. Instead of creating a new activity,
		 * onNewIntent will be called. For more information have a look at the
		 * documentation.
		 * 
		 * In our case this method gets called, when the user attaches a Tag to
		 * the device.
		 */
		handleNfcIntent(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		final int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(
				@SuppressWarnings("rawtypes") final AdapterView parent,
				final View view, final int position, final long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(final int position) {

		// Insert the fragment by replacing any existing fragment
		final FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
		.replace(R.id.content_layout,
						drawerItems.get(position).getFragment())
				.addToBackStack(null).commit();
		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void onBackPressed() {
		/* Creates a AlertDialog to ask the user if he wants to exit the app */
		if (getFragmentManager().getBackStackEntryCount() == 0) {
			new AlertDialog.Builder(this)
			.setMessage("Are you sure you want to exit?")
			.setNegativeButton("Cancel", null)
			.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(
						final DialogInterface dialog,
						final int which) {
					finish();
				}

			}).show();
		} else {
			getFragmentManager().popBackStack();
		}

	}

	/**
	 * Background task for reading the data. Do not block the UI thread while
	 * reading.
	 * 
	 * @author Ralf Wondratschek
	 *
	 */
	private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

		@Override
		protected String doInBackground(final Tag... params) {
			final Tag tag = params[0];

			final Ndef ndef = Ndef.get(tag);
			if (ndef == null) {
				// NDEF is not supported by this Tag.
				return null;
			}

			final NdefMessage ndefMessage = ndef.getCachedNdefMessage();

			final NdefRecord[] records = ndefMessage.getRecords();
			for (final NdefRecord ndefRecord : records) {
				if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN
						&& Arrays.equals(ndefRecord.getType(),
								NdefRecord.RTD_TEXT)) {
					try {
						return readText(ndefRecord);
					} catch (final UnsupportedEncodingException e) {
						Log.e("NFC", "Unsupported Encoding", e);
					}
				}
			}

			return null;
		}

		private String readText(final NdefRecord record)
				throws UnsupportedEncodingException {
			/*
			 * See NFC forum specification for "Text Record Type Definition" at
			 * 3.2.1
			 * 
			 * http://www.nfc-forum.org/specs/
			 * 
			 * bit_7 defines encoding bit_6 reserved for future use, must be 0
			 * bit_5..0 length of IANA language code
			 */

			final byte[] payload = record.getPayload();

			// Get the Text Encoding
			final String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8"
					: "UTF-16";

			// Get the Language Code
			final int languageCodeLength = payload[0] & 0063;

			// String languageCode = new String(payload, 1, languageCodeLength,
			// "US-ASCII");
			// e.g. "en"

			// Get the Text
			return new String(payload, languageCodeLength + 1, payload.length
					- languageCodeLength - 1, textEncoding);
		}

		@Override
		protected void onPostExecute(final String result) {
			if (result != null) {
				TextView smartTag = (TextView) findViewById(R.id.txtTapSmartTag);
				smartTag.setVisibility(View.GONE);
				
				// Create fragment and give it an argument specifying the class it should show
				ClassViewFragment newFragment = new ClassViewFragment(result);

				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack so the user can navigate back
				transaction.replace(R.id.fragment_container, newFragment);
				transaction.addToBackStack(null);

				// Commit the transaction
				transaction.commit();
				Toast.makeText(getApplicationContext(),
						"Read content: " + result, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void handleNfcIntent(final Intent intent) {
		final String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

			final String type = intent.getType();
			if ("text/plain".equals(type)) {

				final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				new NdefReaderTask().execute(tag);

			} else {
				Log.d("NFC", "Wrong mime type: " + type);
			}
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

			// In case we would still use the Tech Discovered Intent
			final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			final String[] techList = tag.getTechList();
			final String searchedTech = Ndef.class.getName();

			for (final String tech : techList) {
				if (searchedTech.equals(tech)) {
					new NdefReaderTask().execute(tag);
					break;
				}
			}
		}
	}

	/**
	 * @param activity
	 *            The corresponding {@link Activity} requesting the foreground
	 *            dispatch.
	 * @param adapter
	 *            The {@link NfcAdapter} used for the foreground dispatch.
	 */
	public static void setupForegroundDispatch(final Activity activity,
			final NfcAdapter adapter) {
		final Intent intent = new Intent(activity.getApplicationContext(),
				activity.getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		final PendingIntent pendingIntent = PendingIntent.getActivity(
				activity.getApplicationContext(), 0, intent, 0);

		final IntentFilter[] filters = new IntentFilter[1];
		final String[][] techList = new String[][] {};

		// Notice that this is the same filter as in our manifest.
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
		try {
			filters[0].addDataType("text/plain");
		} catch (final MalformedMimeTypeException e) {
			throw new RuntimeException("Check your mime type.");
		}

		adapter.enableForegroundDispatch(activity, pendingIntent, filters,
				techList);
	}

	/**
	 * @param activity
	 *            The corresponding {@link BaseActivity} requesting to stop the
	 *            foreground dispatch.
	 * @param adapter
	 *            The {@link NfcAdapter} used for the foreground dispatch.
	 */
	public static void stopForegroundDispatch(final Activity activity,
			final NfcAdapter adapter) {
		adapter.disableForegroundDispatch(activity);
	}

}
