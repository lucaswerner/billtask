package com.werner.billtask.model;

public class Bill extends PersonDescription {

	private Double total;
	
	public Bill(Long id, String name, String number, Double total) {
		super(id, name, number);
		this.total = total;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
