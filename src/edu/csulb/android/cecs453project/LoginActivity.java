package edu.csulb.android.cecs453project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
	
	protected static final String SESSION_KEY = "SESSION_STATUS";
	Button btnLogin;
	EditText edtLogin;
	EditText edtPass;
	Editor prefEditor;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               
		SharedPreferences pref = getApplicationContext().getSharedPreferences("appPref", 0);
		prefEditor = pref.edit();
        
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtPass = (EditText) findViewById(R.id.edtPass);
        
        
        /** Checks the login information and 
         * proceed to the main screen **/
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String login = edtLogin.getText().toString();
				String pass = edtPass.getText().toString();
				if (attemptLogin(login, pass)){
					prefEditor.putBoolean(SESSION_KEY, true);
					prefEditor.commit();
					Intent inLogin = new Intent(getBaseContext(),MainActivity.class);
					LoginActivity.this.startActivity(inLogin);
				}else{
					Toast.makeText(getBaseContext(), "Login failed.", Toast.LENGTH_LONG).show();
				}
			}
		});
    }
    
    private boolean attemptLogin(String login, String pass){
    	
    	/** TODO: Request database access and check credentials **/
    	if(login.equals("admin") && pass.equals("admin"))
    		return true;
    	
    	return false;
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
