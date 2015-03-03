package com.romullogirardi.carrinho0main.model;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.romullogirardi.carrinho1items.model.Item;
import com.romullogirardi.carrinho1items.model.ItemStatus;
import com.romullogirardi.smartlist.R;

public class ItemsSelectedAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Vector<Item> itemsSelected;

	public ItemsSelectedAdapter(Context context, Vector<Item> itemsSelected) {
		this.inflater = LayoutInflater.from(context);
		this.itemsSelected = itemsSelected;
	}

	@Override
	public int getCount() {
		return itemsSelected.size();
	}

	@Override
	public Object getItem(int position) {
		return itemsSelected.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_view_simple_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textViewItemSelected = (TextView) convertView.findViewById(R.id.list_view_simple_item_text_view);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Item item = itemsSelected.get(position);
		viewHolder.textViewItemSelected.setText(item.itemSelectedToString());

		if (item.getStatus().equals(ItemStatus.PURCHASED)) {
			if (Build.VERSION.SDK_INT >= 16) {
				convertView.setBackground(GlobalReferences.applicationContext.getResources().getDrawable(R.drawable.gradient_bg_green));
			}
			else {
				convertView.setBackgroundDrawable(GlobalReferences.applicationContext.getResources().getDrawable(R.drawable.gradient_bg_green));
			}
		}
		else if (item.getStatus().equals(ItemStatus.NOT_FOUND)) {
			if (Build.VERSION.SDK_INT >= 16) {
				convertView.setBackground(GlobalReferences.applicationContext.getResources().getDrawable(R.drawable.gradient_bg_red));
			}
			else {
				convertView.setBackgroundDrawable(GlobalReferences.applicationContext.getResources().getDrawable(R.drawable.gradient_bg_red));
			}
		}
		else {
			convertView.setBackgroundColor(Color.WHITE);
		}

		return convertView;
	}

	private class ViewHolder {
		TextView textViewItemSelected;
	}
}