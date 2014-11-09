package edu.csulb.android.cecs453project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* Checks if the user is logged in */
		SharedPreferences pref = getApplicationContext().getSharedPreferences("appPref", 0);
		Log.d("DEBUG",String.valueOf(pref.getBoolean(LoginActivity.SESSION_KEY, false)));
		if (!pref.getBoolean(LoginActivity.SESSION_KEY, false)){
			Intent inLoginRequired = new Intent(getBaseContext(), LoginActivity.class);
			startActivity(inLoginRequired);
			finish();
		}
		setContentView(R.layout.activity_main);
			
		Intent inLoginSuccess = getIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
