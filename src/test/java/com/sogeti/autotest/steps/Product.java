package com.sogeti.autotest.steps;

import java.util.ArrayList;


//Task 4
public class Product {

	private String sellingPrice;
	private String wasPrice;
	private String sku;
	private String rrp;
	private String styleName;
	private String size;
	private String width;
	private String qty;
	private String colour;
	private ArrayList<String> listOfSizes;
	private ArrayList<String> listOfWidths;

	public ArrayList <String> getListOfSizes() {
		return listOfSizes;
	}

	public void setListOfSizes(ArrayList <String> listOfSizes) {
		this.listOfSizes = listOfSizes;
	}

	public ArrayList <String> getListOfWidths() {
		return listOfWidths;
	}

	public void setListOfWidths(ArrayList <String> listOfWidths) {
		this.listOfWidths = listOfWidths;
	}




	public Product() {
	}

	public Product(String sku, String price, String styleName, String rrp, String wasPrice) {
		this.sku = sku;
		this.sellingPrice = price;
		this.wasPrice = wasPrice;
		this.rrp = rrp;
		this.styleName = styleName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public Product withSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
		return this;
	}

	public String getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Product withWasPrice(String wasPrice) {
		this.wasPrice = wasPrice;
		return this;
	}

	public String getWasPrice() {
		return wasPrice;
	}

	public void setWasPrice(String wasPrice) {
		this.wasPrice = wasPrice;
	}

	public Product withRrp(String rrp) {
		this.rrp = rrp;
		return this;
	}

	public String getRrp() {
		return rrp;
	}

	public void setRrp(String rrp) {
		this.rrp = rrp;
	}

	public Product withSku(String sku) {
		this.sku = sku;
		return this;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Product withStyleName(String styleName) {
		this.styleName = styleName;
		return this;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
}
