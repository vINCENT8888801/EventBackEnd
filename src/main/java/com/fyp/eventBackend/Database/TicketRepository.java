package com.fyp.eventBackend.Database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	@Query("SELECT COUNT(u) FROM Ticket u WHERE u.event=?1")
    Long getTotalTicketByEventId(int eventId);
	
	@Query("SELECT u.ticketNo FROM Ticket u WHERE u.ticketNo IS NOT NULL")
    List<String> getAllTicketNo();
	
	List<Ticket> findByTicketNo(String ticketNo);
}