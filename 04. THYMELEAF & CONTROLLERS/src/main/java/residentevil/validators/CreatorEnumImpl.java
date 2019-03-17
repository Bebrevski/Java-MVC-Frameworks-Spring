package residentevil.validators;

import residentevil.domain.entities.Creator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreatorEnumImpl implements ConstraintValidator<CreatorEnum, Creator> {
    @Override
    public boolean isValid(Creator value, ConstraintValidatorContext context) {
        for (Creator creator : Creator.values()) {
            if (value == null) {
                return false;
            }
            if (value.name().equals(creator.name())) {
                return true;
            }
        }

        return false;
    }
}
