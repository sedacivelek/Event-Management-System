package yte.intern.application.usecases.manageevents.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import yte.intern.application.usecases.manageevents.validation.UniqueEventId;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Builder
public class EventDTO {
    @NotBlank(message = "Event id is mandatory.")
    //@UniqueEventId
    @Size(max=15,message = "Event id can be at most 15 characters.")
    public final String eventId;
    @Size(max=255,message = "Event name can be at most 255 characters.")
    @NotBlank(message = "Event name is mandatory.")
    public final String eventName;
    @FutureOrPresent(message = "Started event can't be added.")
    public final LocalDateTime startDate;
    @FutureOrPresent(message = "Ended event can't be added.")
    public final LocalDateTime endDate;
    @Min(value = 5, message = "Quota can be minimum 5.")
    @Max(value=10000,message = "Quota can be maximum 100.")
    public final int quota;
    public final String address;

    @AssertTrue(message = "Start date must be before than end date.")
    public boolean isStartDateBeforeEndDateValid(){
        return startDate.isBefore(endDate);
    }

    @JsonCreator
    public EventDTO( @JsonProperty("eventId") String eventId,
                    @JsonProperty("eventName") String eventName,
                    @JsonProperty("startDate") LocalDateTime startDate,
                    @JsonProperty("endDate") LocalDateTime endDate,
                    @JsonProperty("quota") int quota,
                    @JsonProperty("address") String address){

        this.eventName = eventName;
        this.eventId = eventId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quota = quota;
        this.address = address;
    }
}
