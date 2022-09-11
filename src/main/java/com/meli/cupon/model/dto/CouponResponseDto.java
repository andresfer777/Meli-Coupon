package com.meli.cupon.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponResponseDto implements Serializable {
	
	@JsonProperty(value = "item_ids")
	private String[] itemIds;
	
	@JsonProperty(value = "total")
	private Double total;
	
	public CouponResponseDto() {
		super();
	}

	public CouponResponseDto(String[] itemIds, Double total) {
		super();
		this.itemIds = itemIds;
		this.total = total;
	}

	public String[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(String[] itemIds) {
		this.itemIds = itemIds;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
}
