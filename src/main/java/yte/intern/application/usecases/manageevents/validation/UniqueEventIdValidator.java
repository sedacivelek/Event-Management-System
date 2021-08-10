package yte.intern.application.usecases.manageevents.validation;

import org.springframework.beans.factory.annotation.Autowired;
import yte.intern.application.usecases.manageevents.service.ManageEventService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEventIdValidator implements ConstraintValidator<UniqueEventId,String> {

    /*@Override
    public void initialize(UniqueEventId constraint) {
    }*/

    @Autowired
    private ManageEventService eventService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean fromDB= (eventService.isEventIdAlreadyInUse(value));
        boolean val = !fromDB;
        return val;
    }

}
