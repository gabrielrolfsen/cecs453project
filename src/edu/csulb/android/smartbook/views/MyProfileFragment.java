package edu.csulb.android.smartbook.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.csulb.android.smartbook.R;

/**
 * MyProfileFragment: User profile, shows all data from the current logged user,
 * data gathered from SharedPreferences
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 18, 2014
 */
public class MyProfileFragment extends Fragment {

	String mClassCode;
	TextView userName;
	TextView userID;
	TextView userMajor;
	ImageView userImg;
	Button btnEditProfile;

	public MyProfileFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_my_profile,
				container, false);
		userName = (TextView) view.findViewById(R.id.txtUserName);
		userID = (TextView) view.findViewById(R.id.txtUserId);
		userMajor = (TextView) view.findViewById(R.id.txtUserMajor);
		btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);

		btnEditProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final FragmentManager fragmentManager = getFragmentManager();
				final Fragment frag = new EditMyProfileFragment();
				fragmentManager.beginTransaction()
				.replace(R.id.content_layout, frag)
						.addToBackStack(null).commit();
			}
		});
		getData();

		return view;
	}

	/**
	 * getData(): get data from SharedPreferences and populate the TextViews
	 */
	public void getData() {
		final SharedPreferences pref = getActivity().getSharedPreferences(
				LoginActivity.SESSION_PREF, 0);
		userName.setText(pref.getString(LoginActivity.USER_FNAME, "Undefined")
				+ " " + pref.getString(LoginActivity.USER_LNAME, "Undefined"));
		userID.setText("#"
				+ pref.getString(LoginActivity.USER_ID, "#000000000"));
		userMajor
		.setText(pref.getString(LoginActivity.USER_MAJOR, "Undefined"));

	}

}
