package com.apap.tutorial8.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		if(userService.validatePasswordFormat(user.getPassword())) {
			userService.addUser(user);
		} else{
			model.addAttribute("error", "Password Tidak Sesuai Format");
		}
		return "home";
	}
	
	@RequestMapping(value="/updatepassword", method = RequestMethod.GET)
	private String updatePassword() {
		return "update-password";
	}
	
	@RequestMapping(value="/success", method = RequestMethod.POST)
	private String updatePasswordSubmit(@RequestParam(value = "password") String password,
			@RequestParam(value = "passwordBaru") String passwordBaru,
			@RequestParam(value = "konfirmasi") String konfirmasi,
			HttpServletRequest request, Model model) {
		
		ArrayList<String> error = new ArrayList<String>();
		//validasi password sekarang
		if(userService.validatePassword(password, request.getRemoteUser())) {
			//validasi password baru dan konfirmasi password baru
			if(passwordBaru.equals(konfirmasi)) {
				if(userService.validatePasswordFormat(passwordBaru)) {
					userService.updatePassword(passwordBaru, request.getRemoteUser());
				} else {
					model.addAttribute("error", "Password Tidak Sesuai Format");
				}
			}
			else {
				model.addAttribute("error", "Password Baru Konfirmasi Berbeda");
			}
		}
		else {
			model.addAttribute("error", "Password Lama Salah");
		}
		return "update-password";
	}
}
