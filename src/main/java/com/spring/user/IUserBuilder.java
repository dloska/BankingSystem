package com.spring.user;

import com.spring.utils.IBuilder;

public interface IUserBuilder extends IBuilder<User> {

  IUserBuilder setEmail(String email);

  IUserBuilder setPassword(String password);

  IUserBuilder setRole(String role);

  IUserBuilder setLastName(String lastName);

  IUserBuilder setFirstName(String firstName);
}
