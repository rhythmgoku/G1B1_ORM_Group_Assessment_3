package com.greatlearning.tickettracker.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greatlearning.tickettracker.entity.Ticket;
import com.greatlearning.tickettracker.services.CustomUserDetailsService;
import com.greatlearning.tickettracker.services.TrackerService;
import com.greatlearning.tickettracker.util.UserUtil;

@Controller
@RequestMapping("/{role}/tickets")
public class TicketTrackerController {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private TrackerService trackerService;

	@Autowired
	Map<Integer, String> roleMap;

	private String role;

	private String userName;

	@GetMapping(value = { "", "/" })
	public String getAllTickets(Model model) {
		Set<Ticket> ticketsList = new LinkedHashSet<>(trackerService.getAllTickets());

		this.role = UserUtil.getHighestRole(customUserDetailsService.geEffectiveUser(), roleMap).toLowerCase();
		this.userName = StringUtils.capitalize(customUserDetailsService.geEffectiveUser().getUsername());

		populateModleAttributes(model, null, ticketsList, "");

		return "tickets";

	}

	@PostMapping("/search")
	public String searchTicket(@ModelAttribute("search") String keyword, Model model) {
		Set<Ticket> ticketList = trackerService.findTicketsBykeyword(keyword);
		populateModleAttributes(model, null, ticketList, keyword);

		return "tickets";
	}

	@GetMapping("/addnew")
	public String createTicketForm(Model model) {
		populateModleAttributes(model, new Ticket(), null, null);
		return "create_ticket";
	}

	@PostMapping("/save")
	public String saveTicket(@ModelAttribute("ticket") Ticket ticket) throws Exception {
		trackerService.saveOrUpdateTicket(ticket, null);
		return "redirect:/" + role + "/tickets";
	}

	@GetMapping("/{id}/edit")
	public String editTicketForm(@PathVariable("id") int id, Model model) {
		Ticket ticket = trackerService.getTicketById(id);
		populateModleAttributes(model, ticket, null, null);
		return "edit_ticket";
	}

	@GetMapping("/{id}/view")
	public String viewTicketForm(@PathVariable("id") int id, Model model) {
		Ticket ticket = trackerService.getTicketById(id);
		populateModleAttributes(model, ticket, null, null);
		return "view_tickets";
	}

	@GetMapping("/{id}/delete")
	public String deleteTicket(@PathVariable("id") String id) {
		trackerService.deleteTicketById(Integer.parseInt(id));
		return "redirect:/" + role + "/tickets";
	}

	@PostMapping("/{id}")
	public String updateTicket(@PathVariable("id") String id, @ModelAttribute("ticket") Ticket updatedTicket)
			throws Exception {
		trackerService.saveOrUpdateTicket(updatedTicket,Integer.parseInt(id));
		return "redirect:/" + role + "/tickets";
	}

	@GetMapping("/health-check")
	public String healthCheck() {
		return "UP";

	}

	private void populateModleAttributes(Model model, Ticket ticket, Set<Ticket> tickets, String search) {

		model.addAttribute("role", role);
		model.addAttribute("username", userName);

		if (null != tickets && !tickets.isEmpty()) {
			model.addAttribute("tickets", tickets);
		}
		if (null != search) {
			model.addAttribute("search", search);
		}
		if (null != ticket) {
			model.addAttribute("ticket", ticket);

		}

	}

}
