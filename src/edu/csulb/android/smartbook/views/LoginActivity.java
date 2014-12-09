package edu.csulb.android.smartbook.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.Student;

/**
 * LoginActivity: Handles login session, verify password/username using the
 * local database.
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 19, 2014
 */
public class LoginActivity extends Activity {

	private static final String REGISTER_WEBSITE = "http://www.csulb.edu";
	public static final String SESSION_PREF = "sesPref";
	public static final String SESSION_KEY = "SESSION_STATUS";
	protected static final String USER_ID = "USER_LOGIN";
	protected static final String USER_FNAME = "USER_FNAME";
	protected static final String USER_LNAME = "USER_LNAME";
	protected static final String USER_MAIL = "USER_MAIL";
	protected static final String USER_MAJOR = "USER_MAJOR";

	Button btnLogin;
	EditText edtLogin;
	EditText edtPass;
	Editor prefEditor;
	TextView txtRegister;
	DatabaseHandler db;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* SharedPreferences to get user's info and session's status */
		final SharedPreferences pref = getApplicationContext()
				.getSharedPreferences(SESSION_PREF, 0);
		prefEditor = pref.edit();

		db = DatabaseHandler.getInstance(getApplicationContext());

		setContentView(R.layout.activity_login);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		edtLogin = (EditText) findViewById(R.id.edtLogin);
		edtPass = (EditText) findViewById(R.id.edtPass);
		txtRegister = (TextView) findViewById(R.id.txtRegister);

		/**
		 * Checks the login information and proceed to the main screen
		 **/
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final String login = edtLogin.getText().toString();
				final String pass = edtPass.getText().toString();
				if (attemptLogin(login, pass)) {
					final Intent inLogin = new Intent(getBaseContext(),
							MainActivity.class);
					LoginActivity.this.startActivity(inLogin);
					finish();
				} else {
					Toast.makeText(getBaseContext(), "Login failed.",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		txtRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				txtRegister.setTextColor(Color.BLUE);
				final Uri uriUrl = Uri.parse(REGISTER_WEBSITE);
				final Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
						uriUrl);
				LoginActivity.this.startActivity(launchBrowser);
			}
		});
	}

	private boolean attemptLogin(final String login, final String pass) {

		final Student st = db.getStudent(login);
		if (st != null && st.getsPassword().equals(pass)) {
			prefEditor.putBoolean(SESSION_KEY, true);
			prefEditor.putString(USER_ID, login);
			prefEditor.putString(USER_FNAME, st.getsFirstName());
			prefEditor.putString(USER_LNAME, st.getsLastName());
			prefEditor.putString(USER_MAIL, st.getsEmail());
			prefEditor.putString(USER_MAJOR, st.getsMajor());
			prefEditor.commit();
			return true;
		}

		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
