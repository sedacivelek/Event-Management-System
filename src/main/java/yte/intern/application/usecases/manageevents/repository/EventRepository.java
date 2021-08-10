package yte.intern.application.usecases.manageevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yte.intern.application.usecases.manageevents.entity.Event;
import yte.intern.application.usecases.manageevents.entity.Participant;

import javax.transaction.Transactional;
import java.util.Optional;

//@Repository("eventRepository")
public interface EventRepository extends JpaRepository<Event,Long> {
    Optional<Event> getByEventId(String eventId);
    Event findByEventId(String eventId);
    @Transactional
    void deleteByEventId(String eventId);
}
