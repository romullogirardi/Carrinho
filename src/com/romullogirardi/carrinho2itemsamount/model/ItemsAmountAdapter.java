package com.romullogirardi.carrinho2itemsamount.model;

import java.util.Vector;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.romullogirardi.carrinho.R;
import com.romullogirardi.carrinho1items.model.Item;
import com.romullogirardi.carrinho1items.model.ItemsManager;
import com.romullogirardi.carrinho2itemsamount.view.ItemsAmountActivity;

public class ItemsAmountAdapter extends BaseAdapter {
	
	//ATRIBUTOS
	private Activity context;
	private LayoutInflater inflater;
	private Vector<Item> itemsAmount;

	//CONSTRUTOR
	public ItemsAmountAdapter(Activity context, Vector<Item> itemsAmount) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.itemsAmount = itemsAmount;
	}

	//IMPLEMENTAÇÃO DE MÉTODOS DE BaseAdapter
	@Override
	public int getCount() {
		return itemsAmount.size();
	}

	@Override
	public Object getItem(int position) {
		return itemsAmount.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_view_items_amount, null);
			viewHolder = new ViewHolder();
			viewHolder.itemAmountTextView = (TextView) convertView.findViewById(R.id.list_view_item_amount_text_view);
			viewHolder.itemAmountSpinner = (Spinner) convertView.findViewById(R.id.list_view_item_amount_spinner);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final Item item = itemsAmount.get(position);
		Integer[] amount = new Integer[34];
		for (int index = 0; index <= 24; index++) {
			amount[index] = index;
		}
		amount[25] = 100;
		amount[26] = 150;
		amount[27] = 200;
		amount[28] = 250;
		amount[29] = 300;
		amount[30] = 350;
		amount[31] = 400;
		amount[32] = 450;
		amount[33] = 500;
		viewHolder.itemAmountSpinner.setAdapter(new ArrayAdapter<Integer>(context, R.layout.list_view_simple_item, amount));
		viewHolder.itemAmountSpinner.setSelection(getIndex(amount, item.getAmount()));
		viewHolder.itemAmountTextView.setText(item.itemAmountToString());
		viewHolder.itemAmountSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
				//Pegar o índice deste item no Vector itemsSelected, caso exista
				int index = ItemsManager.getInstance().getItemsSelected().indexOf(item);

				//Atualizar o atributo amount do item da view selecionada
				int itemAmount = (Integer) ((Spinner) parent).getSelectedItem();
				item.setAmount(itemAmount);

				//Atualizar o Vector itemsSelected com relação ao item da view selecionada
				if (itemAmount != 0) {
					//Adicionar
					if (index == -1) {
						ItemsManager.getInstance().addItemSelected(item);
					}
					//Alterar já existente
					else {
						ItemsManager.getInstance().updateItemSelected(index, item);
					}
				}
				else {
					//Remover
					if (index != -1) {
						ItemsManager.getInstance().removeItemSelected(item);
					}
				}
				
				//Atualizar o Valor Total na ItemsAmountActivity
				((ItemsAmountActivity) context).updateTotalValue();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		
		return convertView;
	}

	private int getIndex(Integer[] array, int value) {
		for (int index = 0; index < array.length; index++) {
			if (array[index] == value) {
				return index;
			}
		}
		return -1;
	}

	//CLASSE INTERNA ViewHolder 
	private class ViewHolder {
		Spinner itemAmountSpinner;
		TextView itemAmountTextView;
	}
}