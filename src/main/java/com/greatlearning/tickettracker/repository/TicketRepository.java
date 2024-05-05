package com.greatlearning.tickettracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.tickettracker.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	List<Ticket> findByTitleContainingOrderByIdAsc(String keyword);

	List<Ticket> findByShortdescriptionContainingOrderByIdAsc(String keyword);

	List<Ticket> findAllByOrderByIdAsc();

}
