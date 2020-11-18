package com.sogeti.autotest.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class Order
{
	private String site;
	private String customerType;
	private String nonHandbagQty;
	private String handbagQty;
	private String voucherCode;
	private String deliveryType;
	private String fulfillmentType;
	private String deliveryMethod;
	private String paymentType;
	private String orderNumber;
	private String orderEmail;
	private String password;
	private String addressType;
	private String worldpayReferenceNumber;
	private Boolean orderPlacementSuccess;
	private String cfsType;
	private String orderSeq;

	public String getAddressType()
	{
		return addressType;
	}

	public Order setAddressType(String addressType)
	{
		this.addressType = addressType;
		return this;
	}

	public String getOrderSeq()
	{
		return orderSeq;
	}

	public Order setOrderSeq(String orderSeq)
	{
		this.orderSeq = orderSeq;
		return this;
	}



	public void assignOrderDetails(String[] orderDetails)
	{

		if (StringUtils.isNotEmpty(orderDetails[0]))
			setOrderSeq(orderDetails[0]);
		if (StringUtils.isNotEmpty(orderDetails[1]))
			setSite(orderDetails[1]);
		if (StringUtils.isNotEmpty(orderDetails[2]))
			setCustomerType(orderDetails[2]);
		if (StringUtils.isNotEmpty(orderDetails[3]))
			setNonhandbagQty(orderDetails[3]);
		if (StringUtils.isNotEmpty(orderDetails[4]))
			setHandbagQty(orderDetails[4]);
		if (StringUtils.isNotEmpty(orderDetails[5]))
			setPaymentType(orderDetails[5]);
		if (StringUtils.isNotEmpty(orderDetails[6]))
			setFulfillmentType(orderDetails[6]);
		if (StringUtils.isNotEmpty(orderDetails[7]))
			setDeliveryMethod(orderDetails[7]);
		if (StringUtils.isNotEmpty(orderDetails[8]))
			setOrderEmail(orderDetails[8]);
		if (StringUtils.isNotEmpty(orderDetails[9]))
			setPassword(orderDetails[9]);
		if (StringUtils.isNotEmpty(orderDetails[10]))
			setAddressType(orderDetails[10]);
		if (StringUtils.isNotEmpty(orderDetails[11]))
			setVoucherCode(orderDetails[11]);
		if (StringUtils.isNotEmpty(orderDetails[12]))
			setCfsType(orderDetails[12]);


	}

	public List<String> exportOrderDetails()
	{
		List<String> orderDetails = new ArrayList<String>();
		//List<String> orderDetails = new List;
		orderDetails.add(this.getSite());
		orderDetails.add(this.getCustomerType());
		orderDetails.add(this.getNonHandbagQty());
		orderDetails.add(this.getHandbagQty());
		orderDetails.add(this.getVoucherCode());
		orderDetails.add(this.getDeliveryType());
		orderDetails.add(this.getDeliveryMethod());
		orderDetails.add(this.getPaymentType());
		orderDetails.add(this.getOrderPlacementSuccess().toString());
		orderDetails.add(this.getOrderNumber());
		orderDetails.add(this.getWorldpayReferenceNumber());
		orderDetails.add(this.getOrderEmail());
		orderDetails.add(this.getPassword());
		orderDetails.add(this.getCfsType());
		orderDetails.add(this.getOrderSeq());

		return orderDetails;
	}

	public String getCustomerType()
	{
		return customerType;
	}

	public Order setCustomerType(String customerType)
	{
		this.customerType = customerType;
		return this;
	}

	public String getCfsType()
	{
		return cfsType;
	}

	public Order setCfsType(String cfsType)
	{
		this.cfsType = cfsType;
		return this;
	}

	public String getNonHandbagQty()
	{
		return nonHandbagQty;
	}

	public Order setNonhandbagQty(String nonHandbagQty)
	{
		this.nonHandbagQty = nonHandbagQty;
		return this;
	}

	public String getHandbagQty()
	{
		return handbagQty;
	}

	public Order setHandbagQty(String handbagQty)
	{
		this.handbagQty = handbagQty;
		return this;
	}

	public String getVoucherCode()
	{
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode)
	{
		this.voucherCode = voucherCode;
	}

	public static String padVoucherCode(String vouchercode, String vouchercodespace)
	{
		switch (vouchercodespace)
		{
			case "leading":
			case "vouchercodespacelead":
				//Add a leading space
				vouchercode = " " + vouchercode;
				break;

			case "leadingdouble":
			case "vouchercodespaceleaddouble":
				//Add a leading space
				vouchercode = "  " + vouchercode;
				break;

			case "trailing":
			case "vouchercodespacetrail":
				//Add a trailing space
				vouchercode = vouchercode + " ";
				break;

			case "trailingdouble":
			case "vouchercodespacetraildouble":
				//Add a trailing space
				vouchercode = vouchercode + "  ";
				break;

			case "wrapped":
			case "vouchercodespacewrap":
				//Add a space both ends
				vouchercode = " " + vouchercode + " ";
				break;

			case "wrappeddouble":
			case "vouchercodespacewrapdouble":
				//Add a space both ends
				vouchercode = "  " + vouchercode + "  ";
				break;

			case "insertin":
				vouchercode = new StringBuilder(vouchercode).insert(vouchercode.length() / 2, " ").toString();
				break;

			case "tolowercase":
				vouchercode = vouchercode.toLowerCase();
				break;

			default:
				//no spaces
				break;
		}

		return vouchercode;
	}

	public String getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public String getWorldpayReferenceNumber()
	{
		return worldpayReferenceNumber;
	}

	public Order setWorldpayReferenceNumber(String worldpayReferenceNumber)
	{
		this.worldpayReferenceNumber = worldpayReferenceNumber;
		return this;
	}

	public Boolean getOrderPlacementSuccess()
	{
		return orderPlacementSuccess;
	}

	public Order setOrderPlacementSuccess(boolean b)
	{
		this.orderPlacementSuccess = b;
		return this;
	}

	public String getDeliveryType()
	{
		return deliveryType;
	}

	public Order setDeliveryType(String deliveryType)
	{
		this.deliveryType = deliveryType;
		return this;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public Order setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
		return this;
	}

	public String getDeliveryMethod()
	{
		return deliveryMethod;
	}

	public Order setDeliveryMethod(String deliveryMethod)
	{
		this.deliveryMethod = deliveryMethod;
		return this;
	}

	public String getOrderEmail()
	{
		return orderEmail;
	}

	public Order setOrderEmail(String orderEmail)
	{
		this.orderEmail = orderEmail;
		return this;
	}

	public String getPassword()
	{
		return password;
	}

	public Order setPassword(String password)
	{
		this.password = password;
		return this;
	}

	public String getSite()
	{
		return site;
	}

	public Order setSite(String site)
	{
		this.site = site;
		return this;
	}

	public String getFulfillmentType()
	{
		return fulfillmentType;
	}

	public Order setFulfillmentType(String fulfillmentType)
	{
		this.fulfillmentType = fulfillmentType;
		return this;
	}
}
