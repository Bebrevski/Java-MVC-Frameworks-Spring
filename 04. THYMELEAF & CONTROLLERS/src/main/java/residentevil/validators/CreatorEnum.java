package residentevil.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = CreatorEnumImpl.class)
@ReportAsSingleViolation
public @interface CreatorEnum {

    Class<? extends Enum<?>> enumClazz();

    String message() default "Invalid Creator. Can be 'Corp' or 'corp'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
