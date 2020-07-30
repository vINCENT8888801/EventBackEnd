package com.fyp.eventBackend.Database;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository extends JpaRepository<Event, Integer> {
	
	Event findByName(String username);
	
	Page<Event> findAllByDatetimeBetween(Date dateTimeStart,Date dateTimeEnd, Pageable page);
	
}
