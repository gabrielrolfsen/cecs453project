package edu.csulb.android.smartbook.models;

import android.support.v4.app.Fragment;

/**
 * DrawerItem: Item that goes into the NavigationDrawer, the item can be a
 * header (unclickable) or an item that launches a Fragment.
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 19, 2014
 */
public class DrawerItem {
	/* Set default to 0 = header */
	private int icon = 0;
	private String title;
	Fragment fragment;

	/* Constructor for header icon = 0 */
	/**
	 * @param headerTitle
	 *            Header's title (unclickable).
	 */
	public DrawerItem(final String headerTitle) {
		this.title = headerTitle;
	}

	/**
	 * @param icon
	 *            Icon that will be displayed in the left of the title.
	 * @param title
	 *            Item's title.
	 * @param fragment
	 *            Fragment that will be launched when the user clicks the item.
	 */
	public DrawerItem(final int icon, final String title,
			final Fragment fragment) {
		this.icon = icon;
		this.title = title;
		this.fragment = fragment;
	}

	/* Checks if the item is a header */
	public boolean isHeader() {
		return this.icon == 0 ? true : false;
	}

	public void setIcon(final int icon) {
		this.icon = icon;
	}

	public int getIcon() {
		return this.icon;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Fragment getFragment() {
		return this.fragment;
	}

}
