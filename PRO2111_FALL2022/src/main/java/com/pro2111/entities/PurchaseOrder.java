// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "PurchaseOrder")
@Table(name = "purchase_orders")
public class PurchaseOrder implements Serializable {

	/** Primary key. */
	protected static final String PK = "purchaseId";

	@Id
	@Column(name = "purchase_id", unique = true, nullable = false, length = 50)
	private String purchaseId;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "total_money", precision = 10)
	private BigDecimal totalMoney;
	@Column(precision = 10)
	private int discount;
	@Column(name = "discount_type", precision = 10)
	private int discountType;
	@Column(name = "need_to_pay", precision = 10)
	private BigDecimal needToPay;
	@Column(precision = 10)
	private BigDecimal pay;
	@Column(precision = 10)
	private int status;
	@OneToMany(mappedBy = "purchaseOrder")
	private Set<PurchaseOrderDetail> purchaseOrderDetail;
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User users;

	/** Default constructor. */
	public PurchaseOrder() {
		super();
	}

	/**
	 * Access method for purchaseId.
	 *
	 * @return the current value of purchaseId
	 */
	public String getPurchaseId() {
		return purchaseId;
	}

	/**
	 * Setter method for purchaseId.
	 *
	 * @param aPurchaseId the new value for purchaseId
	 */
	public void setPurchaseId(String aPurchaseId) {
		purchaseId = aPurchaseId;
	}

	/**
	 * Access method for createdDate.
	 *
	 * @return the current value of createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * Setter method for createdDate.
	 *
	 * @param aCreatedDate the new value for createdDate
	 */
	public void setCreatedDate(LocalDateTime aCreatedDate) {
		createdDate = aCreatedDate;
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
	 * Access method for discount.
	 *
	 * @return the current value of discount
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * Setter method for discount.
	 *
	 * @param aDiscount the new value for discount
	 */
	public void setDiscount(int aDiscount) {
		discount = aDiscount;
	}

	/**
	 * Access method for discountType.
	 *
	 * @return the current value of discountType
	 */
	public int getDiscountType() {
		return discountType;
	}

	/**
	 * Setter method for discountType.
	 *
	 * @param aDiscountType the new value for discountType
	 */
	public void setDiscountType(int aDiscountType) {
		discountType = aDiscountType;
	}

	/**
	 * Access method for needToPay.
	 *
	 * @return the current value of needToPay
	 */
	public BigDecimal getNeedToPay() {
		return needToPay;
	}

	/**
	 * Setter method for needToPay.
	 *
	 * @param aNeedToPay the new value for needToPay
	 */
	public void setNeedToPay(BigDecimal aNeedToPay) {
		needToPay = aNeedToPay;
	}

	/**
	 * Access method for pay.
	 *
	 * @return the current value of pay
	 */
	public BigDecimal getPay() {
		return pay;
	}

	/**
	 * Setter method for pay.
	 *
	 * @param aPay the new value for pay
	 */
	public void setPay(BigDecimal aPay) {
		pay = aPay;
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
	 * Access method for purchaseOrderDetail.
	 *
	 * @return the current value of purchaseOrderDetail
	 */
	public Set<PurchaseOrderDetail> getPurchaseOrderDetail() {
		return purchaseOrderDetail;
	}

	/**
	 * Setter method for purchaseOrderDetail.
	 *
	 * @param aPurchaseOrderDetail the new value for purchaseOrderDetail
	 */
	public void setPurchaseOrderDetail(Set<PurchaseOrderDetail> aPurchaseOrderDetail) {
		purchaseOrderDetail = aPurchaseOrderDetail;
	}

	/**
	 * Access method for supplier.
	 *
	 * @return the current value of supplier
	 */
	public Supplier getSupplier() {
		return supplier;
	}

	/**
	 * Setter method for supplier.
	 *
	 * @param aSupplier the new value for supplier
	 */
	public void setSupplier(Supplier aSupplier) {
		supplier = aSupplier;
	}

	/**
	 * Access method for users.
	 *
	 * @return the current value of users
	 */
	public User getUsers() {
		return users;
	}

	/**
	 * Setter method for users.
	 *
	 * @param aUsers the new value for users
	 */
	public void setUsers(User aUsers) {
		users = aUsers;
	}

	/**
	 * Compares the key for this instance with another PurchaseOrder.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class PurchaseOrder and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PurchaseOrder)) {
			return false;
		}
		PurchaseOrder that = (PurchaseOrder) other;
		Object myPurchaseId = this.getPurchaseId();
		Object yourPurchaseId = that.getPurchaseId();
		if (myPurchaseId == null ? yourPurchaseId != null : !myPurchaseId.equals(yourPurchaseId)) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another PurchaseOrder.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PurchaseOrder))
			return false;
		return this.equalKeys(other) && ((PurchaseOrder) other).equalKeys(this);
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
		if (getPurchaseId() == null) {
			i = 0;
		} else {
			i = getPurchaseId().hashCode();
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
		StringBuffer sb = new StringBuffer("[PurchaseOrder |");
		sb.append(" purchaseId=").append(getPurchaseId());
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
		ret.put("purchaseId", getPurchaseId());
		return ret;
	}

}
