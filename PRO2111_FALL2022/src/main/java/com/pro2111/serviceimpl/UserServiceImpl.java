package com.pro2111.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.User;
import com.pro2111.repositories.UserRepository;
import com.pro2111.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public synchronized List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public synchronized User findById(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public synchronized User create(User user) {
		return userRepository.save(user);
	}

	@Override
	public synchronized User update(User user) {
		return userRepository.save(user);
	}

	@Override
	public synchronized List<User> findApproximatePhone(String phone) {
		return userRepository.findApproximatePhone(phone);
	}

	@Override
	public synchronized User delete(User user) {
		userRepository.delete(user);
		return user;
	}

	@Override
	public synchronized User findByEmail(String email) {
		return userRepository.findByEmailLike(email);
	}
	
	@Override
	public synchronized User findByPhone(String phone) {
		return userRepository.findByPhoneLike(phone);
	}

	@Override
	public synchronized List<User> findApproximatePhoneorEmail(String input) {
		return userRepository.findApproximatePhoneorEmail(input);
	}

	@Override
	public synchronized List<User> findUserSalse() {
		return userRepository.findUserSalse();
	}

	@Override
	public synchronized List<User> findByStatus(Integer status) {
		return userRepository.findByStatusLike(status);
	}

}
