package com.greatlearning.tickettracker.dataloader;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.greatlearning.tickettracker.entity.Ticket;
import com.greatlearning.tickettracker.repository.TicketRepository;
import com.greatlearning.tickettracker.services.TrackerServiceImpl;

@Component
public class TicketPreDataLoader implements CommandLineRunner{

	@Autowired
	TicketRepository ticketRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Date date = new Date();
		
		DateFormat DATE_FORMAT = TrackerServiceImpl.DATE_FORMAT;

		Ticket ticket1 = Ticket.builder().title("TKT001").shortdescription("Basic Ticket Info About Ticket 1").content("This Info is About Ticket 1").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket2 = Ticket.builder().title("TKT002").shortdescription("Basic Ticket Info About Ticket 2").content("This Info is About Ticket 2").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket3 = Ticket.builder().title("TKT003").shortdescription("Basic Ticket Info About Ticket 3").content("This Info is About Ticket 3").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket4 = Ticket.builder().title("TKT004").shortdescription("Basic Ticket Info About Ticket 4").content("This Info is About Ticket 4").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket5 = Ticket.builder().title("TKT005").shortdescription("Basic Ticket Info About Ticket 5").content("This Info is About Ticket 5").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket6 = Ticket.builder().title("TKT006").shortdescription("Basic Ticket Info About Ticket 6").content("This Info is About Ticket 6").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket7 = Ticket.builder().title("TKT007").shortdescription("Basic Ticket Info About Ticket 7").content("This Info is About Ticket 7").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket8 = Ticket.builder().title("TKT008").shortdescription("Basic Ticket Info About Ticket 8").content("This Info is About Ticket 8").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket9 = Ticket.builder().title("TKT009").shortdescription("Basic Ticket Info About Ticket 9").content("This Info is About Ticket 9").createdOn(DATE_FORMAT.format(date)).build();
		Ticket ticket10 = Ticket.builder().title("TKT010").shortdescription("Basic Ticket Info About Ticket 10").content("This Info is About Ticket 10").createdOn(DATE_FORMAT.format(date)).build();
		
		List<Ticket> tickets = List.of(ticket1,ticket2,ticket3,ticket4,ticket5,ticket6,ticket7,ticket8,ticket9,ticket10);
		ticketRepository.saveAll(tickets);

	}
	
	
}
