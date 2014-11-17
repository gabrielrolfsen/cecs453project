package edu.csulb.android.smartbook.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.models.DrawerItem;

public class MyClassesAdapter extends BaseAdapter {

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	private static final int TYPE_MAX_COUNT = TYPE_ITEM + 1;

	private final ArrayList<DrawerItem> itemList;

	public MyClassesAdapter(final ArrayList<DrawerItem> itemList) {
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
				convertView = inflater.inflate(R.layout.drawer_item, null);
				holder.mText = (TextView) convertView
						.findViewById(R.id.txtDrawerItem);
				holder.mIcon = (ImageView) convertView
						.findViewById(R.id.imgDrawerItem);
				break;
			case TYPE_HEADER:
				convertView = inflater.inflate(R.layout.drawer_header, null);
				holder.mText = (TextView) convertView
						.findViewById(R.id.txtDrawerHeader);
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
		Log.d("DEBUG>", String.valueOf(itemList.get(position).isHeader()));
		return itemList.get(position).isHeader() ? TYPE_HEADER : TYPE_ITEM;
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
