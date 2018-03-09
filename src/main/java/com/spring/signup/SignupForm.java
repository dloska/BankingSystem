package com.spring.signup;

import com.spring.user.User;
import com.spring.user.UserBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
public class SignupForm {

  private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
  private static final String EMAIL_MESSAGE = "{email.message}";
  private static final String EMAIL_EXISTS_MESSAGE = "{email-exists.message}";

  @NotBlank(message = NOT_BLANK_MESSAGE)
  @Email(message = EMAIL_MESSAGE)
  @EmailExists(message = EMAIL_EXISTS_MESSAGE)
  private String email;

  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String password;

  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String firstName;

  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String lastName;

  public final User createUser() {
    return UserBuilder.aUser().setEmail(getEmail()).setPassword(getPassword())
        .setRole(User.RoleConstants.USER.getRoleConstant()).setFirstName(getFirstName())
        .setLastName(getLastName()).build();
  }
}
