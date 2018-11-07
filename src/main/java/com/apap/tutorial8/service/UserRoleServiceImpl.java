package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public UserRoleModel getUser(String username) {
		return userDb.findByUsername(username);
	}
	
	@Override
	public boolean validatePassword(String passwordLama, String username) {
		UserRoleModel user = userDb.findByUsername(username);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(passwordEncoder.matches(passwordLama, user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public void updatePassword(String password, String username) {
		UserRoleModel user = userDb.findByUsername(username);
		String passEncrypt = this.encrypt(password);
		user.setPassword(passEncrypt);
		userDb.save(user);
	}

	@Override
	public boolean validatePasswordFormat(String password) {
		boolean matching = password.matches("(?=.*[0-9])(?=.*[a-zA-Z]).{8,}");
		return matching;
	}
	
}
