package com.romullogirardi.carrinho1items.model;

import java.io.Serializable;

public enum ItemMeasureUnit implements Serializable {
	UNIDADE("unidade(s)"), CAIXA("caixa(s)"), PACOTE("pacote(s)"), KILO("kilo(s)"), LITRO("litro(s)"), GRAMA("grama(s)"), POTE("pote(s)"), LATA("lata(s)");

	//Atributo do enum
	private String text;

	//Construtor do enum
	private ItemMeasureUnit(String text) {
		this.text = text;
	}

	//Getter
	public String getText() {
		return text;
	}
}