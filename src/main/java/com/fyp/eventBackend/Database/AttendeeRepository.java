package com.fyp.eventBackend.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {

}
