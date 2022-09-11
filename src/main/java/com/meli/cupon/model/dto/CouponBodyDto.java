package com.meli.cupon.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponBodyDto implements Serializable {
	
	@JsonProperty(value = "item_ids")
	private String[] itemIds;
	
	@JsonProperty(value = "amount")
	private Double amount;
	
	public CouponBodyDto() {
		super();
	}

	public CouponBodyDto(String[] itemIds, Double amount) {
		super();
		this.itemIds = itemIds;
		this.amount = amount;
	}

	public String[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(String[] itemIds) {
		this.itemIds = itemIds;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	} 
	
}
