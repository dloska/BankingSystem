package com.spring.user;

import com.github.javafaker.Faker;
import com.spring.storage.Storage;
import java.util.ArrayList;
import java.util.List;

public final class UserBuilder implements IUserBuilder {

  private String email;
  private String firstName;
  private String lastName;
  private String password;
  private String role = "ROLE_USER";
  private List<Storage> storages = new ArrayList<>();

  private UserBuilder() {
  }

  public static UserBuilder aUser() {
    return new UserBuilder();
  }

  public UserBuilder setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserBuilder setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder setRole(String role) {
    this.role = role;
    return this;
  }

  public UserBuilder setStorages(List<Storage> storages) {
    this.storages = storages;
    return this;
  }

  public User build() {
    User user = new User();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPassword(password);
    user.setRole(role);
    user.setStorages(storages);
    return user;
  }

  @Override
  public User generateExample() {
    Faker faker = new Faker();

    User user = UserBuilder.aUser()
        .setEmail(faker.internet().emailAddress())
        .setPassword(faker.internet().password())
        .setRole(User.RoleConstants.USER.getRoleConstant())
        .setFirstName(faker.name().firstName())
        .setLastName(faker.name().lastName()).build();

    return user;
  }
}
