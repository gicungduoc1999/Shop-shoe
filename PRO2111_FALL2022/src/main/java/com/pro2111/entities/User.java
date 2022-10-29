// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro2111.utils.FormateString;
import com.pro2111.validations.users.UniqueUserEmail;
import com.pro2111.validations.users.UniqueUserPhone;

@Entity
@Table(name = "users", indexes = { @Index(name = "users_Email_IX", columnList = "email", unique = true),
		@Index(name = "users_Phone_IX", columnList = "phone", unique = true) })
public class User implements Serializable, UserDetails {

	/** Primary key. */
	protected static final String PK = "userId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false, precision = 10)
	private int userId;

	@NotBlank
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;

	@NotBlank
	@Email
	@UniqueUserEmail
	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@NotBlank
	@Column(name = "password", nullable = false, length = 100)
	@Length(min = 8)
	private String password;

	@NotBlank
	@UniqueUserPhone
	@Column(name = "phone", unique = true, nullable = false, length = 20)
	@Length(min = 10, max = 11)
	private String phone;

	@NotNull
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "sex", nullable = false, precision = 10)
	private int sex;

	@NotNull
	@Column(name = "role", nullable = false, precision = 10)
	private int role;

	@Column(name = "avatar", length = 200)
	private String avatar;

	@JsonIgnore
	@NotNull
	@Column(name = "otp", nullable = false, precision = 10)
	private int otp;

	@NotNull
	@Column(name = "status", nullable = false, precision = 10)
	private int status;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@JsonIgnore
	@OneToMany(mappedBy = "users")
	private Set<Cart> carts;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Bill> bills;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private Set<Bill> bill2;
	
	@JsonIgnore
	@OneToMany(mappedBy="users")
    private Set<PurchaseReturn> purchaseReturns;
	
	@JsonIgnore
    @OneToMany(mappedBy="users")
    private Set<PurchaseOrder> purchaseOrders;

	/** Default constructor. */
	public User() {
		super();
	}

	/**
	 * Access method for userId.
	 *
	 * @return the current value of userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Setter method for userId.
	 *
	 * @param aUserId the new value for userId
	 */
	public void setUserId(int aUserId) {
		userId = aUserId;
	}

	/**
	 * Access method for fullName.
	 *
	 * @return the current value of fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Setter method for fullName.
	 *
	 * @param aFullName the new value for fullName
	 */
	public void setFullName(String aFullName) {
		fullName = aFullName;
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
	 * Access method for password.
	 *
	 * @return the current value of password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method for password.
	 *
	 * @param aPassword the new value for password
	 */
	public void setPassword(String aPassword) {
		password = aPassword;
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
	 * Access method for sex.
	 *
	 * @return the current value of sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * Setter method for sex.
	 *
	 * @param aSex the new value for sex
	 */
	public void setSex(int aSex) {
		sex = aSex;
	}

	/**
	 * Access method for role.
	 *
	 * @return the current value of role
	 */
	public int getRole() {
		return role;
	}

	/**
	 * Setter method for role.
	 *
	 * @param aRole the new value for role
	 */
	public void setRole(int aRole) {
		role = aRole;
	}

	/**
	 * Access method for avatar.
	 *
	 * @return the current value of avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Setter method for avatar.
	 *
	 * @param aAvatar the new value for avatar
	 */
	public void setAvatar(String aAvatar) {
		avatar = aAvatar;
	}

	/**
	 * @return the otp
	 */
	public int getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(int otp) {
		this.otp = otp;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Access method for carts.
	 *
	 * @return the current value of carts
	 */
	public Set<Cart> getCarts() {
		return carts;
	}

	/**
	 * Setter method for carts.
	 *
	 * @param aCarts the new value for carts
	 */
	public void setCarts(Set<Cart> aCarts) {
		carts = aCarts;
	}

	/**
	 * Access method for bills.
	 *
	 * @return the current value of bills
	 */
	public Set<Bill> getBills() {
		return bills;
	}

	/**
	 * Setter method for bills.
	 *
	 * @param aBills the new value for bills
	 */
	public void setBills(Set<Bill> aBills) {
		bills = aBills;
	}

	/**
	 * @return the bill2
	 */
	public Set<Bill> getBill2() {
		return bill2;
	}

	/**
	 * @param bill2 the bill2 to set
	 */
	public void setBill2(Set<Bill> bill2) {
		this.bill2 = bill2;
	}
	
	

	/**
	 * @return the purchaseReturns
	 */
	public Set<PurchaseReturn> getPurchaseReturns() {
		return purchaseReturns;
	}

	/**
	 * @param purchaseReturns the purchaseReturns to set
	 */
	public void setPurchaseReturns(Set<PurchaseReturn> purchaseReturns) {
		this.purchaseReturns = purchaseReturns;
	}

	/**
	 * @return the purchaseOrders
	 */
	public Set<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}

	/**
	 * @param purchaseOrders the purchaseOrders to set
	 */
	public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

	/**
	 * Compares the key for this instance with another Users.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Users and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof User)) {
			return false;
		}
		User that = (User) other;
		if (this.getUserId() != that.getUserId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Users.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof User))
			return false;
		return this.equalKeys(other) && ((User) other).equalKeys(this);
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
		i = getUserId();
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
		StringBuffer sb = new StringBuffer("[Users |");
		sb.append(" userId=").append(getUserId());
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
		ret.put("userId", Integer.valueOf(getUserId()));
		return ret;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
