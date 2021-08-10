package yte.intern.application.usecases.manageevents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.intern.application.usecases.manageevents.dto.EventDTO;
import yte.intern.application.usecases.manageevents.dto.ParticipantDTO;
import yte.intern.application.usecases.manageevents.entity.Event;
import yte.intern.application.usecases.manageevents.entity.Participant;
import yte.intern.application.usecases.manageevents.exception.CustomException;
import yte.intern.application.usecases.manageevents.mailsender.EmailService;
import yte.intern.application.usecases.manageevents.mapper.EventMapper;
import yte.intern.application.usecases.manageevents.mapper.ParticipantMapper;
import yte.intern.application.usecases.manageevents.service.ManageEventService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated

public class ManageEventController {

    private final ManageEventService manageEventService;
    private final EmailService emailService;
    private final EventMapper eventMapper;
    private final ParticipantMapper participantMapper;


    @GetMapping("/events")
    @PreAuthorize("permitAll()")
    public List<EventDTO> listAllEvents() {
        List<Event> events = manageEventService.listAllEvents();
        List<EventDTO> dtoEvent = eventMapper.mapToDto(events);
        return dtoEvent;
    }


    @PostMapping("/events")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventDTO addEvent(@Valid @RequestBody EventDTO eventDTO){
        Event addedEvent = manageEventService.addEvent(eventMapper.mapToEntity(eventDTO));
        EventDTO dtoEvent = eventMapper.mapToDto(addedEvent);
        return dtoEvent;
    }

    @GetMapping("/events/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventDTO getEventByEventId(@PathVariable String eventId){
        Event event = manageEventService.getByEventId(eventId);
        return eventMapper.mapToDto(event);
    }

    @PutMapping("/events/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventDTO updateEvent(@PathVariable String eventId,@Valid @RequestBody EventDTO eventDTO) throws CustomException {
        Event event = eventMapper.mapToEntity(eventDTO);
        Event addedEvent = manageEventService.updateEvent(eventId,event);
        return eventMapper.mapToDto(addedEvent);
    }
    @DeleteMapping("/events/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEvent(@PathVariable String eventId) throws CustomException {
        manageEventService.deleteEvent(eventId);
    }
    @GetMapping("/participants")
    @PreAuthorize("permitAll()")
    public List<EventDTO> listAllEventsForParticipants() {
        List<Event> events = manageEventService.listAllEvents();
        List<EventDTO> dtoEvent = eventMapper.mapToDto(events);
        return dtoEvent;
    }
    @PostMapping("/participants/{eventId}")
    @PreAuthorize("permitAll()")
    public void addParticipantToEvent(@PathVariable String eventId,@Valid @RequestBody ParticipantDTO participantDTO) throws Exception {
        Participant addedParticipant = manageEventService.addParticipantToStudent(eventId, participantMapper.mapToEntity(participantDTO));
        emailService.sendMail(participantMapper.mapToEntity(participantDTO),eventId);
    }
    @GetMapping("/participants/{eventId}")
    @PreAuthorize("permitAll()")
    public List<ParticipantDTO> getEventParticipants(@PathVariable String eventId){
        Set<Participant> eventParticipants = manageEventService.getEventParticipants(eventId);
        return participantMapper.mapToDto(new ArrayList<>(eventParticipants));
    }
    @GetMapping("/participants/{eventId}/address")
    @PreAuthorize("permitAll()")
    public EventDTO getEventForAddress(@PathVariable String eventId){
        Event event = manageEventService.getByEventId(eventId);
        return eventMapper.mapToDto(event);
    }

}
