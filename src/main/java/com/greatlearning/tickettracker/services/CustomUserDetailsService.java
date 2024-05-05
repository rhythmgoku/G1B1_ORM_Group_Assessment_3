package com.greatlearning.tickettracker.services;

import com.greatlearning.tickettracker.entity.Users;

public interface CustomUserDetailsService {
	
	Users geEffectiveUser();
	
	void seEffectiveUser(Users users);


}
