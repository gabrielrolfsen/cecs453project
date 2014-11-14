package edu.csulb.android.smartbook.views;import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.csulb.android.cecs453project.R;
import edu.csulb.android.smartbook.adapters.DrawerAdapter;
import edu.csulb.android.smartbook.models.DrawerItem;

public class MainActivity extends Activity {
    private String[] mPlanetTitles = { "A", "B" };
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    ArrayList<DrawerItem> addIt = new ArrayList<DrawerItem>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        addIt.add(new DrawerItem(R.drawable.ic_launcher, "Username"));
        addIt.add(new DrawerItem(R.drawable.ic_launcher, "My Classes"));
        addIt.add(new DrawerItem("Header"));
        addIt.add(new DrawerItem(R.drawable.ic_launcher, "My Grades"));
        mDrawerList.setAdapter(new DrawerAdapter(addIt));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
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
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		Log.d("DEBUG", String.valueOf(position));
	}

	@Override
	public void setTitle(CharSequence title) {

	}
	
}
