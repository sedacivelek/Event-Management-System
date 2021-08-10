package yte.intern.application.usecases.manageevents.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import yte.intern.application.usecases.manageevents.entity.Participant;
import yte.intern.application.usecases.manageevents.validation.Tckn;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class ParticipantDTO {

    @NotBlank(message = "Name and surname is mandatory.")
    public final String nameAndSurname;

    @NotBlank(message = "Tckn is mandatory.")
    @Tckn(message = "Tckn must be valid.")
    @Size(min=11,max=11,message="Tckn must be 11 characters long.")
    public final String tckn;

    @Email(message = "Wrong email format.")
    @NotBlank(message = "Email is mandatory.")
    public final String email;
    @NotBlank(message = "Participant name is mandatory.")
    public final String participantName;
    @JsonCreator
    public ParticipantDTO(@JsonProperty("nameAndSurname") String nameAndSurname,
                          @JsonProperty("tckn") String tckn,
                          @JsonProperty("email") String email,
                          @JsonProperty("participantName") String participantName){
        this.nameAndSurname = nameAndSurname;
        this.tckn = tckn;
        this.email = email;
        this.participantName = participantName;
    }
}
