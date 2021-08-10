package yte.intern.application.usecases.manageevents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.application.usecases.manageevents.entity.Event;
import yte.intern.application.usecases.manageevents.entity.Participant;
import yte.intern.application.usecases.manageevents.exception.CustomException;
import yte.intern.application.usecases.manageevents.repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ManageEventService {
    private final EventRepository eventRepository;
    public List<Event> listAllEvents(){
        /*List<Event> eventList = eventRepository.findAll().stream().filter(it->(it.isStarted()==false))
                .collect(toList());

        return eventList;*/
        return eventRepository.findAll();
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);

    }

    public Event getByEventId(String eventId) {

        return eventRepository.getByEventId(eventId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Event updateEvent(String eventId, Event event) throws CustomException {
        Optional<Event> eventRep = eventRepository.getByEventId(eventId);
        if(eventRep.isPresent()){
            Event eventFromDB = eventRep.get();
            if(eventFromDB.isStarted()){
                throw new CustomException("Event has been started. You can't update this event.");
            }
            else {
                updateEventDB(event, eventFromDB);
                return eventRepository.save(eventFromDB);
            }
        }
        else{
            throw new EntityNotFoundException();
        }
    }



    private void updateEventDB(Event event,Event eventDB){
        eventDB.setEventName(event.getEventName());
        eventDB.setStartDate(event.getStartDate());
        eventDB.setEndDate(event.getEndDate());
        eventDB.setQuota(event.getQuota());
        eventDB.setAddress(event.getAddress());

    }

    public void deleteEvent(String eventId) throws CustomException {
        Optional<Event> eventRep = eventRepository.getByEventId(eventId);
        if(eventRep.isPresent()){
            Event eventFromDB = eventRep.get();
            if ( eventFromDB.isStarted()){
                throw new CustomException("Event has been started. You can't delete this event.");
            }
            else{
                eventRepository.deleteByEventId(eventId);
            }
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    public Participant addParticipantToStudent(String eventId, Participant participant) throws CustomException {
        Optional<Event> eventOptional = eventRepository.getByEventId(eventId);
        if(eventOptional.isPresent()){
            Event event = eventOptional.get();
            Set<Participant> participants = event.getParticipants();
            if(event.hasParticipant(participant)){
                throw new CustomException("You have already applied for this event.");
            }
            else if(event.isFull()){
                throw new CustomException("Quota is full. Please check other events.");
            }
            else if(event.isStarted()){
                throw new CustomException("Event has been started. You can't apply for this event.");
            }
            else{
                participants.add(participant);
                Event savedEvent = eventRepository.save(event);
                Participant returned = savedEvent.getParticipants().stream()
                        .filter(it->it.getNameAndSurname().equals(participant.getNameAndSurname()))
                        .collect(toList())
                        .get(0);
                return returned;
            }
        }
        else{
            throw new EntityNotFoundException();
        }

    }

    public Set<Participant> getEventParticipants(String eventId) {
        return eventRepository.getByEventId(eventId).map(Event::getParticipants)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public boolean isEventIdAlreadyInUse(String eventId) {


            boolean eventInDb = true;
            if (eventRepository.findByEventId(eventId) == null) eventInDb = false;
            return eventInDb;
    }

    
}
