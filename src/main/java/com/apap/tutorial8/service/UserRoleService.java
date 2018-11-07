package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	UserRoleModel getUser(String username);
	public String encrypt(String password);
	public boolean validatePassword(String passwordLama, String username);
	void updatePassword(String password, String username);
	boolean validatePasswordFormat(String password);
}
