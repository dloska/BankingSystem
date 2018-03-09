package com.spring.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.storage.Storage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
@SuppressWarnings("serial")
@Entity
@Table(name = "`user`")
public class User implements java.io.Serializable {

  private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
  private static final String EMAIL_MESSAGE = "{email.message}";
  private static final String EMAIL_EXISTS_MESSAGE = "{email-exists.message}";

  @Id
  @GeneratedValue
  @Column(name = "user_id", unique = true, nullable = false)
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(unique = true)
  @NotBlank(message = NOT_BLANK_MESSAGE)
  @Email(message = EMAIL_MESSAGE)
  private String email;

  @Column(name = "first_name")
  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String firstName;

  @Column(name = "last_name")
  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String lastName;

  @JsonIgnore
  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String password;

  @NotBlank(message = NOT_BLANK_MESSAGE)
  private String role = RoleConstants.USER.getRoleConstant();

  private Instant created = Instant.now();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST)
  private List<Storage> storages = new ArrayList<>();

  protected User() {

  }

  public void addStorage(Storage storage) {
    storages.add(storage);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", password='" + password + '\'' +
        ", role='" + role + '\'' +
        ", created=" + created +
        '}';
  }

  public enum RoleConstants {
    CLIENT("ROLE_CLIENT"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String userProfileType;

    RoleConstants(String userProfileType) {
      this.userProfileType = userProfileType;
    }

    public String getRoleConstant() {
      return userProfileType;
    }

  }


}
