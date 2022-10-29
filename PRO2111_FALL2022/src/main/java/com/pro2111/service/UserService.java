package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.User;

public interface UserService {

	List<User> getAll();

	User findById(Integer id);

	User create(User user);

	User update(User user);

	List<User> findApproximatePhone(String phone);

	User delete(User user);
	
	User findByEmail(String email);
	
	User findByPhone(String phone);

	/**
	 * @param input
	 * @return List<User>
	 */
	List<User> findApproximatePhoneorEmail(String input);

	/**
	 * @return
	 */
	List<User> findUserSalse();

	/**
	 * @return List<User>
	 */
	List<User> findByStatus(Integer status);


}
