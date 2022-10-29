// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Supplier")
@Table(name = "suppliers")
public class Supplier implements Serializable {

	/** Primary key. */
	protected static final String PK = "supplierId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id", unique = true, nullable = false, precision = 10)
	private int supplierId;
	@Column(name = "supplier_name", length = 100)
	private String supplierName;
	@Column(length = 20)
	private String phone;
	@Column(length = 255)
	private String address;
	@Column(length = 100)
	private String email;
	@Column(name = "tax_number", length = 50)
	private String taxNumber;
	@Column(length = 45)
	private String note;
	@Column(precision = 10)
	private int status;
	@OneToMany(mappedBy = "supplier")
	private Set<PurchaseReturn> purchaseReturn;
	@OneToMany(mappedBy = "supplier")
	private Set<PurchaseOrder> purchaseOrder;

	/** Default constructor. */
	public Supplier() {
		super();
	}

	/**
	 * Access method for supplierId.
	 *
	 * @return the current value of supplierId
	 */
	public int getSupplierId() {
		return supplierId;
	}

	/**
	 * Setter method for supplierId.
	 *
	 * @param aSupplierId the new value for supplierId
	 */
	public void setSupplierId(int aSupplierId) {
		supplierId = aSupplierId;
	}

	/**
	 * Access method for supplierName.
	 *
	 * @return the current value of supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * Setter method for supplierName.
	 *
	 * @param aSupplierName the new value for supplierName
	 */
	public void setSupplierName(String aSupplierName) {
		supplierName = aSupplierName;
	}

	/**
	 * Access method for phone.
	 *
	 * @return the current value of phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter method for phone.
	 *
	 * @param aPhone the new value for phone
	 */
	public void setPhone(String aPhone) {
		phone = aPhone;
	}

	/**
	 * Access method for address.
	 *
	 * @return the current value of address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter method for address.
	 *
	 * @param aAddress the new value for address
	 */
	public void setAddress(String aAddress) {
		address = aAddress;
	}

	/**
	 * Access method for email.
	 *
	 * @return the current value of email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method for email.
	 *
	 * @param aEmail the new value for email
	 */
	public void setEmail(String aEmail) {
		email = aEmail;
	}

	/**
	 * Access method for taxNumber.
	 *
	 * @return the current value of taxNumber
	 */
	public String getTaxNumber() {
		return taxNumber;
	}

	/**
	 * Setter method for taxNumber.
	 *
	 * @param aTaxNumber the new value for taxNumber
	 */
	public void setTaxNumber(String aTaxNumber) {
		taxNumber = aTaxNumber;
	}

	/**
	 * Access method for note.
	 *
	 * @return the current value of note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Setter method for note.
	 *
	 * @param aNote the new value for note
	 */
	public void setNote(String aNote) {
		note = aNote;
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
	public Set<PurchaseReturn> getPurchaseReturn() {
		return purchaseReturn;
	}

	/**
	 * Setter method for purchaseReturn.
	 *
	 * @param aPurchaseReturn the new value for purchaseReturn
	 */
	public void setPurchaseReturn(Set<PurchaseReturn> aPurchaseReturn) {
		purchaseReturn = aPurchaseReturn;
	}

	/**
	 * Access method for purchaseOrder.
	 *
	 * @return the current value of purchaseOrder
	 */
	public Set<PurchaseOrder> getPurchaseOrder() {
		return purchaseOrder;
	}

	/**
	 * Setter method for purchaseOrder.
	 *
	 * @param aPurchaseOrder the new value for purchaseOrder
	 */
	public void setPurchaseOrder(Set<PurchaseOrder> aPurchaseOrder) {
		purchaseOrder = aPurchaseOrder;
	}

	/**
	 * Compares the key for this instance with another Supplier.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Supplier and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Supplier)) {
			return false;
		}
		Supplier that = (Supplier) other;
		if (this.getSupplierId() != that.getSupplierId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Supplier.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Supplier))
			return false;
		return this.equalKeys(other) && ((Supplier) other).equalKeys(this);
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
		i = getSupplierId();
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
		StringBuffer sb = new StringBuffer("[Supplier |");
		sb.append(" supplierId=").append(getSupplierId());
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
		ret.put("supplierId", Integer.valueOf(getSupplierId()));
		return ret;
	}

}
