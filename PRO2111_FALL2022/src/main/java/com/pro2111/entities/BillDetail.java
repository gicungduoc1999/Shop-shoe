// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bill_details")
public class BillDetail implements Serializable {

	/** Primary key. */
	protected static final String PK = "detailBillId";
	
	public BillDetail(int quantity, BigDecimal price, BigDecimal totalMoney, Bill bills, Integer status, BigDecimal tax, ProductVariant productVariants) {
		this.quantity = quantity;
		this.price = price;
		this.totalMoney = totalMoney;
		this.bills = bills;
		this.status = status;
		this.tax = tax;
		this.productVariants = productVariants;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "detail_bill_id", unique=true, nullable=false, length=50)
	private String detailBillId;
	@Column(name = "quantity", nullable = false, precision = 10)
	private int quantity;
	@Column(name = "price", nullable = false, precision = 10)
	private BigDecimal price;
	@Column(name = "total_money", nullable = false, precision = 10)
	private BigDecimal totalMoney;
	@ManyToOne(optional = false)
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bills;
	@Column(name = "note", nullable = false, precision = 255)
	private String note;
	@Column(name = "status", nullable = false, precision = 10)
	private Integer status;
	@Column(name = "tax", nullable = false, precision = 10)
	private BigDecimal tax;
	@ManyToOne(optional = false)
	@JoinColumn(name = "variant_id", nullable = false)
	private ProductVariant productVariants;

	/** Default constructor. */
	public BillDetail() {
		super();
	}

	/**
	 * @return the detailBillId
	 */
	public String getDetailBillId() {
		return detailBillId;
	}

	/**
	 * @param detailBillId the detailBillId to set
	 */
	public void setDetailBillId(String detailBillId) {
		this.detailBillId = detailBillId;
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
	 * Access method for price.
	 *
	 * @return the current value of price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Setter method for price.
	 *
	 * @param aPrice the new value for price
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
	 * Access method for bills.
	 *
	 * @return the current value of bills
	 */
	public Bill getBills() {
		return bills;
	}

	/**
	 * Setter method for bills.
	 *
	 * @param aBills the new value for bills
	 */
	public void setBills(Bill aBills) {
		bills = aBills;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

	/**
	 * @return the tax
	 */
	public BigDecimal getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	/**
	 * Compares the key for this instance with another DetailBills.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class DetailBills and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BillDetail)) {
			return false;
		}
		BillDetail that = (BillDetail) other;
		if (this.getDetailBillId() != that.getDetailBillId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another DetailBills.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BillDetail))
			return false;
		return this.equalKeys(other) && ((BillDetail) other).equalKeys(this);
	}

	/**
	 * Returns a hash code for this instance.
	 *
	 * @return Hash code
	 */
	@Override
	public int hashCode() {
		long i;
		long result = 17;
		i = getDetailBillId().hashCode();
		result = 37 * result + i;
		return (int) result;
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[DetailBills |");
		sb.append(" detailBillId=").append(getDetailBillId());
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
		ret.put("detailBillId", getDetailBillId());
		return ret;
	}

}
