package yte.intern.application.usecases.manageevents.entity;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cglib.core.Local;
import yte.intern.application.usecases.common.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen",sequenceName = "EVENT_SEQ")

public class Event extends BaseEntity {
    @Column(name="EVENT_ID",unique = true)
    private String eventId;
    @Column(name="EVENT_NAME")
    private String eventName;
    @Column(name="START_DATE")
    private LocalDateTime startDate;
    @Column(name="END_DATE")
    private LocalDateTime endDate;
    @Column(name="QUOTA")
    private int quota;
    @Column(name="ADDRESS")
    private String address;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "EVENT_PARTICIPANTS",
            joinColumns = @JoinColumn(name="EVENT_ID"),
            inverseJoinColumns = @JoinColumn(name="PARTICIPANT_NAME")
    )
    Set<Participant> participants;
    public boolean hasParticipant(Participant participant){
        return participants.stream().anyMatch(it -> it.getTckn().equals(participant.getTckn()));
    }
    public boolean isFull(){
        return (quota==participants.size());
    }
    public boolean isStarted(){

        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.isAfter(startDate);
    }

}
