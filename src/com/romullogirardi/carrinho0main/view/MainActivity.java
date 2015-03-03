package com.romullogirardi.carrinho0main.view;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.romullogirardi.carrinho0main.model.ItemsSelectedAdapter;
import com.romullogirardi.carrinho0main.model.GlobalReferences;
import com.romullogirardi.carrinho1items.model.Item;
import com.romullogirardi.carrinho1items.model.ItemStatus;
import com.romullogirardi.carrinho1items.model.ItemsManager;
import com.romullogirardi.carrinho1items.view.ItemsActivity;
import com.romullogirardi.carrinho2itemsamount.view.ItemsAmountActivity;
import com.romullogirardi.smartlist.R;

public class MainActivity extends Activity implements OnItemClickListener {

	//ELEMENTOS DA INTERFACE GRÁFICA
	private ListView itemsSelectedListView;
	private TextView totalValueTextView;
	private TextView purchasedValueTextView;
	private TextView emptyListTextView;

	//MÉTODOS DO CICLO DE VIDA DA Activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Setar referência global para o contexto da aplicação
		GlobalReferences.applicationContext = this;

		//Persistir o estado da aplicação
		ItemsManager.getInstance().readFile();

		itemsSelectedListView = (ListView) findViewById(R.id.list_view_items_selected);
		totalValueTextView = (TextView) findViewById(R.id.text_view_items_selected_total_value);
		purchasedValueTextView = (TextView) findViewById(R.id.text_view_items_selected_purchased);
		emptyListTextView = (TextView) findViewById(R.id.text_view_items_selected_empty);

		itemsSelectedListView.setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadItemsSelectedListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_items_amount) {
			startActivity(new Intent(this, ItemsAmountActivity.class));
		}
		else {
			startActivity(new Intent(this, ItemsActivity.class));
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Sair");
		dialogBuilder.setMessage("Deseja sair do aplicativo?");

		dialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		dialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ItemsManager.getInstance().saveFile();
				dialog.dismiss();
				finish();
			}
		});
		
		dialogBuilder.create().show();
	}

	//IMPLEMENTAÇÃO DE MÉTODO DE OnItemClickListener
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
		//Pegar, no modelo, a referência para o item clicado pelo usuário
		Item itemSelected = (Item) ItemsManager.getInstance().getItemsSelected().get(position);
		
		//Setar o status atual do item de acordo com o seu estado anterior
		if (itemSelected.getStatus().equals(ItemStatus.NONE)) {
			itemSelected.setStatus(ItemStatus.PURCHASED);
		}
		else if (itemSelected.getStatus().equals(ItemStatus.PURCHASED)) {
			itemSelected.setStatus(ItemStatus.NOT_FOUND);
		}
		else {
			itemSelected.setStatus(ItemStatus.NONE);
		}

		//Atualizar o item na interface gráfica
		parent.getAdapter().getView(position, view, parent);

		//Atualizar o total no carrinho
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		purchasedValueTextView.setText("Total no carrinho: R$ " + decimalFormat.format(ItemsManager.getInstance().getPurchasedValue()));

		//Apresentar dialog de compra finalizada, caso necessário
		if (ItemsManager.getInstance().isShoppingFinished()) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
					dialogBuilder.setTitle("Compra finalizada");
					dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					dialogBuilder.create().show();
				}
			});
		}
	}

	//OUTROS MÉTODOS
	private void loadItemsSelectedListView() {
		if (!ItemsManager.getInstance().getItemsSelected().isEmpty()) {
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			totalValueTextView.setText("Total estimado: R$ " + decimalFormat.format(ItemsManager.getInstance().getTotalValue()));
			itemsSelectedListView.setAdapter(new ItemsSelectedAdapter(this, ItemsManager.getInstance().getItemsSelected()));
			purchasedValueTextView.setText("Total no carrinho: R$ " + decimalFormat.format(ItemsManager.getInstance().getPurchasedValue()));
			totalValueTextView.setVisibility(View.VISIBLE);
			itemsSelectedListView.setVisibility(View.VISIBLE);
			purchasedValueTextView.setVisibility(View.VISIBLE);
			emptyListTextView.setVisibility(View.GONE);
		}
		else {
			totalValueTextView.setVisibility(View.GONE);
			itemsSelectedListView.setVisibility(View.GONE);
			purchasedValueTextView.setVisibility(View.GONE);
			emptyListTextView.setVisibility(View.VISIBLE);
		}
	}
}