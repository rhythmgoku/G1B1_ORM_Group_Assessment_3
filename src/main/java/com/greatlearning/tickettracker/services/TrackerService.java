package com.greatlearning.tickettracker.services;

import java.util.List;
import java.util.Set;

import com.greatlearning.tickettracker.entity.Ticket;

public interface TrackerService {

	List<Ticket> getAllTickets();

	Ticket saveOrUpdateTicket(Ticket ticket, Integer id) throws Exception;

	void deleteTicketById(int int1);

	Ticket getTicketById(int id);

	Set<Ticket> findTicketsBykeyword(String keyword);

}
