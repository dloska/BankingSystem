package com.spring.profile;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.spring.user.UserRepository;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailExistsForProfileUpdateValidator.class)
@Documented
public @interface EmailExistsForProfileUpdate {

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

@Component
class EmailExistsForProfileUpdateValidator implements
    ConstraintValidator<EmailExistsForProfileUpdate, String> {

  private final UserRepository userRepository;

  public EmailExistsForProfileUpdateValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public void initialize(EmailExistsForProfileUpdate constraintAnnotation) {

  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    Assert.notNull(currentUserEmail);
    boolean ifExists = !org.thymeleaf.util.StringUtils.equalsIgnoreCase(currentUserEmail, email)
        && userRepository.findOneByEmail(email) == null;
    return !ifExists;
  }
}

