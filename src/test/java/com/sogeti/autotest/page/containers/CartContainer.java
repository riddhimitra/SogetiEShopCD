package com.sogeti.autotest.page.containers;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CartContainer {

	protected static String containingClass(String className) {
		return "contains(concat(' ',normalize-space(@class),' '),' " + className + " ')";
	}
	@FindBy(how = How.NAME, using = "updatebutton")
	public WebElement updateButton;

	@FindBy(how = How.CSS, using = "section.col-xs-4 a.esh-basket-checkout")
	public WebElement checkOutButton;
	
	@FindBy(how = How.CSS, using = "[name='quantity']")
	public static WebElement quantity;

	@FindBy(how = How.CSS, using = "[data-test^='increase-btn-']")
	public static WebElement IncreaseQty;

	@FindBy(how = How.CSS, using = "[data-test^='increase-btn-']")
	public static WebElement UpdateQty;

	@FindBy(how = How.CSS, using = "[data-test^='decrease-btn-']")
	public static WebElement DecreaseQty;

	@FindBy(how = How.CSS, using = ".cart-product-list")
	public static WebElement cartProductDetails;

	@FindBy(how = How.ID, using = "vouhcerCode")
	public static WebElement voucherCodeInput;

	@FindBy(how = How.CSS, using = "button.js-remove-promo-code")
	public static WebElement removePromoCode;

	@FindBy(how = How.XPATH, using = "//div[@class='est-order-summary-container col-xs-12 col-sm-6']//div[@class='order-row promo-applied  ']")
	public static WebElement validateDiscountPromoCode;

	@FindBy(how = How.CSS, using = "dd.voucherDiscountsDiv")
	public static WebElement discountPrice;

	@FindBy(how = How.CSS, using = ".js-apply-promo-code")
	public static WebElement applyCodeButton;

	@FindBy(how = How.CSS, using = ".success-container >p")
	public static WebElement voucherApplySuccess;

	@FindBy(how = How.CSS, using = "#invalid-code-error,p.invalid-code-error")
	public List<WebElement> voucherApplyInvalid;

	@FindBy(how = How.CSS, using = ".invalid-code-error")
	public List<WebElement> voucherErrorMessage;

	@FindBy(how = How.CSS, using = "[data-test='remove-product-link']")
	public List<WebElement> removeProduct;

	@FindBy(how = How.CSS, using = "h1.shoppingbag-header")
	public WebElement shoppingBagHeader;

	@FindBy(how = How.CSS, using = "select.delivery-date")
	public WebElement deliveryMode;

	public static final String deliveryErrorMsg = "div.js-delivery-mode-msg";

	@FindBy(how = How.XPATH, using = "//select[@id='selectDeliveryMode']")
	public WebElement collectPlusDirectDropdown;

	@FindBy(how = How.XPATH, using = "//select[@class='delivery-date']")
	public WebElement pudoDEDirectDropdown;

	@FindBy(how = How.XPATH, using = "//select[@id='selectDeliveryMode']")
	public WebElement expressdaydeliveryeu;

	@FindBy(how = How.XPATH, using = "//select[@id='selectDeliveryMode']")
	public WebElement expressdaydeliveryde;

	@FindBy(how = How.XPATH, using = "//select[@id='selectDeliveryMode']")
	public WebElement nextdaydeliveryuk;

	@FindBy(how = How.XPATH, using = "//select[@id='selectDeliveryMode']")
	public WebElement deliveryMode_Direct_clickONcfs;

	@FindBy(how = How.CSS, using = "[data-test='checkout-button']")
	public WebElement checkoutNowbutton;

	@FindBy(how = How.CSS, using = ".cart-product-list__product")
	public List<WebElement> cartitems;

	@FindBy(how = How.CSS, using = "input[name='quantity'][id^='updateQuantityForm']")
	public List<WebElement> cartQty;

	@FindBy(how = How.CSS, using = ".cart-product-list__product-price [data-test='selling-price']")
	public List<WebElement> cartUnitPrice;

	@FindBy(how = How.CSS, using = ".cart-product-list__product-price [data-test='was-price']")
	public List<WebElement> cartWasPrice;

	@FindBy(how = How.CSS, using = "span.bag-id")
	public WebElement cartId;

	@FindBy(how = How.ID, using = "orderType")
	public WebElement orderType;

	@FindBy(how = How.NAME, using = "inputASMOriginalId")
	public WebElement asmInputOrderID;

	@FindBy(how = How.CSS, using = "span.asm-message")
	public WebElement orderNumberValidated;

	@FindBy(css = ".text-right.row-data.subTotalDiv")
	public WebElement subTotalPrice;

	@FindBy(how = How.CSS, using = "select[id^='size']")
	public List<WebElement> sizeSelectDropDown;

	@FindBy(how = How.CSS, using = "select[id^='width']")
	public List<WebElement> widthSelectDropDown;

	@FindBy(how = How.CSS, using = "div.cart-product-list__product-details-size-width")
	public List<WebElement> sizeWidthCartEntries;

	public String cartProductsSizeWidth = ".cart-product-list__product-details-size-width";

	public String productsInCart = "li[id*=cart-product-]";

	@FindBy(how = How.CSS, using = "span.item-font,div.cart-product-list__product-details-color")
	public List<WebElement> productColour;

	public String sizeSelectWithProdCode = "div[id='cart-product-replace'] select[id^='size']";
	public String widthSelectWithProdCode = "div[id='cart-product-replace'] select[id^='width']";

	public String sizeDisplayWithProdCode = "div[id='cart-product-replace'] label[for^=size]+p";
	public String widthDisplayWithProdCode = "div[id='cart-product-replace'] label[for^=width]+p";

	@FindBy(how = How.CSS, using = ".infobox-cart")
	public WebElement nextDayDeliveryUnavailableModule;

	@FindBy(how = How.CSS, using = "#next-day-shipping>span>span>del")
	public WebElement nextDayDeliveryInfoTextUnavailable;

	@FindBy(how = How.CSS, using = "#pudo>span>span>del")
	public WebElement pudoInfoTextUnavailable;

	@FindBy(how = How.CSS, using = ".js-get-size")
	public WebElement prodSize;

	@FindBy(how = How.CSS, using = ".js-get-width")
	public WebElement prodWidth;
}