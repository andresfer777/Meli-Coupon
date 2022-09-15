package com.meli.cupon.model.dto;

import java.io.Serializable;

public class ItemDto implements Serializable, Comparable<ItemDto>{

	private static final long serialVersionUID = 3532575130857157241L;

	private String itemId;
	
	private Double price;

	public ItemDto() {
		super();
	}

	public ItemDto(String itemId, Double price) {
		super();
		this.itemId = itemId;
		this.price = price;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public int compareTo(ItemDto itemDtoComparado) {
		return this.getPrice().compareTo(itemDtoComparado.getPrice());
	}

	
}
