package com.spring.user;

import java.util.Collections;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

  @Getter
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional
  public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    return savedUser;
  }

  public User getCurrentUser() {
    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    Assert.notNull(currentUserEmail);
    User currentUser = findOneByEmail(currentUserEmail);
    return currentUser;
  }

  public boolean checkUserIfExists(User userToUpdate) {
    String email = getCurrentUser().getEmail();
    return !org.thymeleaf.util.StringUtils.equalsIgnoreCase(email, userToUpdate.getEmail())
        && userRepository.findOneByEmail(userToUpdate.getEmail()) != null;
  }

  @Transactional
  public User findOneByEmail(@NotNull String email) {
    return userRepository.findOneByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findOneByEmail(username);
    if (user == null) {
      String s = new StringBuilder().append("User not found with the given username: ")
          .append(username).toString();
      log.debug(s);
      throw new UsernameNotFoundException(s);
    }
    return createUser(user);
  }

  public void signin(User user) {
    SecurityContextHolder.getContext().setAuthentication(authenticate(user));
  }

  private Authentication authenticate(User user) {
    return new UsernamePasswordAuthenticationToken(createUser(user), null,
        Collections.singleton(createAuthority(user)));
  }

  private org.springframework.security.core.userdetails.User createUser(User user) {
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(),
        Collections.singleton(createAuthority(user)));
  }

  private GrantedAuthority createAuthority(User user) {
    return new SimpleGrantedAuthority(user.getRole());
  }

}
