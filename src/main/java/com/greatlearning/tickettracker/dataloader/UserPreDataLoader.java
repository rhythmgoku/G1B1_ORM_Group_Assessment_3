package com.greatlearning.tickettracker.dataloader;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.greatlearning.tickettracker.entity.Roles;
import com.greatlearning.tickettracker.entity.Users;
import com.greatlearning.tickettracker.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserPreDataLoader implements CommandLineRunner {

	@Value("${username.role.admin:#{admin}}")
	private String adminUsers;

	@Value("${username.role.user:#{user}}")
	private String normalUsers;

	@Autowired
	PasswordEncoder encoder;

	private final UserRepository userRepository;

	public UserPreDataLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		log.info("populating intial user data");
		Arrays.asList(adminUsers.split(",")).stream()
				.forEach(enrty -> userRepository.save(Users.builder()
						.username(enrty)
						.password(encoder.encode(enrty))
						.accountEnabledStatus(1)
						.accountExpiryDate(LocalDate.now().plusDays(5))
						.accountLockedStatus(1)
						.credentialsExpiryDate(LocalDate.now().plusDays(5))
						.roles(List.of(Roles.builder().name("ADMIN").build(), Roles.builder().name("USER").build()))
						.build()));

		Arrays.asList(normalUsers.split(",")).stream()
				.forEach(enrty -> userRepository.save(Users.builder()
						.username(enrty)
						.password(encoder.encode(enrty))
						.accountEnabledStatus(1)
						.accountExpiryDate(LocalDate.now().plusDays(5))
						.accountLockedStatus(1)
						.credentialsExpiryDate(LocalDate.now().plusDays(5))
						.roles(List.of(Roles.builder().name("USER").build()))
						.build()));

	}

}
