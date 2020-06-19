package com.fyp.eventBackend.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository extends JpaRepository<Event, Integer> {
	
	Event findByName(String username);
	
	
	
}
