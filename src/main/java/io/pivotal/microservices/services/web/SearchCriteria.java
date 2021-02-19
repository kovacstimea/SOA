package io.pivotal.microservices.services.web;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class SearchCriteria {
	private String accountNumber;
	private String productNumber;
	private String orderNumber;

	private String searchText;

	public String getAccountNumber() {
		return accountNumber;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public boolean isValidAccount() {
		if (StringUtils.hasText(accountNumber))
			return !(StringUtils.hasText(searchText));
		else
			return (StringUtils.hasText(searchText));
	}

	public boolean isValidProduct() {
		if (StringUtils.hasText(productNumber))
			return !(StringUtils.hasText(searchText));
		else
			return (StringUtils.hasText(searchText));
	}

	public boolean isValidOrder() {
		if (StringUtils.hasText(orderNumber))
			return !(StringUtils.hasText(searchText));
		else
			return (StringUtils.hasText(searchText));
	}

	public boolean validateAccount(Errors errors) {
		if (StringUtils.hasText(accountNumber)) {
			if (accountNumber.length() != 9)
				errors.rejectValue("accountNumber", "badFormat", "Account number should be 9 digits");
			else {
				try {
					Integer.parseInt(accountNumber);
				} catch (NumberFormatException e) {
					errors.rejectValue("accountNumber", "badFormat", "Account number should be 9 digits");
				}
			}

			if (StringUtils.hasText(searchText)) {
				errors.rejectValue("searchText", "nonEmpty", "Cannot specify account number and search text");
			}
		} else if (StringUtils.hasText(searchText)) {
			; // Nothing to do
		} else {
			errors.rejectValue("accountNumber", "nonEmpty", "Must specify either an account number or search text");

		}

		return errors.hasErrors();
	}

	public boolean validateProduct(Errors errors) {
		if (StringUtils.hasText(productNumber)) {
			if (productNumber.length() != 9)
				errors.rejectValue("productNumber", "badFormat", "Product number should be 9 digits");
			else {
				try {
					Integer.parseInt(productNumber);
				} catch (NumberFormatException e) {
					errors.rejectValue("productNumber", "badFormat", "Product number should be 9 digits");
				}
			}

			if (StringUtils.hasText(searchText)) {
				errors.rejectValue("searchText", "nonEmpty", "Cannot specify product number and search text");
			}
		} else if (StringUtils.hasText(searchText)) {
			; // Nothing to do
		} else {
			errors.rejectValue("productNumber", "nonEmpty", "Must specify either a product number or search text");

		}

		return errors.hasErrors();
	}

	public boolean validateOrder(Errors errors) {
		if (StringUtils.hasText(orderNumber)) {
			if (orderNumber.length() != 9)
				errors.rejectValue("orderNumber", "badFormat", "Order number should be 9 digits");
			else {
				try {
					Integer.parseInt(orderNumber);
				} catch (NumberFormatException e) {
					errors.rejectValue("orderNumber", "badFormat", "Order number should be 9 digits");
				}
			}

			if (StringUtils.hasText(searchText)) {
				errors.rejectValue("searchText", "nonEmpty", "Cannot specify order number and search text");
			}
		} else if (StringUtils.hasText(searchText)) {
			; // Nothing to do
		} else {
			errors.rejectValue("orderNumber", "nonEmpty", "Must specify either an order number or search text");

		}

		return errors.hasErrors();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (StringUtils.hasText(accountNumber) ? "number: " + accountNumber : "") + (StringUtils.hasText(searchText) ? " text: " + searchText : "");
	}
}
