/*
 * Copyright (C) 2010 Eric Harlow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.romullogirardi.carrinho1items.model;

import java.text.DecimalFormat;
import java.util.Vector;

import com.romullogirardi.carrinho.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public final class ItemsAdapter extends BaseAdapter implements RemoveListener, DropListener {

//	private int[] mIds;
	private int[] mLayouts;
	private LayoutInflater mInflater;
	private Vector<Item> mContent;

	public ItemsAdapter(Context context, Vector<Item> content) {
		init(context, new int[] { android.R.layout.simple_list_item_1 }, new int[] { android.R.id.text1 }, content);
	}

	public ItemsAdapter(Context context, int[] itemLayouts, int[] itemIDs, Vector<Item> content) {
		init(context, itemLayouts, itemIDs, content);
	}

	private void init(Context context, int[] layouts, int[] ids, Vector<Item> content) {
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);
//		mIds = ids;
		mLayouts = layouts;
		mContent = content;
	}

	/**
	 * The number of items in the list
	 * 
	 * @see android.widget.ListAdapter#getCount()
	 */
	public int getCount() {
		return mContent.size();
	}

	/**
	 * Since the data comes from an array, just returning the index is sufficient to get at the data. If we were using a more complex data structure, we would return whatever object represents one row in the list.
	 * 
	 * @see android.widget.ListAdapter#getItem(int)
	 */
	public Item getItem(int position) {
		return mContent.get(position);
	}

	/**
	 * Use the array index as a unique id.
	 * 
	 * @see android.widget.ListAdapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Make a view to hold each row.
	 * 
	 * @see android.widget.ListAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unneccessary calls
		// to findViewById() on each row.
		ViewHolder holder;

		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(mLayouts[0], null);

			// Creates a ViewHolder and store references to the two children views
			// we want to bind data to.
			holder = new ViewHolder();

			holder.imageViewItemImage = (ImageView) convertView.findViewById(R.id.image_view_items_image);
			holder.textViewItemName = (TextView) convertView.findViewById(R.id.text_view_items_name);
			holder.textViewItemMeasureUnit = (TextView) convertView.findViewById(R.id.text_view_items_measure_unit);
			holder.textViewItemValue = (TextView) convertView.findViewById(R.id.text_view_items_value);

			convertView.setTag(holder);
		}
		else {
			// Get the ViewHolder back to get fast access to the TextView and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		Item item = mContent.get(position);
		if (item.getImageReference() instanceof Integer) {
			holder.imageViewItemImage.setImageResource((Integer) item.getImageReference());
		}
		else if (item.getImageReference() instanceof String) {
			holder.imageViewItemImage.setImageBitmap(ItemsManager.getInstance().adjustBitmap((String) item.getImageReference()));
		}
		else {
			holder.imageViewItemImage.setImageResource(R.drawable.ic_launcher_cart);
		}
		holder.imageViewItemImage.setTag(item.getImageReference());
		holder.textViewItemName.setText(item.getName());
		holder.textViewItemMeasureUnit.setText("Unidade de medida: " + item.getMeasureUnit().toString());
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		holder.textViewItemValue.setText("Valor unitário: R$ " + decimalFormat.format(item.getValue()));

		return convertView;
	}

	static class ViewHolder {
		ImageView imageViewItemImage;
		TextView textViewItemName;
		TextView textViewItemMeasureUnit;
		TextView textViewItemValue;
	}

	public void onRemove(int which) {
		if (which < 0 || which > mContent.size()) return;
		mContent.remove(which);
	}

	public void onDrop(int from, int to) {
		Item temp = mContent.get(from);
		mContent.remove(from);
		mContent.add(to, temp);
	}
}