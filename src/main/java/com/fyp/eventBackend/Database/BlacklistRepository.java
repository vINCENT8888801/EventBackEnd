package com.fyp.eventBackend.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BlacklistRepository extends JpaRepository<Blacklist, Integer> {

	Blacklist findByObjectToken(String objectToken);

}
