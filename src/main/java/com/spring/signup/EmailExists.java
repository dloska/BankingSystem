package com.spring.signup;

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
import org.springframework.stereotype.Component;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = com.spring.signup.EmailExistsValidator.class)
@Documented
public @interface EmailExists {

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

@Component
class EmailExistsValidator implements ConstraintValidator<com.spring.signup.EmailExists, String> {

  private final UserRepository userRepository;

  public EmailExistsValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public void initialize(com.spring.signup.EmailExists constraintAnnotation) {

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return userRepository.findOneByEmail(value) == null;
  }
}
