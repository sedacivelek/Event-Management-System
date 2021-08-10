package yte.intern.application.usecases.manageevents.mapper;

import org.mapstruct.Mapper;
import yte.intern.application.usecases.manageevents.dto.EventDTO;
import yte.intern.application.usecases.manageevents.dto.ParticipantDTO;
import yte.intern.application.usecases.manageevents.entity.Event;
import yte.intern.application.usecases.manageevents.entity.Participant;

import java.util.List;

@Mapper(componentModel="spring")
public interface ParticipantMapper {
    ParticipantDTO mapToDto(Participant participant);
    List<ParticipantDTO> mapToDto(List<Participant> participantList);
    Participant mapToEntity(ParticipantDTO participantDTO);
    List<Participant> mapToEntity(List<ParticipantDTO> participantDTOList);
}
