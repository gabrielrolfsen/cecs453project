package edu.csulb.android.smartbook.models;

public class DrawerItem {
	/* Set default to 0 = header */
	private int icon = 0;
	private String title;
	
	/* Constructor for header icon = 0*/
	public DrawerItem(String headerTitle){
		this.title = headerTitle;
	}
	
	public DrawerItem(int icon, String title) {
		this.icon = icon;
		this.title = title;
	}

	/* Checks if the item is a header */
	public boolean isHeader(){
		return this.icon == 0 ? true : false;
	}
	
	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getIcon() {
		return icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
