package com.romullogirardi.carrinho1items.model;

import java.io.Serializable;
import java.text.DecimalFormat;


public class Item implements Serializable {
	private static final long serialVersionUID = 7953557974228103535L;

	//ATRIBUTOS
	private int amount;
	private Object imageReference = null;
	private ItemMeasureUnit measureUnit;
	private String name;
	private double value;
	private ItemStatus status;

	//CONSTRUTORES
	public Item() {
		this.amount = 0;
		this.status = ItemStatus.NONE;
	}

	public Item(Object imageReference, ItemMeasureUnit measureUnit, String name, double value) {
		this.amount = 0;
		this.imageReference = imageReference;
		this.measureUnit = measureUnit;
		this.name = name;
		this.value = value;
		this.status = ItemStatus.NONE;
	}

	//GETTERS E SETTERS
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Object getImageReference() {
		return imageReference;
	}

	public void setImageReference(Object imageReference) {
		this.imageReference = imageReference;
	}

	public ItemMeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(ItemMeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public ItemStatus getStatus() {
		return status;
	}

	public void setStatus(ItemStatus status) {
		this.status = status;

		//Salvar o estado atualizado no arquivo de persistência
		ItemsManager.getInstance().saveFile();
	}

	//OUTROS MÉTODOS
	public String itemSelectedToString() {
		return amount + " " + measureUnit.getText() + " de " + name;
	}

	public String itemAmountToString() {
		return measureUnit.getText() + " de " + name;
	}

	public String itemToString() {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return name + " - R$ " + decimalFormat.format(value);
	}
}