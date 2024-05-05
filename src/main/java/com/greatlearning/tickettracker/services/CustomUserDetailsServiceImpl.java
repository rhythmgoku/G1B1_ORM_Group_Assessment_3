package com.greatlearning.tickettracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.greatlearning.tickettracker.entity.Users;
import com.greatlearning.tickettracker.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService, UserDetailsService {

	@Autowired
	UserRepository customUserRepository;

	private static Users loggedInUser;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users customUser = customUserRepository.findByUsername(username);
		setLoggedInUser(customUser);
		if (customUser == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		return customUser;
	}

	@Override
	public Users geEffectiveUser() {
		return loggedInUser;
	}

	@Override
	public void seEffectiveUser(Users users) {
		setLoggedInUser(users);
	}

	private static void setLoggedInUser(Users users) {
		loggedInUser = users;
	}

}
