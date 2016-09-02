package com.castis.c3.dao;

import java.util.List;

import com.castis.c3.dto.Ticket;

public interface TicketDao {
	public Number add(Ticket ticket);

	public void deleteAll();

	public Ticket get(Number ticketNumber);

	public List<Ticket> getAll();

	public int getCount();
}
