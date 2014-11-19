package edu.csulb.android.smartbook.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import edu.csulb.android.smartbook.R;

/**
 * SplashActivity: Logo Activity that is shown when the user open the app for
 * the first time and is not logged in. Determines if there's an active session
 * or not and redirect the user to the right screen.
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 19, 2014
 */
public class SplashActivity extends Activity {

	/* How much time the splash screen will be shown */
	private final int SPLASH_DISPLAY_LENGTH = 1000;
	Intent activityIntent;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		final SharedPreferences pref = getApplicationContext()
				.getSharedPreferences(LoginActivity.SESSION_PREF, 0);

		/*
		 * Checks if the user is logged in and redirects to the correct activity
		 */
		if (pref.getBoolean(LoginActivity.SESSION_KEY, false)) {
			activityIntent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(activityIntent);
			finish();
		} else {

			/*
			 * New Handler to start the Menu-Activity and close this
			 * Splash-Screen after SPLASH_DISPLAY_LENGTH.
			 */
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					/* Create an Intent that will start the Login Activity. */
					activityIntent = new Intent(SplashActivity.this,
							LoginActivity.class);
					startActivity(activityIntent);
					finish();
				}
			}, SPLASH_DISPLAY_LENGTH);

		}
	}
}
