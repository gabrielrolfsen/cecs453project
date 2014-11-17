package edu.csulb.android.smartbook.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.csulb.android.smartbook.R;
import edu.csulb.android.smartbook.models.ClassItem;

public class ClassItemAdapter extends BaseAdapter {

	private final ArrayList<ClassItem> classList;

	public ClassItemAdapter() {
		this.classList = null;
	}

	public ClassItemAdapter(final ArrayList<ClassItem> classList) {
		this.classList = classList;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) parent
					.getContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.item_class, null);
			holder.mName = (TextView) convertView
					.findViewById(R.id.txtClassItemName);
			holder.mCode = (TextView) convertView
					.findViewById(R.id.txtClassItemCode);
			holder.mNum = (TextView) convertView
					.findViewById(R.id.txtClassItemNum);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ClassItem classItem = getItem(position);

		if (holder.mCode != null) {
			holder.mCode.setText(classItem.getCode());
		}

		if (holder.mName != null) {
			holder.mName.setText(classItem.getName());
		}

		if (holder.mNum != null) {
			holder.mNum.setText(classItem.getNum());
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView mCode;
		private TextView mName;
		private TextView mNum;
	}

	@Override
	public int getCount() {
		if (classList == null) {
			return 0;
		}
		return classList.size();
	}

	@Override
	public ClassItem getItem(final int position) {
		if (classList == null) {
			return null;
		}
		return classList.get(position);
	}

	public void addItem(final ClassItem classItem) {
		classList.add(classItem);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(final int position) {
		return classList != null ? classList.get(position).hashCode() : 0;
	}

}
