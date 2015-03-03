package com.romullogirardi.carrinho1items.model;

import java.io.Serializable;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.romullogirardi.carrinho.R;
import com.romullogirardi.carrinho0main.model.GlobalReferences;
import com.romullogirardi.carrinhoutils.FileManipulator;

public class ItemsManager implements Serializable {
	private static final long serialVersionUID = -5893144296262452631L;

	//ATRIBUTOS
	private static ItemsManager instance = null;
	private Vector<Item> items = new Vector<Item>();
	private Vector<Item> itemsSelected = new Vector<Item>();
	private final String FILE_PATH = GlobalReferences.applicationContext.getFilesDir().getAbsolutePath() + "/smartlist.szb";

	//Id da imagem default para os itens
	private int defaultDrawableId = R.drawable.ic_launcher_cart;

	//IMPLEMENTAÇÃO COMO SINGLETON
	public static ItemsManager getInstance() {
		if (instance == null) {
			instance = new ItemsManager();
		}
		return instance;
	}

	//GETTERS E SETTERS
	public Vector<Item> getItems() {
		return items;
	}

	public Vector<Item> getItemsSelected() {
		return itemsSelected;
	}

	//OUTROS MÉTODOS
	public void addItem(Item item) {
		items.add(item);

		//Salvar o estado atualizado no arquivo de persistência
		saveFile();
	}

	public void addItemSelected(Item item) {
		itemsSelected.add(item);

		//Salvar o estado atualizado no arquivo de persistência
		saveFile();
	}

	public void removeItem(Item item) {
		items.remove(item);

		//Salvar o estado atualizado no arquivo de persistência
		saveFile();
	}

	public void updateItem(int index, Item item) {
		items.set(index, item);

		//Salvar o estado atualizado no arquivo de persistência
		saveFile();
	}

	public void removeItemSelected(Item item) {
		itemsSelected.remove(item);

		//Salvar o estado atualizado no arquivo de persistência
		saveFile();
	}

	public void updateItemSelected(int index, Item item) {
		itemsSelected.set(index, item);

		//Salvar o estado atualizado no arquivo de persistência
		saveFile();
	}

	public float getTotalValue() {
		float totalValue = 0;
		for (Item item : items) {
			totalValue += item.getAmount() * item.getValue();
		}
		return totalValue;
	}

	public float getPurchasedValue() {
		float purchasedValue = 0;
		for (Item item : items) {
			if (item.getStatus().equals(ItemStatus.PURCHASED)) {
				purchasedValue += item.getAmount() * item.getValue();
			}
		}
		return purchasedValue;
	}

	public boolean isShoppingFinished() {
		for (Item item : itemsSelected) {
			if (item.getStatus().equals(ItemStatus.NONE)) {
				return false;
			}
		}
		return true;
	}

	private Vector<Item> getDefaultItems() {
		Vector<Item> defaultItems = new Vector<Item>();

		Item item;
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "GRANOLA", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "BATATA PALHA", 6);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.POTE, "TODDY", 8);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "FAROFA PRONTA", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "QUEIJO RALADO", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "ORÉGANO", 3);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "BARRA DE CHOCOLATE", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "MIOJO", 2);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.CAIXA, "CREME DE LEITE", 3);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.LATA, "LEITE CONDENSADO", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.LATA, "MILHO", 3);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "CHEETOS", 7);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "BARRA DE CEREAL", 3);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "ISOTÔNICO", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "NESFIT", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.KILO, "ARROZ", 6);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.KILO, "FEIJÃO", 6);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.KILO, "AÇÚCAR", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.KILO, "SAL", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "AZEITE", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.LITRO, "LEITE", 3.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.POTE, "KETCHUP", 6);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.POTE, "MOSTARDA", 6);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.POTE, "REQUEIJÃO", 6);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "CHEDDAR", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.POTE, "GELÉIA", 10);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.LITRO, "SUCO", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.LITRO, "CHÁ", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.LITRO, "VODKA", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "OVO", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.LATA, "CERVEJA", 2.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "PÃO", 6);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "BOLO", 8);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.GRAMA, "PEITO DE PERU", 0.04);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "RICOTA", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.GRAMA, "QUEIJO BOLA", 0.05);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "BANANA", 0.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "BATATA", 0.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "CENOURA", 0.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "TOMATE", 0.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "CEBOLA", 0.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "ALHO", 0.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.KILO, "UVA", 20);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.CAIXA, "PIZZA", 10);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "SALSICHA", 8);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "PÃO DE QUEIJO", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.CAIXA, "NUGGETS", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "FRANGO", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "HOT POCKET", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "PAPEL HIGIÊNICO", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "X-14", 12);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "LIMPA VIDRO", 8);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "DESINFETANTE", 5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "PERFEX", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "VEJA MULTIUSO", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "DETERGENTE", 2);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "ESPONJA", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "SABÃO LÍQUIDO", 10);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "AMACIANTE", 10);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "VANISH", 10);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "SHAMPOO", 10);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "CONDICIONADOR", 10);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.POTE, "ANTISÉPTICO BUCAL", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "SABONETE", 2.5);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "SABONETE DE ROSTO", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.PACOTE, "ESCOVA DE DENTE", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.CAIXA, "PASTA DE DENTE", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.CAIXA, "COTONETE", 4);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "DESODORANTE", 7);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "ESPUMA DE BARBEAR", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "GILETE DE BARBEAR", 15);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "TALCO EM PÓ", 8);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "TALCO SPRAY", 12);
		defaultItems.add(item);
		item = new Item(defaultDrawableId, ItemMeasureUnit.UNIDADE, "ANTIMOFO", 6);
		defaultItems.add(item);

		return defaultItems;
	}

//	public Bitmap adjustBitmap(Bitmap bitmap) {
//		Bitmap adjustedBitmap;
//		if (bitmap.getWidth() >= bitmap.getHeight()) {
//			adjustedBitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2 - bitmap.getHeight() / 2, 0, bitmap.getHeight(), bitmap.getHeight());
//		}
//		else {
//			adjustedBitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2 - bitmap.getWidth() / 2, bitmap.getWidth(), bitmap.getWidth());
//		}
//		return adjustedBitmap;
//	}

	public Bitmap adjustBitmap(String picturePath) {
		Bitmap bitmap;
		final int requestedWidth = 200;
		final int requestedHeight = 200;

		//First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(picturePath, options);

		//Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, requestedWidth, requestedHeight);

		//Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(picturePath, options);

		Bitmap adjustedBitmap;

		//Ajustando a imagem para o formato quadrado
		if (bitmap.getWidth() >= bitmap.getHeight()) {
			adjustedBitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2 - bitmap.getHeight() / 2, 0, bitmap.getHeight(), bitmap.getHeight());
		}
		else {
			adjustedBitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2 - bitmap.getWidth() / 2, bitmap.getWidth(), bitmap.getWidth());
		}

		return adjustedBitmap;
	}

	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	//MÉTODOS DE MANIPULAÇÃO DE ARQUIVO
	public void readFile() {
		FileManipulator fileManipulator = new FileManipulator();
		Object object = fileManipulator.readObj(FILE_PATH);
		if (object != null) {
			instance = (ItemsManager) object;
		}
		else {
			items = getDefaultItems();
		}
	}

	public void saveFile() {
		FileManipulator fileManipulator = new FileManipulator();
		fileManipulator.saveObject(FILE_PATH, this);
	}
}