package yte.intern.application.usecases.manageevents.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEventIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UniqueEventId {
    public String message() default "There is already event with this event id!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};
}




