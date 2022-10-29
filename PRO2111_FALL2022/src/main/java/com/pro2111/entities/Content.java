// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contents")
public class Content implements Serializable {

	/** Primary key. */
	protected static final String PK = "contentId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Content_id", unique = true, nullable = false, precision = 10)
	private int contentId;
	@Column(name = "Content", length = 21840)
	private String content;
	@Column(name = "Status", nullable = false, precision = 10)
	private int status;
	@ManyToOne(optional = false)
	@JoinColumn(name = "Product_variant_id", nullable = false)
	private ProductVariant productVariants;

	/** Default constructor. */
	public Content() {
		super();
	}

	/**
	 * Access method for contentId.
	 *
	 * @return the current value of contentId
	 */
	public int getContentId() {
		return contentId;
	}

	/**
	 * Setter method for contentId.
	 *
	 * @param aContentId the new value for contentId
	 */
	public void setContentId(int aContentId) {
		contentId = aContentId;
	}

	/**
	 * Access method for content.
	 *
	 * @return the current value of content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Setter method for content.
	 *
	 * @param aContent the new value for content
	 */
	public void setContent(String aContent) {
		content = aContent;
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
	 * Compares the key for this instance with another Contents.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Contents and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Content)) {
			return false;
		}
		Content that = (Content) other;
		if (this.getContentId() != that.getContentId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Contents.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Content))
			return false;
		return this.equalKeys(other) && ((Content) other).equalKeys(this);
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
		i = getContentId();
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
		StringBuffer sb = new StringBuffer("[Contents |");
		sb.append(" contentId=").append(getContentId());
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
		ret.put("contentId", Integer.valueOf(getContentId()));
		return ret;
	}

}
