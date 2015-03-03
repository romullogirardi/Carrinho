package com.romullogirardi.carrinho2itemsamount.view;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.romullogirardi.carrinho.R;
import com.romullogirardi.carrinho1items.model.Item;
import com.romullogirardi.carrinho1items.model.ItemStatus;
import com.romullogirardi.carrinho1items.model.ItemsManager;
import com.romullogirardi.carrinho2itemsamount.model.ItemsAmountAdapter;

public class ItemsAmountActivity extends Activity {

	//ELEMENTOS DA INTERFACE GRÁFICA
	private ListView itemsAmountListView;
	private TextView itemsAmountTextView;

	//MÉTODOS DO CICLO DE VIDA DA Activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_amount);

		itemsAmountListView = (ListView) findViewById(R.id.list_view_items_amount);
		itemsAmountTextView = (TextView) findViewById(R.id.text_view_items_amount);

		if (!ItemsManager.getInstance().getItemsSelected().isEmpty()) {
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle("Nova lista de compras");
			dialogBuilder.setMessage("Deseja apagar a lista de compras anterior e criar uma nova?");

			dialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					loadItemsAmountListView();
					itemsAmountTextView.setVisibility(View.VISIBLE);
					dialog.dismiss();
				}
			});

			dialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					resetSettings();
					loadItemsAmountListView();
					itemsAmountTextView.setVisibility(View.VISIBLE);
					dialog.dismiss();
				}
			});

			dialogBuilder.create().show();
		}
		else {
			itemsAmountTextView.setVisibility(View.VISIBLE);
			loadItemsAmountListView();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items_amount, menu);
		return true;
	}

	//OUTROS MÉTODOS
	private void loadItemsAmountListView() {
		itemsAmountListView.setAdapter(new ItemsAmountAdapter(this, ItemsManager.getInstance().getItems()));
		updateTotalValue();
	}
	
	public void updateTotalValue() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				itemsAmountTextView.setText("Total estimado: R$ " + decimalFormat.format(ItemsManager.getInstance().getTotalValue()));
			}
		});
	}
	
	private void resetSettings() {
		ItemsManager.getInstance().getItemsSelected().removeAllElements();

		for (Item item : ItemsManager.getInstance().getItems()) {
			item.setAmount(0);
			item.setStatus(ItemStatus.NONE);
		}
	}
}