package edu.csulb.android.smartbook.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.csulb.android.cecs453project.R;
import edu.csulb.android.smartbook.db.DatabaseHandler;
import edu.csulb.android.smartbook.models.Student;


public class LoginActivity extends Activity {
	
	protected static final String SESSION_PREF = "sesPref";
	protected static final String SESSION_KEY = "SESSION_STATUS";
	protected static final String USER_ID = "USER_LOGIN";
	protected static final String USER_NAME = "USER_NAME";
	
	Button btnLogin;
	EditText edtLogin;
	EditText edtPass;
	Editor prefEditor;
	TextView txtRegister;
	DatabaseHandler db;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* SharedPreferences to get user's info and session's status */
        SharedPreferences pref = getApplicationContext().getSharedPreferences(SESSION_PREF, 0);
		prefEditor = pref.edit();
		
		db = DatabaseHandler.getInstance(getApplicationContext());
        
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtPass = (EditText) findViewById(R.id.edtPass);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        
        
        /** Checks the login information and 
         * proceed to the main screen **/
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String login = edtLogin.getText().toString();
				String pass = edtPass.getText().toString();
				if (attemptLogin(login, pass)){
					Intent inLogin = new Intent(getBaseContext(),MainActivity.class);
					LoginActivity.this.startActivity(inLogin);
					finish();
				}else{
					Toast.makeText(getBaseContext(), "Login failed.", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        txtRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txtRegister.setTextColor(Color.BLUE);
				Intent inRegister = new Intent(getBaseContext(),RegisterActivity.class);
				LoginActivity.this.startActivity(inRegister);
			}
		});
    }
    
    private boolean attemptLogin(String login, String pass){
    	
    	Student st = db.getStudent(login);
    	if (st != null && st.getsPassword().equals(pass)){
			prefEditor.putBoolean(SESSION_KEY, true);
			prefEditor.putString(USER_ID, login);
			prefEditor.putString(USER_NAME, st.getsFirstName());			
			prefEditor.commit();
    		return true;
    	}
    	
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
