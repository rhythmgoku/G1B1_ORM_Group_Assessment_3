package com.greatlearning.tickettracker.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Users implements UserDetails  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	private String username;

	private String password;

	private LocalDate accountExpiryDate;

	private int accountLockedStatus;

	private LocalDate credentialsExpiryDate;

	private int accountEnabledStatus;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private List<Roles> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.getAccountExpiryDate().isAfter(LocalDate.now());
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.getAccountLockedStatus() == 1;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.getCredentialsExpiryDate().isAfter(LocalDate.now());
	}

	@Override
	public boolean isEnabled() {
		return this.getAccountEnabledStatus() == 1;
	}

}