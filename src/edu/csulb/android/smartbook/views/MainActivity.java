package edu.csulb.android.smartbook.views;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.csulb.android.cecs453project.R;
import edu.csulb.android.smartbook.adapters.DrawerAdapter;
import edu.csulb.android.smartbook.models.DrawerItem;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;
	ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		drawerItems.add(new DrawerItem(R.drawable.ic_launcher, "Username",
				new MyClassesFragment()));
		drawerItems.add(new DrawerItem(R.drawable.ic_launcher, "My Classes",
				new MyClassesFragment()));
		drawerItems.add(new DrawerItem("Header"));
		drawerItems.add(new DrawerItem(R.drawable.ic_launcher, "My Grades",
				new MyClassesFragment()));
		mDrawerList.setAdapter(new DrawerAdapter(drawerItems));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

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
		public void onItemClick(final AdapterView parent, final View view,
				final int position, final long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(final int position) {

		// Insert the fragment by replacing any existing fragment
		final FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
		.replace(R.id.content_layout,
						drawerItems.get(position).getFragment()).commit();
		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(final CharSequence title) {

	}

}
