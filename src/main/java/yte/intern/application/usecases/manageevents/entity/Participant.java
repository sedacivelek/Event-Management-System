package yte.intern.application.usecases.manageevents.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yte.intern.application.usecases.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name="idgen",sequenceName = "PARTICIPANT_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class Participant extends BaseEntity {

    @Column(name= "NAME_AND_SURNAME")
    private String nameAndSurname;

    @Column(name = "TCKN",unique=true)
    private String tckn;

    @Column(name="EMAIL",unique = true)
    private String email;

    @Column(name= "PARTICIPANT_NAME",unique=true)
    private String participantName;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "participants")

    Set<Event> enrolledEvents;


}
