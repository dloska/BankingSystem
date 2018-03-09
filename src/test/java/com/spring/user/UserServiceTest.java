package com.spring.user;

import static java.util.function.Predicate.isEqual;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  @InjectMocks
  private UserService userService = new UserService();
  @Mock
  private UserRepository userRepositoryMock;
  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  public void shouldInitializeWithTwoDemoUsers() {
    User user1 = UserBuilder.aUser().setEmail("user").setPassword("demo")
        .setRole(User.RoleConstants.USER.getRoleConstant()).setFirstName("userFirstName")
        .setLastName("userLastName").build();
    User user2 = UserBuilder.aUser().setEmail("admin").setPassword("admin")
        .setRole(User.RoleConstants.ADMIN.getRoleConstant()).setFirstName("adminFirstName")
        .setLastName("adminLastName").build();

    // act
    userService.save(user1);
    userService.save(user2);
    // assert
    verify(userRepositoryMock, times(2)).save(any(User.class));
  }

  @Test
  public void shouldThrowExceptionWhenUserNotFound() {
    // arrange
    thrown.expect(UsernameNotFoundException.class);
    thrown.expectMessage("User not found with the given username: user@example.com");

    when(userRepositoryMock.findOneByEmail("user@example.com")).thenReturn(null);
    // act
    userService.loadUserByUsername("user@example.com");
  }

  @Test
  public void shouldReturnUserDetails() {
    // arrange
    User demoUser = UserBuilder.aUser().setEmail("user@example.com").setPassword("demo")
        .setRole(User.RoleConstants.USER.getRoleConstant()).build();
    when(userRepositoryMock.findOneByEmail("user@example.com")).thenReturn(demoUser);
    // act
    UserDetails userDetails = userService.loadUserByUsername("user@example.com");

    // assert
    assertThat(demoUser.getEmail()).isEqualTo(userDetails.getUsername());
    assertThat(demoUser.getPassword()).isEqualTo(userDetails.getPassword());
    assertThat(hasAuthority(userDetails, demoUser.getRole())).isTrue();
  }

  private boolean hasAuthority(UserDetails userDetails, String role) {
    return userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(isEqual(role));
  }
}
