package edu.csulb.android.smartbook.models;

import android.app.Fragment;

public class DrawerItem {
	/* Set default to 0 = header */
	private int icon = 0;
	private String title;
	Fragment fragment;

	/* Constructor for header icon = 0 */
	public DrawerItem(final String headerTitle) {
		this.title = headerTitle;
	}

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
