package com.romullogirardi.carrinho1items.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.romullogirardi.carrinho.R;
import com.romullogirardi.carrinho1items.model.Item;
import com.romullogirardi.carrinho1items.model.ItemMeasureUnit;
import com.romullogirardi.carrinho1items.model.ItemsManager;

public class ItemRegisterDialogFragment extends DialogFragment implements OnClickListener {

	//ELEMENTOS DA INTERFACE GRÁFICA
	private View view;
	private ImageView itemImageImageView;
	private Spinner itemMeasureUnitSpinner;
	private EditText itemNameEditText;
	private EditText itemValueEditText;

	//ATRIBUTOS
	private int itemPosition;

	//FLAGS
	private final int SUCCESS_FLAG = 1;

	//CONSTRUTOR
	public ItemRegisterDialogFragment(int itemPosition) {
		this.itemPosition = itemPosition;
	}

	//MÉTODOS DO CICLO DE VIDA DO DialogFragment
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//Inicialização dos elementos do dialog
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view = mInflater.inflate(R.layout.dialog_fragment_new_item, null);
		itemImageImageView = (ImageView) view.findViewById(R.id.image_view_dialog_fragment_new_item_image);
		itemMeasureUnitSpinner = (Spinner) view.findViewById(R.id.spinner_dialog_fragment_new_item_measure_unit);
		itemNameEditText = (EditText) view.findViewById(R.id.edit_text_dialog_fragment_new_item_name);
		itemValueEditText = (EditText) view.findViewById(R.id.edit_text_dialog_fragment_new_item_value);

		itemImageImageView.setOnClickListener(this);
		itemMeasureUnitSpinner.setAdapter(new ArrayAdapter<ItemMeasureUnit>(getActivity(), R.layout.list_view_simple_item, ItemMeasureUnit.values()));

		//Construção do dialog
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setView(view);
		if (itemPosition == ListView.INVALID_POSITION) {
			dialogBuilder.setTitle("Cadastrar item");
			itemImageImageView.setImageResource(R.drawable.ic_add);

			dialogBuilder.setNegativeButton("Cadastrar e fechar", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					addNewItem();
					dialog.dismiss();
				}
			});

			dialogBuilder.setPositiveButton("Continuar cadastrando", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					addNewItem();
					dialog.dismiss();
					new ItemRegisterDialogFragment(ListView.INVALID_POSITION).show(getFragmentManager(), "itemRegisterDialogFragment");
				}
			});
		}
		else {
			dialogBuilder.setTitle("Editar item");

			Item item = ItemsManager.getInstance().getItems().get(itemPosition);
			setImageView(item);
			itemMeasureUnitSpinner.setSelection(item.getMeasureUnit().ordinal());
			itemNameEditText.setText(item.getName());
			itemValueEditText.setText(String.valueOf(item.getValue()));

			dialogBuilder.setNegativeButton("Salvar alterações", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					editItem(itemPosition);
					dialog.dismiss();
				}
			});
		}

		//Criar o dialog
		return dialogBuilder.create();
	}

	private void addNewItem() {
		Object imageReference = itemImageImageView.getTag();
		ItemMeasureUnit measureUnit = ItemMeasureUnit.values()[itemMeasureUnitSpinner.getSelectedItemPosition()];
		String name = itemNameEditText.getText().toString();
		float value = Float.parseFloat(itemValueEditText.getText().toString());
		Item item = new Item(imageReference, measureUnit, name, value);
		ItemsManager.getInstance().getItems().add(item);
		((ItemsActivity) getActivity()).loadListViewItems();
	}

	private void editItem(int itemPosition) {
		Item item = new Item();
		item.setImageReference(itemImageImageView.getTag());
		item.setMeasureUnit(ItemMeasureUnit.values()[itemMeasureUnitSpinner.getSelectedItemPosition()]);
		item.setName(itemNameEditText.getText().toString());
		item.setValue(Float.parseFloat(itemValueEditText.getText().toString()));
		ItemsManager.getInstance().updateItem(itemPosition, item);
		((ItemsActivity) getActivity()).loadListViewItems();
	}

	//MÉTODOS DE MANIPULAÇÃO DE IMAGEM
	//Abrir galeria para a escolha da imagem
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, SUCCESS_FLAG);
	}

	//Receber a imagem escolhida 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SUCCESS_FLAG && resultCode == Activity.RESULT_OK && data != null) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			itemImageImageView.setImageBitmap(ItemsManager.getInstance().adjustBitmap(picturePath));
			itemImageImageView.setTag(picturePath);
		}
	}

	//Setar a imagem na ImageView
	public void setImageView(Item item) {
		if (item.getImageReference() instanceof Integer) {
			itemImageImageView.setImageResource((Integer) item.getImageReference());
		}
		else if (item.getImageReference() instanceof String) {
			itemImageImageView.setImageBitmap(ItemsManager.getInstance().adjustBitmap((String) item.getImageReference()));
		}
		else {
			itemImageImageView.setImageResource(R.drawable.ic_launcher_cart);
		}
		itemImageImageView.setTag(item.getImageReference());
	}
}