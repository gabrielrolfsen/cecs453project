package edu.csulb.android.cecs453project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {

	/* How much time the splash screen will be shown */
	private final int SPLASH_DISPLAY_LENGTH = 1000;
	Intent activityIntent;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		
		/* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
        		SharedPreferences pref = getApplicationContext().getSharedPreferences(LoginActivity.SESSION_PREF, 0);

        		/* Checks if the user is logged in and redirects to the correct activity */
        		if (!pref.getBoolean(LoginActivity.SESSION_KEY, false)){
        			activityIntent = new Intent(getBaseContext(), LoginActivity.class);
        		}else{
        			activityIntent = new Intent(SplashActivity.this,MainActivity.class);
        		}
                startActivity(activityIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

	}
}
