package edu.csulb.android.smartbook.views;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.adapters.DrawerAdapter;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.DrawerItem;

/**
 * MainActivity: Manages all fragments through the NavigationDrawer and handles
 * the NFC functionality.
 *
 * @author Gabriel Franzoni
 * @author Joaquin Gonzales
 * @version 1.0
 * @since Nov 19, 2014
 */
public class MainActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;
	private RelativeLayout relativeLayoutDrawer;
	private final ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();

	private NfcAdapter mNfcAdapter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		relativeLayoutDrawer = (RelativeLayout) findViewById(R.id.lyt_left_drawer);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		final TextView myTextView = (TextView) findViewById(R.id.txtTapSmartTag);
		final Typeface myTypeface = Typeface.createFromAsset(getAssets(),
				"fonts/stheitiultralight.ttf");
		myTextView.setTypeface(myTypeface);

		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inJustDecodeBounds = true;
		// BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
		// int imageHeight = options.outHeight;
		// int imageWidth = options.outWidth;
		// String imageType = options.outMimeType;

		// ClassViewFragment firstFragment = new ClassViewFragment();
		// getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
		// firstFragment).commit();

		// Set the adapter for the list view

		createNavigationDrawer();

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

	private void createNavigationDrawer() {
		final SharedPreferences pref = getSharedPreferences(
				LoginActivity.SESSION_PREF, 0);

		drawerItems.add(new DrawerItem("Current Session"));
		drawerItems.add(new DrawerItem(R.drawable.ic_profile, pref.getString(
				LoginActivity.USER_FNAME, "Undef.")
				+ " "
				+ pref.getString(LoginActivity.USER_LNAME, "Undef."),
				new MyProfileFragment()));
		drawerItems.add(new DrawerItem("Smart Menu"));
		drawerItems.add(new DrawerItem(R.drawable.ic_my_classes, "My Classes",
				new MyClassesFragment()));
		drawerItems.add(new DrawerItem(R.drawable.ic_paperstack,
				"My Assignments", new MyAssignmentFragment()));
		drawerItems.add(new DrawerItem(R.drawable.ic_my_grades, "My Grades",
				new MyClassesFragment()));
		// drawerItems.add(new DrawerItem(R.drawable.ic_gear_logout, "Logout"));
		final RelativeLayout logoutItem = (RelativeLayout) findViewById(R.id.lyt_logout);
		logoutItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						MainActivity.this);

				alertDialogBuilder.setTitle("Logout");

				// set dialog message
				alertDialogBuilder
				.setMessage("Are you sure that you want to logout?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							final DialogInterface dialog,
							final int id) {
						final SharedPreferences pref = MainActivity.this
								.getSharedPreferences(
										LoginActivity.SESSION_PREF,
										0);
						final Editor prefEditor = pref.edit();
						prefEditor.putBoolean(
												LoginActivity.SESSION_KEY,
												false);
						prefEditor.commit();
						// TODO: remove data from user
						final Intent endSession = new Intent(
								MainActivity.this,
								LoginActivity.class);
						endSession
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						((Activity) MainActivity.this).finish();
						MainActivity.this
						.startActivity(endSession);
					}
				})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});

				final AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
		mDrawerList.setAdapter(new DrawerAdapter(drawerItems));
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
			selectNavigationItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectNavigationItem(final int position) {
		final String fragmentTag = "INNER_FRAG";
		// Insert the fragment by replacing any existing fragment
		final FragmentManager fragmentManager = getSupportFragmentManager();
		final Fragment activeFragment = getFragmentManager().findFragmentByTag(
				"INNER_FRAG");
		if (activeFragment != null && activeFragment.isVisible()) {
			fragmentManager
					.beginTransaction()
			.replace(R.id.content_layout,
							drawerItems.get(position).getFragment(), "MAIN")
					.commit();
		}
		fragmentManager
				.beginTransaction()
		.replace(R.id.content_layout,
						drawerItems.get(position).getFragment(), fragmentTag)
				.addToBackStack(null).commit();

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(relativeLayoutDrawer);

	}

	@Override
	public void onBackPressed() {
		/* Creates a AlertDialog to ask the user if he wants to exit the app */
		if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
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
			getSupportFragmentManager().popBackStack();
		}

	}

	// The code after this comment comes from an online tutorial and has only
	// been tweaked to
	// apply to our project specifically. Joaquin Gonzalez made all changes in
	// this section.
	// The tutorial is located at:
	// http://code.tutsplus.com/tutorials/reading-nfc-tags-with-android--mobile-17278

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

		@SuppressLint("SimpleDateFormat")
		@Override
		protected void onPostExecute(final String result) {
			if (result != null) {
				final TextView smartTag = (TextView) findViewById(R.id.txtTapSmartTag);
				smartTag.setVisibility(View.GONE);
				final DatabaseHandler dbHandler = DatabaseHandler
						.getInstance(getApplicationContext());

				// Create fragment and give it an argument specifying the class
				// it should show
				final ClassViewFragment newFragment = new ClassViewFragment(
						result);
				final FragmentManager fragmentManager = getSupportFragmentManager();

				fragmentManager.beginTransaction()
				.replace(R.id.content_layout, newFragment)
						.addToBackStack(null).commit();

				final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				final String currentDate = sdf.format(new Date());

				final SharedPreferences pref = getApplicationContext()
						.getSharedPreferences(LoginActivity.SESSION_PREF, 0);
				final String id = pref.getString(LoginActivity.USER_ID, "");

				Log.d("Current Date: ", currentDate);

				if (dbHandler.addAttendance(id, result, currentDate, 2)) {
					Toast.makeText(getApplicationContext(),
							"You have checked in to class: " + result,
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Could not check in to class: " + result,
							Toast.LENGTH_SHORT).show();
				}
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
