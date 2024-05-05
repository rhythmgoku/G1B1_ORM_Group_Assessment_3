package com.greatlearning.tickettracker.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.tickettracker.entity.Ticket;
import com.greatlearning.tickettracker.repository.TicketRepository;

@Service
public class TrackerServiceImpl implements TrackerService {

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	TicketRepository ticketRepository;

	@Override
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAllByOrderByIdAsc();
	}

	@Override
	public Ticket saveOrUpdateTicket(Ticket ticket, Integer id) throws Exception {
		Date date = new Date();

		if (null != id) {

			if (null != getTicketById(id)) {
				ticket.setCreatedOn(DATE_FORMAT.format(date));
				ticket.setEditedOn(DATE_FORMAT.format(date));

			} else {
				throw new Exception(
						"Ticket with provided Ticket Id does not exists in the database, Please Add the Ticket before editing ");
			}

		} else {
			ticket.setCreatedOn(DATE_FORMAT.format(date));

		}

		return ticketRepository.save(ticket);
	}

	@Override
	public void deleteTicketById(int id) {
		ticketRepository.deleteById(id);

	}

	@Override
	public Ticket getTicketById(int id) {
		return ticketRepository.findById(id).get();

	}

	@Override
	public Set<Ticket> findTicketsBykeyword(String keyword) {

		List<Ticket> ticketsList = new ArrayList<>();
		if (StringUtils.isBlank(keyword)) {
			ticketsList = getAllTickets();
		} else {
			ticketsList.addAll(ticketRepository.findByShortdescriptionContainingOrderByIdAsc(keyword));
			ticketsList.addAll(ticketRepository.findByTitleContainingOrderByIdAsc(keyword));
		}

		return new LinkedHashSet<>(ticketsList);

	}

}
