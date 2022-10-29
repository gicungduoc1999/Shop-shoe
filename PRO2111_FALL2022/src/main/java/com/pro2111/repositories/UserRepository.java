package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pro2111.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.phone LIKE %:phone%")
	List<User> findApproximatePhone(String phone);

	User findByEmailLike(String email);

	User findByPhoneLike(String phone);

	@Query("SELECT u FROM User u WHERE u.phone LIKE %:input% OR u.email LIKE %:input%")
	List<User> findApproximatePhoneorEmail(String input);

	@Query("SELECT u FROM User u WHERE u.role > 1 AND u.status =1")
	List<User> findUserSalse();

	List<User> findByStatusLike(Integer status);
}
