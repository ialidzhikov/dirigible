package org.eclipse.dirigible.database.persistence.test;

import javax.persistence.Column;

/**
 * The Class MultiOrder.
 */
public class MultiOrder extends Order {

	@Column(name = "ORDER_AMOUNT", columnDefinition = "BIGINT", nullable = true)
	private Long amount;

	@Column(name = "ORDER_DESCRIPTION", columnDefinition = "VARCHAR", nullable = true, length = 512)
	private String description;

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount
	 *            the new amount
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * Gets the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
