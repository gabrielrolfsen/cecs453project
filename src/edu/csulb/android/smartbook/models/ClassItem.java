package edu.csulb.android.smartbook.models;

import android.app.Fragment;

public class ClassItem {
	private final String code;
	private final String name;
	private final String num;
	Fragment fragment;

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