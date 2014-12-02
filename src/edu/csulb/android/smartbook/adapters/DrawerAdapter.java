package edu.csulb.android.smartbook.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.models.DrawerItem;
import edu.csulb.android.smartbook.views.LoginActivity;

/**
 * DrawerAdapter: Manages NavigationDrawer Items
 *
 * @author Gabriel Franzoni
 * @version 1.0
 * @since Nov 19, 2014
 */
public class DrawerAdapter extends BaseAdapter {

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	private static final int TYPE_LOGOUT = 2;
	private static final int TYPE_MAX_COUNT = TYPE_LOGOUT + 1;

	private final ArrayList<DrawerItem> itemList;

	public DrawerAdapter(final ArrayList<DrawerItem> itemList) {
		this.itemList = itemList;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder holder = null;
		final int type = getItemViewType(position);

		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) parent
					.getContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();

			switch (type) {
			case TYPE_ITEM:
				convertView = inflater.inflate(R.layout.item_drawer, parent,
						false);
				holder.mText = (TextView) convertView
						.findViewById(R.id.txtDrawerItem);
				holder.mIcon = (ImageView) convertView
						.findViewById(R.id.imgDrawerItem);
				break;
			case TYPE_HEADER:
				convertView = inflater.inflate(R.layout.item_drawer_header,
						null);
				holder.mText = (TextView) convertView
						.findViewById(R.id.txtDrawerHeader);
				// Make headers unclickable
				convertView.setEnabled(false);
				convertView.setOnClickListener(null);
				break;
			case TYPE_LOGOUT:
				convertView = inflater.inflate(R.layout.item_drawer, parent,
						false);
				holder.mText = (TextView) convertView
						.findViewById(R.id.txtDrawerItem);
				holder.mIcon = (ImageView) convertView
						.findViewById(R.id.imgDrawerItem);
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								parent.getContext());

						alertDialogBuilder.setTitle("Logout");

						// set dialog message
						alertDialogBuilder
								.setMessage(
										"Are you sure that you want to logout?")
								.setCancelable(false)
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													final DialogInterface dialog,
													final int id) {
												final SharedPreferences pref = parent
														.getContext()
														.getSharedPreferences(
																LoginActivity.SESSION_PREF,
																0);
												final Editor prefEditor = pref
														.edit();
												prefEditor
														.putBoolean(
																LoginActivity.SESSION_KEY,
																false);
												prefEditor.commit();
												// TODO: remove data from user
												final Intent endSession = new Intent(
														parent.getContext(),
														LoginActivity.class);
												endSession
														.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												((Activity) parent.getContext())
										.finish();
												parent.getContext()
														.startActivity(
																endSession);

											}
										})
								.setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													final DialogInterface dialog,
													final int id) {
												dialog.cancel();
											}
										});

						final AlertDialog alertDialog = alertDialogBuilder
								.create();
						alertDialog.show();

					}
				});
				break;
			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final DrawerItem dIt = getItem(position);

		if (holder.mText != null) {
			holder.mText.setText(dIt.getTitle());
		}

		if (holder.mIcon != null) {
			holder.mIcon.setImageResource(dIt.getIcon());
		}

		return convertView;
	}

	private static class ViewHolder {
		public TextView mText;
		public ImageView mIcon;

	}

	@Override
	public int getCount() {
		if (itemList == null) {
			return 0;
		}
		return itemList.size();
	}

	@Override
	public DrawerItem getItem(final int position) {
		if (itemList == null) {
			return null;
		}
		return itemList.get(position);
	}

	@Override
	public int getItemViewType(final int position) {
		return itemList.get(position).getType();
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	public void addItem(final DrawerItem dIt) {
		itemList.add(dIt);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(final int position) {
		return itemList != null ? itemList.get(position).hashCode() : 0;
	}

}
