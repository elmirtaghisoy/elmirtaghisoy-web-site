package az.et.ws.component.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsValidFile.FileValidator.class)
public @interface IsValidFile {
    String message() default "File is null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class FileValidator implements ConstraintValidator<IsValidFile, MultipartFile> {
        @Override
        public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
            return file.getSize() > 0;
        }
    }
}
