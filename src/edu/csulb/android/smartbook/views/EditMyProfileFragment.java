package edu.csulb.android.smartbook.views;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.csulb.android.smartbook.R;

/**
 * MyProfileFragment: User profile, shows all data from the current logged user,
 * data gathered from SharedPreferences
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 18, 2014
 */
public class EditMyProfileFragment extends Fragment {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;

	String mClassCode;
	TextView userName;
	TextView userID;
	TextView userMajor;
	TextView editPhoto;
	ImageView userImg;

	public EditMyProfileFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_my_profile,
				container, false);
		userName = (TextView) view.findViewById(R.id.txtUserName);
		userID = (TextView) view.findViewById(R.id.txtUserId);
		userMajor = (TextView) view.findViewById(R.id.txtUserMajor);
		editPhoto = (TextView) view.findViewById(R.id.txtEditPhoto);

		editPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}

		});

		getData();

		return view;
	}

	private static Uri getOutputMediaFileUri(final int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(final int type) {

		Environment.getExternalStorageState();
		final File mediaStorageDir = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
		.format(new Date(type));
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		return mediaFile;
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(getActivity().getBaseContext(),
						"Image saved to:\n" + data.getData(), Toast.LENGTH_LONG)
						.show();
				handleSmallCameraPhoto(data);
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

	}

	private void handleSmallCameraPhoto(final Intent intent) {
		final Bundle extras = intent.getExtras();
		final Bitmap mImageBitmap = (Bitmap) extras.get("data");
		userImg.setImageBitmap(mImageBitmap);
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
