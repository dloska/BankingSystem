package com.spring.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.config.WebAppConfigurationAware;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class UserRepositoryTest extends WebAppConfigurationAware {

  @Value("${test.config.database.howManyUsersShouldBeGenerated}")
  private int howManyUsersShouldBeGenerated;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void shouldGetAllUsers() {
    List<User> returnedUsers = userRepository.findAll();
    log.debug(String.format("Users found: %s", Arrays.toString(returnedUsers.toArray())));
    assertThat(returnedUsers.size()).isEqualTo(howManyUsersShouldBeGenerated);
  }

  @Test
  public void shouldCheckIfUserExists() {
    User user1 = UserBuilder.aUser().setEmail("userTest@email.com").setPassword("demo")
        .setRole(User.RoleConstants.USER.getRoleConstant()).setFirstName("userFirstName")
        .setLastName("userLastName").build();

    // act
    userRepository.save(user1);
    assertThat(userRepository.findOneByEmail(user1.getEmail())).isNotNull();
    userRepository.delete(user1);

  }

}