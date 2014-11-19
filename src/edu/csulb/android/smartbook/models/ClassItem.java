package edu.csulb.android.smartbook.models;

import android.support.v4.app.Fragment;

/**
 * ClassItem: Item that goes into ClassViewFragment's listview to show the
 * classes that student is enrolled with
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 19, 2014
 */
public class ClassItem {
	private final String code;
	private final String name;
	private final String num;
	Fragment fragment;

	/**
	 * @param code
	 *            Class code (ex: CECS 453)
	 * @param name
	 *            Class name (ex: Mobile Applications Development)
	 * @param num
	 *            Class Section number (ex: #123502)
	 * @param fragment
	 *            Class Fragment, this is the fragment that the user will see
	 *            when the item is clicked.
	 */
	public ClassItem(final String code, final String name, final String num,
			final Fragment fragment) {
		this.code = code;
		this.name = name;
		this.num = num;
		this.fragment = fragment;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getNum() {
		return num;
	}

	public Fragment getFragment() {
		return fragment;
	}

}