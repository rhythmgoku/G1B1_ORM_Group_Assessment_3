package com.greatlearning.tickettracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.tickettracker.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{

	public Users findByUsername(String s);
}