package com.greatlearning.tickettracker.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greatlearning.tickettracker.entity.Users;
import com.greatlearning.tickettracker.services.CustomUserDetailsService;
import com.greatlearning.tickettracker.util.UserUtil;

@Controller
public class UserController {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	Map<Integer, String> roleMap;

	private String role;

	private String userName;

	@RequestMapping(value = { "/", "/home" })
	public String index(Model model) {

		Users user = customUserDetailsService.geEffectiveUser();
		String highestRole = findHighestRole(user);

		userName = user.getUsername();
		role = highestRole;

		model.addAttribute("username", userName.toUpperCase());
		model.addAttribute("role", role);

		userName = user.getUsername();
		role = highestRole;

		return "home";
	}

	private String findHighestRole(Users user) {
		return UserUtil.getHighestRole(user, roleMap);
	}

	@RequestMapping(value = { "/accessdenied" })
	public String accessDenied(Model model) {
		model.addAttribute("errorCode", "403");
		model.addAttribute("errorUniqueMessage", "You don't have enough privileges to perform this action");
		model.addAttribute("username", userName);
		model.addAttribute("role", role.toLowerCase());
		return "error";
	}

	@RequestMapping(value = { "/logon" })
	public String logon(Model model) {
		return "logon";
	}

	@RequestMapping(value = { "/redirect-to-base" })
	public String redirectToBase() {
		return "redirect:/" + role.toLowerCase() + "/tickets";
	}

	@GetMapping("/exit")
	public String logout(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			customUserDetailsService.seEffectiveUser(null);
		}
		return "exit";
	}

}
