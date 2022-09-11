package com.meli.cupon.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class ItemEntity {
	
	@Id
	@Column(name = "ITEM_ID", length = 5)
	private String itemId;
	
	@Column(name = "PRICE", scale = 5, precision = 2)
	private String price;

	public ItemEntity() {
		super();
	}

	public ItemEntity(String itemId, String price) {
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
