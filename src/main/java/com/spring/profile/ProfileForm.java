package com.spring.profile;

import com.spring.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
public class ProfileForm {

  private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
  private static final String EMAIL_MESSAGE = "{email.message}";
  private static final String EMAIL_EXISTS_MESSAGE = "{email-exists.message}";

  @NotBlank(message = NOT_BLANK_MESSAGE)
  @Email(message = EMAIL_MESSAGE)
  @EmailExistsForProfileUpdate(message = EMAIL_EXISTS_MESSAGE)
  private String email;

  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String password;

  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String firstName;

  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String lastName;

  public ProfileForm() {

  }

  public ProfileForm(User user) {
    setEmail(user.getEmail());
    setFirstName(user.getFirstName());
    setLastName(user.getLastName());
  }

  public User updateUserFields(User userToUpdate) {
    userToUpdate.setEmail(getEmail());
    userToUpdate.setFirstName(getFirstName());
    userToUpdate.setLastName(getLastName());
    userToUpdate.setPassword(getPassword());
    return userToUpdate;
  }
}
