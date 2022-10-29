// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "ProductVariant")
@Table(name = "product_variants")
public class ProductVariant implements Serializable {

	/** Primary key. */
	protected static final String PK = "variantId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "variant_id", unique = true, nullable = false, precision = 19)
	private Long variantId;
	@Column(name = "SKU_ID", nullable = false, length = 255)
	private String skuId;

	@NotNull
	@Min(value = 0)
	@Column(nullable = false, precision = 10)
	private int quantity;

	@NotNull
	@Min(value = 0)
	@Column(name = "import_price", nullable = false, precision = 10)
	private BigDecimal importPrice;

	@NotNull
	@Min(value = 0)
	@Column(nullable = false, precision = 10)
	private BigDecimal price;
	
	@NotNull
	@Min(value = 0)
	@Column(name = "tax", nullable = false, precision = 10)
	private BigDecimal tax;
	@Column(name = "is_sale", precision = 10)
	private int isSale;
	@Column(nullable = false, precision = 10)
	private int status;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product products;

	@JsonIgnore
	@OneToMany(mappedBy = "productVariants")
	private Set<ProductSale> productSales;

	@JsonIgnore
	@OneToMany(mappedBy = "productVariants")
	private Set<Image> images;

	@JsonIgnore
	@OneToMany(mappedBy = "productVariants")
	private Set<Content> contents;

	@JsonIgnore
	@OneToMany(mappedBy = "productVariants")
	private Set<BillDetail> detailBills;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productVariants")
	private Set<VariantValue> variantValues;

	@JsonIgnore
	@OneToMany(mappedBy = "productVariants")
	private Set<CartDetail> detailCarts;

	@JsonIgnore
	@OneToMany(mappedBy = "productVariants")
	private Set<PurchaseOrderDetail> purchaseOrderDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "productVariants")
	private Set<PurchaseReturnDetail> purchaseReturnDetails;

	/** Default constructor. */
	public ProductVariant() {
		super();
	}

	/**
	 * Access method for variantId.
	 *
	 * @return the current value of variantId
	 */
	public Long getVariantId() {
		return variantId;
	}

	/**
	 * Setter method for variantId.
	 *
	 * @param aVariantId the new value for variantId
	 */
	public void setVariantId(Long aVariantId) {
		variantId = aVariantId;
	}

	/**
	 * Access method for skuId.
	 *
	 * @return the current value of skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * Setter method for skuId.
	 *
	 * @param aSkuId the new value for skuId
	 */
	public void setSkuId(String aSkuId) {
		skuId = aSkuId;
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
	 * @return the importPrice
	 */
	public BigDecimal getImportPrice() {
		return importPrice;
	}

	/**
	 * @param importPrice the importPrice to set
	 */
	public void setImportPrice(BigDecimal importPrice) {
		this.importPrice = importPrice;
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
	 * Access method for isSale.
	 *
	 * @return the current value of isSale
	 */
	public int getIsSale() {
		return isSale;
	}

	/**
	 * Setter method for isSale.
	 *
	 * @param aIsSale the new value for isSale
	 */
	public void setIsSale(int aIsSale) {
		this.isSale = aIsSale;
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
		this.status = aStatus;
	}

	/**
	 * @return the products
	 */
	public Product getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(Product products) {
		this.products = products;
	}

	/**
	 * @return the productSales
	 */
	public Set<ProductSale> getProductSales() {
		return productSales;
	}

	/**
	 * @param productSales the productSales to set
	 */
	public void setProductSales(Set<ProductSale> productSales) {
		this.productSales = productSales;
	}

	/**
	 * @return the images
	 */
	public Set<Image> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(Set<Image> images) {
		this.images = images;
	}

	/**
	 * @return the contents
	 */
	public Set<Content> getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

	/**
	 * @return the detailBills
	 */
	public Set<BillDetail> getDetailBills() {
		return detailBills;
	}

	/**
	 * @param detailBills the detailBills to set
	 */
	public void setDetailBills(Set<BillDetail> detailBills) {
		this.detailBills = detailBills;
	}

	/**
	 * @return the variantValues
	 */
	public Set<VariantValue> getVariantValues() {
		return variantValues;
	}

	/**
	 * @param variantValues the variantValues to set
	 */
	public void setVariantValues(Set<VariantValue> variantValues) {
		this.variantValues = variantValues;
	}

	/**
	 * @return the detailCarts
	 */
	public Set<CartDetail> getDetailCarts() {
		return detailCarts;
	}

	/**
	 * @param detailCarts the detailCarts to set
	 */
	public void setDetailCarts(Set<CartDetail> detailCarts) {
		this.detailCarts = detailCarts;
	}

	/**
	 * @return the purchaseOrderDetail
	 */
	public Set<PurchaseOrderDetail> getPurchaseOrderDetails() {
		return purchaseOrderDetails;
	}

	/**
	 * @param purchaseOrderDetail the purchaseOrderDetail to set
	 */
	public void setPurchaseOrderDetails(Set<PurchaseOrderDetail> purchaseOrderDetails) {
		this.purchaseOrderDetails = purchaseOrderDetails;
	}

	/**
	 * @return the purchaseReturnDetail
	 */
	public Set<PurchaseReturnDetail> getPurchaseReturnDetails() {
		return purchaseReturnDetails;
	}

	/**
	 * @param purchaseReturnDetail the purchaseReturnDetail to set
	 */
	public void setPurchaseReturnDetails(Set<PurchaseReturnDetail> purchaseReturnDetails) {
		this.purchaseReturnDetails = purchaseReturnDetails;
	}

	/**
	 * Compares the key for this instance with another ProductVariant.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class ProductVariant and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductVariant)) {
			return false;
		}
		ProductVariant that = (ProductVariant) other;
		if (this.getVariantId() != that.getVariantId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another ProductVariant.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProductVariant))
			return false;
		return this.equalKeys(other) && ((ProductVariant) other).equalKeys(this);
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
		i = (int) (getVariantId() ^ (getVariantId() >>> 32));
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
		StringBuffer sb = new StringBuffer("[ProductVariant |");
		sb.append(" variantId=").append(getVariantId());
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
		ret.put("variantId", Long.valueOf(getVariantId()));
		return ret;
	}

}
