package com.sogeti.autotest.pangaea2.containers;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MyAccountContainer {

	@FindBy(how = How.XPATH, using = "//a[@href='/my-account/address-book']")
	public WebElement deliveryAddressDetails;

	@FindBy(how = How.XPATH, using = "//*[@id='content']/div[3]/div[2]/table/tbody/tr[5]/td[2]/a")
	public WebElement EditLink;

	@FindBy(how = How.XPATH, using = "//*[@id='accountNav']/ul/li[3]/a")
	public WebElement paymentDetails;

	@FindBy(how = How.CSS, using = "a.js-edit-preferences")
	public WebElement editMarketingPreferences;

	@FindBy(how = How.XPATH, using = "//button[@class='button btn-yellow btn-update-marketing-preferences js-update-marketing-preferences']")
	public WebElement updateMyPreferencebutton;

	@FindBy(how = How.CSS, using = "div.alert")
	public WebElement subscribeAlert;

	@FindBy(how = How.XPATH, using = "//*[@id='accountNav']/ul/li[6]/a")
	public WebElement LocalStores;

	@FindBy(how = How.CSS, using = ".store-finder-navigation-list-store-distance")
	public WebElement storemiles;

	@FindBy(how = How.CSS, using = ".store-finder-navigation-list-store-name")
	public WebElement sogetiStoreName;

	@FindBy(how = How.CSS, using = "li.store-finder-navigation-list-entry a.js-ibm-store-detail")
	public WebElement storeDetailsLink;

	@FindBy(how = How.CSS, using = ".js-store-found-count-stores")
	public WebElement storeLocatorResults;

	@FindBy(how = How.CSS, using = ".store-finder-navigation-list-store-address")
	public WebElement storeAddress;

	@FindBy(how = How.CSS, using = ".store-results-wrap")
	public WebElement resultFound;

	@FindBy(how = How.CSS, using = "#storeLocatorHeader .no-result-container")
	public WebElement resultNOTFound;

	@FindBy(how = How.XPATH, using = "//*[@id='giftCardLink']")
	public WebElement GiftCard;

	@FindBy(how = How.XPATH, using = "//*[@id='frmPersonalDetails']/div[1]/div[4]/div/div[6]/div/button")
	public WebElement personaldetailSave;

	@FindBy(how = How.CSS, using = "a.js-edit-personal-details")
	public WebElement personalDetails;

	@FindBy(how = How.CSS, using = ".user-info")
	public WebElement UserInfo;

	@FindBy(how = How.CSS, using = "a.button.js-add-new-home-address")
	public WebElement addNewAddress;

	@FindBy(how = How.CSS, using = "a.button.js-add-new-store-address")
	public WebElement addNewStore;

	@FindBy(how = How.XPATH, using = "//div[5]/div/div[1]/h3")
	public WebElement sogetiStoreHeader;

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'storeFinderSearchPage')]")
	public WebElement StoreLocatorSearchPage;

	@FindBy(how = How.XPATH, using = "//form/div[1]/div[2]/div[1]/input")
	public WebElement cityForStore;

	@FindBy(how = How.XPATH, using = "//*[@id='distance-filter']")
	public WebElement radiusSelector;

	@FindBy(how = How.ID, using = "sortOptions1")
	public WebElement SortOption;

	@FindBy(how = How.CSS, using = ".frm-search-store>.btn-container button.js-search-store")
	public WebElement searchForStore;

	@FindBy(how = How.CSS, using = "button.js-search-store")
	public WebElement searchUSStoreBtn;

	@FindBy(how = How.CSS, using = "div.store-locater-box #homepageAddress")
	public WebElement storeSearchInput;

	@FindBy(how = How.ID, using = "addressName")
	public WebElement nameAddress;

	@FindBy(how = How.ID, using = "addressName")
	public WebElement validateNameAddressUS;

	@FindBy(how = How.CSS, using = "div#address0 div.text-container p.subheading-text")
	public WebElement validateNameAddress;

	@FindBy(how = How.CSS, using = "div#address0 div.text-container p:nth-child(2)")
	public WebElement validateAddress;

	@FindBy(how = How.ID, using = "address.line1")
	public WebElement validateAddressUS;

	@FindBy(how = How.ID, using = "address.line1")
	public WebElement addressLine1;

	@FindBy(how = How.ID, using = "address.line2")
	public WebElement addressLine2;

	@FindBy(how = How.ID, using = "address.townCity")
	public WebElement townCity;

	@FindBy(how = How.ID, using = "address.postalcode")
	public WebElement PostalCode;

	@FindBy(how = How.CSS, using = "input.js-validate-postcode")
	public WebElement postCode;

	@FindBy(how = How.NAME, using = "regionIso")
	public WebElement selectState;

	@FindBy(how = How.CSS, using = "select[id='address.country']")
	public WebElement selectCountry;

	@FindBy(how = How.XPATH, using = "//button[@class='button btn-yellow js-save-home-address ']")
	public WebElement addAddressButton;

	@FindBy(how = How.CSS, using = "button.button.btn-yellow.js-save-home-address")
	public WebElement addDeliveryAddressButton;

	@FindBy(how = How.XPATH, using = "//div[@class='cta-container pull-right']/a/span")
	public List<WebElement> editAddress;

	@FindBy(how = How.CSS, using = "div.content-footer-container.gray-background.selected p.remove-text.text-center")
	public WebElement removeAddress;

	@FindBy(how = How.XPATH, using = "//div[1]/div/div[2]/div[2]/a[2]")
	public WebElement removeLink;

	@FindBy(how = How.CSS, using = "div[id^='address']")
	public List<WebElement> addressBoxes;

	@FindBy(how = How.ID, using = "profile.firstName")
	public WebElement add_firstname;

	@FindBy(how = How.ID, using = "profile.lastName")
	public WebElement add_lastname;

	@FindBy(how = How.XPATH, using = "//span[@id='ddlCountryCode_title']/span[@class='ddlabel']")
	public WebElement add_countrycode;

	@FindBy(how = How.XPATH, using = "//span[contains(.,'+1')]")
	public WebElement add_countrycodeOption1;

	@FindBy(how = How.ID, using = "register.phone")
	public WebElement add_phone;

	@FindBy(how = How.CSS, using = "select.day")
	public WebElement add_day;

	@FindBy(how = How.CSS, using = "select.month")
	public WebElement add_month;

	@FindBy(how = How.CSS, using = "select.year")
	public WebElement add_year;

	@FindBy(how = How.CSS, using = ".personal-details-container .breadcrumb-section>a")
	public WebElement myAccountlink;

	@FindBy(how = How.XPATH, using = "//*[starts-with(@id,'female-radio_label')]")
	public WebElement genderRadio;

	@FindBy(how = How.CSS, using = "a.js-past-orders")
	public WebElement pastOrderHeader;

	@FindBy(how = How.XPATH, using = "//div[2]/div/div[2]/div[2]/div[5]/div/span[2]/a")
	public WebElement viewDetailsLink;

	public String accountCategoryPrefLocator = "span[id*='preferenceCheckbox']";

	public String accountCategoryPrefSelected = "div[data-preference].checked";

	public String verifyStoreLocator = "div.collection-store .store-detail-container .store-address";
	@FindBy(how = How.CSS, using = "div.collection-store .store-detail-container .store-address")
	public WebElement verifyStore;

	@FindBy(how = How.XPATH, using = "//*[@id='storeDetailMap']")
	public WebElement StoreMap;

	@FindBy(how = How.XPATH, using = "//form/div/div/div/div/div/div/div[1]/div[2]/div[2]/div[3]/button[2]")
	public WebElement removeStore;

	@FindBy(how = How.XPATH, using = "//form/div/div/div/div/div/div/div[2]/div[2]/a[2]")
	public WebElement removeStorelink;

	@FindBy(how = How.CSS, using = "button.js-select-store")
	public List<WebElement> selectStore;

	@FindBy(how = How.CSS, using = "#store-list .store-finder-navigation-list-store-address")
	public List<WebElement> storeAddressList;

	@FindBy(how = How.CSS, using = "#sogetiStoresPanel .store-finder-navigation-list-store-address")
	public List<WebElement> storeAddressListUS;

	@FindBy(how = How.CSS, using = "a.js-ibm-store-detail")
	public List<WebElement> viewStoreDetails;

	@FindBy(how = How.CSS, using = ".my-account-order")
	public WebElement ordersection;

	@FindBy(how = How.CSS, using = ".js-order-number > span")
	public WebElement ordernumberorderdetails;

	@FindBy(how = How.CSS, using = ".my-account-order-status > span")
	public WebElement orderstatusorderdetails;

	@FindBy(how = How.CSS, using = "button.btn-return-order-uksite")
	public WebElement returnbuttonUKorderdetails;

	@FindBy(how = How.CSS, using = "button.btn-return-order")
	public WebElement returnbuttonEUorderdetails;

	@FindBy(how = How.CSS, using = "button.js-make-a-return")
	public WebElement returnbuttonUSorderdetails;

	@FindBy(how = How.CSS, using = ".track-order-button")
	public WebElement trackbuttonorderdetails;

	@FindBy(how = How.CSS, using = ".return-checkbox label")
	public WebElement returntickbox;

	@FindBy(how = How.CSS, using = ".select-reason-box")
	public WebElement returnreasondropdown;

	@FindBy(how = How.CSS, using = "button.btn-submit-return")
	public WebElement submitReturnButtonUs;

	@FindBy(how = How.CSS, using = "#return-order-modal")
	public WebElement returnordermodal;

	@FindBy(how = How.CSS, using = "button.print-label")
	public WebElement printreturnlabel;

	@FindBy(how = How.CSS, using = ".print-slip")
	public WebElement printpackingslip;

	@FindBy(how = How.CSS, using = "div.printLabel input[value='ups']")
	public WebElement selectreturnlabelUPS;

	@FindBy(how = How.CSS, using = "div.printLabel input[value='usps']")
	public WebElement selectreturnlabelUSPS;

	@FindBy(how = How.CSS, using = "div.printLabel input[value='fedex']")
	public WebElement selectreturnlabelFedEx;

	@FindBy(how = How.CLASS_NAME, using = "setting-comp-name")
	public WebElement personalHeader;

	@FindBy(how = How.CLASS_NAME, using = "alert-info")
	public WebElement addressRemovedMsg;

	public String addressSaveErrorLocator = "alert-danger";

	@FindBy(how = How.CLASS_NAME, using = "alert-danger")
	public WebElement addressSaveError;

	@FindBy(how = How.CSS, using = ".button.js-remove-location")
	public List<WebElement> removeAddressButtonList;

	@FindBy(how = How.CSS, using = ".JS_edit_deliveryAdd.pull-left>span")
	public WebElement delivery_details;

	@FindBy(how = How.CLASS_NAME, using = "unsubscribe-link")
	public WebElement changeEmailSettings;

	@FindBy(how = How.CLASS_NAME, using = "btn-update-marketing-preferences")
	public WebElement updateMarketingPrefs;

	@FindBy(how = How.ID, using = "fewerEmails-radio_label")
	public WebElement optDownRadioButton;

	@FindBy(how = How.ID, using = "unsubscribe-radio_label")
	public WebElement unSubscribeRadioButton;

	@FindBy(how = How.ID, using = "success-msg")
	public WebElement optDownSuccessMessage;

	@FindBy(how = How.ID, using = "success-msg")
	public WebElement unSubscribeSuccessMessage;

	@FindBy(how = How.ID, using = "updateFreqBtn")
	public WebElement updateFrequencyButton;

	@FindBy(how = How.ID, using = "unsubBtn")
	public WebElement unSubscribeButton;

	@FindBy(how = How.CSS, using = ".text-center.js-delete-store-action")
	public List<WebElement> removeConfirmButtonList;

	//Appointments
	//On MyAccount PAge

	@FindBy(how = How.CSS, using = ".appointment .sub-title")
	public WebElement myAppointmentsHeader;

	@FindBy(how = How.CSS, using = "[data-test='my-appointment-store-info']")
	public WebElement myAppointmentStoreInfo;

	@FindBy(how = How.CSS, using = "[data-test='my-appointment-date']")
	public WebElement myAppointmentDate;

	@FindBy(how = How.CSS, using = "[data-test='my-appointment-time']")
	public WebElement myAppointmentTime;

	@FindBy(how = How.CSS, using = "[data-test='my-appointment-for']")
	public WebElement myAppointmentFor;

	@FindBy(how = How.CSS, using = ".appointment .my-appointment-content")
	public List<WebElement> myAppointmentsList;

	@FindBy(how = How.CSS, using = ".appointment .my-appointment-content .my-appointment-btns button")
	public List<WebElement> myAppointmentsListCancelButtons;

	@FindBy(how = How.CSS, using = ".js-cancel-appointment")
	public WebElement cancelappointment;

	@FindBy(how = How.CSS, using = "#cancel-appointment-modal")
	public WebElement cancelAppointmentPopup;

	@FindBy(how = How.XPATH, using = "//button[@id='cancel-Appointment-button']")
	public WebElement confirmcancelappointment;

	@FindBy(how = How.CSS, using = "[data-test='book-store-appointment-btn']")
	public WebElement KidsbookAppointment;

	@FindBy(how = How.CSS, using = "#storeLocatorModal .modal-body")
	public WebElement fittingBookingPopup;

	@FindBy(how = How.CSS, using = "#kids-booking-input-modal")
	public WebElement fittingBookingModal;

	@FindBy(how = How.XPATH, using = "//table[@class='table-condensed']")
	public WebElement Calendar;

	@FindBy(how = How.CSS, using = ".datepicker-days td:not(.disabled)+td")
	public WebElement selectTommorrowAppt;

	@FindBy(how = How.CSS, using = "button.js-btn-next-date-time")
	public WebElement NextTimeDateAvailable;

	@FindBy(how = How.CSS, using = ".js-confirm-assignment")
	public WebElement ConfirmAppointment;

	@FindBy(how = How.CSS, using = "[data-test='times-container']")
	public WebElement Timeslots;

	@FindBy(how = How.CSS, using = "[data-test='time-slot']")
	public WebElement firstAvailableSlot;

	@FindBy(how = How.XPATH, using = "//div[@class='store-address-section']")
	public WebElement StoreDetails;

	@FindBy(how = How.XPATH, using = "//input[@id='booking-name']")
	public WebElement bookingname;

	@FindBy(how = How.XPATH, using = "//input[@id='booking-email']")
	public WebElement bookingemail;

	@FindBy(how = How.XPATH, using = "//input[@id='booking-contact-number']")
	public WebElement bookingcontactnumber;

	@FindBy(how = How.XPATH, using = "//img[@class='img js-image-responsive banner-overlay-bg']")
	public WebElement AppointmentConfirmationicon;

	@FindBy(how = How.CSS, using = "[data-test='booked-appointment-date']")
	public WebElement BookedAppointmentDate;
	
	@FindBy(how = How.CSS, using = "[data-test='booked-appointment-confirmation-no']")
	public WebElement BookedAppointmentConfirmationNo;

	@FindBy(how = How.CSS, using = "[data-test='booked-appointment-time']")
	public WebElement BookedAppointmentTime;

	@FindBy(how = How.CSS, using = "[data-test='booked-appointment-no-children']")
	public WebElement BookedAppointmentNoChildren;

	@FindBy(how = How.CSS, using = "[data-test='terms-and-conditions-label']")
	public WebElement BookAppointmentTAndCsCheckboxLabel;

	@FindBy(how = How.CSS, using = "[data-test='terms-and-conditions-input']")
	public WebElement BookAppointmentTAndCsCheckboxInput;

	@FindBy(how = How.CSS, using = ".js-confirm-kids-done")
	public WebElement closebookconfirmation;

	@FindBy(how = How.CSS, using = ".kids-booking-confirmation-content .js-confirm-kids-book-another-appointment")
	public WebElement bookanotherfitting;

	@FindBy(how = How.CSS, using = "#ddlCountryCode_child >ul>li")
	public List<WebElement> countryCodeList;

	@FindBy(how = How.CSS, using = ".ddArrow.arrowoff")
	public WebElement selectCountryCode;

	@FindBy(how = How.CSS, using = "#trackOrderModal")
	public WebElement trackOrderModal;

	@FindBy(how = How.CSS, using = ".js-add-new-appointment")
	public WebElement guestKidsbookAppointment;

	@FindBy(how = How.CSS, using = "#storeLocatorModal")
	public WebElement guestFittingBookingPopup;

	@FindBy(how = How.CSS, using = ".js-open-store-locator")
	public WebElement kidsbookVirtualAppointment;

	@FindBy(how = How.CSS, using = "#kids-booking-input-modal")
	public WebElement virtualFittingBookingPopup;

	@FindBy(how = How.CSS, using = "#address")
	public WebElement storeInput;

	@FindBy(how = How.CSS, using = ".js-ibm-ft01-e01")
	public WebElement storeSearch;

	public String updateMarketingPrefsErrorLocator = "div.error-link";

	public String footerMarketingPrefsErrorLocator = "p.error-link";

}
