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

package com.romullogirardi.carrinho1items.view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.romullogirardi.carrinho.R;
import com.romullogirardi.carrinho1items.model.DragListener;
import com.romullogirardi.carrinho1items.model.DragNDropListView;
import com.romullogirardi.carrinho1items.model.DropListener;
import com.romullogirardi.carrinho1items.model.ItemsAdapter;
import com.romullogirardi.carrinho1items.model.ItemsManager;
import com.romullogirardi.carrinho1items.model.RemoveListener;

public class ItemsActivity extends ListActivity implements OnItemClickListener {

	//MEODOS DO CICLO DE VIDA DA Activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_items);

		//Carregar os itens na ListView
		loadListViewItems();

		//Setar os listeners
		ListView listView = getListView();
		listView.setOnItemClickListener(this);
		if (listView instanceof DragNDropListView) {
			((DragNDropListView) listView).setDropListener(mDropListener);
			((DragNDropListView) listView).setRemoveListener(mRemoveListener);
			((DragNDropListView) listView).setDragListener(mDragListener);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_new_item) {
			new ItemRegisterDialogFragment(ListView.INVALID_POSITION).show(getFragmentManager(), ItemRegisterDialogFragment.class.getSimpleName());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//IMPLEMENTAÇÃO DE MÉTODO DE OnItemClickListener
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Alterar item");
		dialogBuilder.setMessage("Que alteração deseja executar?");

		dialogBuilder.setNegativeButton("Remover", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ItemsManager.getInstance().removeItem(ItemsManager.getInstance().getItems().get(position));
				loadListViewItems();
				dialog.dismiss();
			}
		});

		dialogBuilder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				new ItemRegisterDialogFragment(position).show(getFragmentManager(), ItemRegisterDialogFragment.class.getSimpleName());
				dialog.dismiss();
			}
		});

		dialogBuilder.create().show();
	}

	//MÉTODO DE CARREGAMENTO DA ListView
	public void loadListViewItems() {
		setListAdapter(new ItemsAdapter(this, new int[] { R.layout.list_view_items_dark }, new int[] { R.id.text_view_items_name }, ItemsManager.getInstance().getItems()));
	}
	
	//CRIAÇÃO DOS LISTENERS DE DRAG AND DROP
	private DropListener mDropListener = new DropListener() {
		public void onDrop(int from, int to) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof ItemsAdapter) {
				((ItemsAdapter) adapter).onDrop(from, to);
				getListView().invalidateViews();
			}
		}
	};

	private RemoveListener mRemoveListener = new RemoveListener() {
		public void onRemove(int which) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof ItemsAdapter) {
				((ItemsAdapter) adapter).onRemove(which);
				getListView().invalidateViews();
			}
		}
	};

	private DragListener mDragListener = new DragListener() {

		int backgroundColor = 0xe0103010;
		Drawable defaultBackgroundColor;

		public void onDrag(int x, int y, ListView listView) {
		}

		public void onStartDrag(View itemView) {
			itemView.setVisibility(View.INVISIBLE);
			defaultBackgroundColor = itemView.getBackground();
			itemView.setBackgroundColor(backgroundColor);
			ImageView iv = (ImageView) itemView.findViewById(R.id.image_view_items_image);
			if (iv != null) iv.setVisibility(View.INVISIBLE);
		}

		@SuppressWarnings("deprecation")
		public void onStopDrag(View itemView) {
			itemView.setVisibility(View.VISIBLE);
			itemView.setBackgroundDrawable(defaultBackgroundColor);
			ImageView iv = (ImageView) itemView.findViewById(R.id.image_view_items_image);
			if (iv != null) iv.setVisibility(View.VISIBLE);
		}

	};
}