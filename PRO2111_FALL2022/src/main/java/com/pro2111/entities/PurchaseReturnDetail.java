// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "PurchaseReturnDetail")
@Table(name = "purchase_return_details")
public class PurchaseReturnDetail implements Serializable {

	/** Primary key. */
	protected static final String PK = "detailId";

	@Id
	@Column(name = "detail_id", unique = true, nullable = false, length = 50)
	private String detailId;
	@Column(precision = 10)
	private int quantity;
	@Column(name = "price", precision = 10)
	private BigDecimal price;
	@Column(name = "total_money", precision = 10)
	private BigDecimal totalMoney;
	@Column(precision = 10)
	private int status;
	@ManyToOne(optional = false)
	@JoinColumn(name = "purchase_id", nullable = false)
	private PurchaseReturn purchaseReturn;
	@ManyToOne(optional = false)
	@JoinColumn(name = "variant_id", nullable = false)
	private ProductVariant productVariants;

	/** Default constructor. */
	public PurchaseReturnDetail() {
		super();
	}

	/**
	 * Access method for detailId.
	 *
	 * @return the current value of detailId
	 */
	public String getDetailId() {
		return detailId;
	}

	/**
	 * Setter method for detailId.
	 *
	 * @param aDetailId the new value for detailId
	 */
	public void setDetailId(String aDetailId) {
		detailId = aDetailId;
	}

	/**
	 * Access method for quantity.
	 *
	 * @return the current value of quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Setter method for quantity.
	 *
	 * @param aQuantity the new value for quantity
	 */
	public void setQuantity(int aQuantity) {
		quantity = aQuantity;
	}

	/**
	 * Access method for importPrice.
	 *
	 * @return the current value of importPrice
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Setter method for importPrice.
	 *
	 * @param aImportPrice the new value for importPrice
	 */
	public void setPrice(BigDecimal aPrice) {
		price = aPrice;
	}

	/**
	 * Access method for totalMoney.
	 *
	 * @return the current value of totalMoney
	 */
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	/**
	 * Setter method for totalMoney.
	 *
	 * @param aTotalMoney the new value for totalMoney
	 */
	public void setTotalMoney(BigDecimal aTotalMoney) {
		totalMoney = aTotalMoney;
	}

	/**
	 * Access method for status.
	 *
	 * @return the current value of status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter method for status.
	 *
	 * @param aStatus the new value for status
	 */
	public void setStatus(int aStatus) {
		status = aStatus;
	}

	/**
	 * Access method for purchaseReturn.
	 *
	 * @return the current value of purchaseReturn
	 */
	public PurchaseReturn getPurchaseReturn() {
		return purchaseReturn;
	}

	/**
	 * Setter method for purchaseReturn.
	 *
	 * @param aPurchaseReturn the new value for purchaseReturn
	 */
	public void setPurchaseReturn(PurchaseReturn aPurchaseReturn) {
		purchaseReturn = aPurchaseReturn;
	}

	/**
	 * Access method for productVariants.
	 *
	 * @return the current value of productVariants
	 */
	public ProductVariant getProductVariants() {
		return productVariants;
	}

	/**
	 * Setter method for productVariants.
	 *
	 * @param aProductVariants the new value for productVariants
	 */
	public void setProductVariants(ProductVariant aProductVariants) {
		productVariants = aProductVariants;
	}

	/**
	 * Compares the key for this instance with another PurchaseReturnDetail.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class PurchaseReturnDetail and
	 *         the key objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PurchaseReturnDetail)) {
			return false;
		}
		PurchaseReturnDetail that = (PurchaseReturnDetail) other;
		Object myDetailId = this.getDetailId();
		Object yourDetailId = that.getDetailId();
		if (myDetailId == null ? yourDetailId != null : !myDetailId.equals(yourDetailId)) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another PurchaseReturnDetail.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PurchaseReturnDetail))
			return false;
		return this.equalKeys(other) && ((PurchaseReturnDetail) other).equalKeys(this);
	}

	/**
	 * Returns a hash code for this instance.
	 *
	 * @return Hash code
	 */
	@Override
	public int hashCode() {
		int i;
		int result = 17;
		if (getDetailId() == null) {
			i = 0;
		} else {
			i = getDetailId().hashCode();
		}
		result = 37 * result + i;
		return result;
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[PurchaseReturnDetail |");
		sb.append(" detailId=").append(getDetailId());
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Return all elements of the primary key.
	 *
	 * @return Map of key names to values
	 */
	public Map<String, Object> getPrimaryKey() {
		Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
		ret.put("detailId", getDetailId());
		return ret;
	}

}
