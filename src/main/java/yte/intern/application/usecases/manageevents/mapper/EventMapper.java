package yte.intern.application.usecases.manageevents.mapper;

import org.mapstruct.Mapper;
import yte.intern.application.usecases.manageevents.dto.EventDTO;
import yte.intern.application.usecases.manageevents.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface  EventMapper {

    EventDTO mapToDto(Event event);
    List<EventDTO> mapToDto(List<Event> eventList);
    Event mapToEntity(EventDTO eventDTO);
    List<Event> mapToEntity(List<EventDTO> eventDTOList);
}
