package com.spring.config;

import com.spring.user.User;
import com.spring.user.UserRepository;
import com.spring.location.LocationRepository;
import com.spring.storage.StorageRepository;
import com.spring.utils.UserGenerator;
import java.util.List;
import javax.inject.Inject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringInstanceTestClassRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {
    ApplicationConfigTest.class
})
public abstract class WebAppConfigurationAware implements InstanceTestClassListener {

  @Value("${test.config.database.howManyUsersShouldBeGenerated}")
  private int howManyUsersShouldBeGenerated;

  @Value("${test.config.database.minStoragesToGenerate}")
  private int minStoragesToGenerate;

  @Value("${test.config.database.maxStoragesToGenerate}")
  private int maxStoragesToGenerate;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private StorageRepository storageRepository;
  @Autowired
  private LocationRepository locationsRepository;

  @Inject
  protected WebApplicationContext wac;

  protected MockMvc mockMvc;

  @Override
  public void beforeClassSetup() {
    UserGenerator userGenerator = UserGenerator.builder()
        .howManyUsersShouldBeGenerated(howManyUsersShouldBeGenerated)
        .maxStoragesToGenerate(maxStoragesToGenerate)
        .minStoragesToGenerate(minStoragesToGenerate)
        .build();
    List<User> users = userGenerator.generateMultipleEntities();
    userRepository.save(users);
  }

  @Override
  public void afterClassSetup() {
    storageRepository.deleteAll();
    userRepository.deleteAll();
    locationsRepository.deleteAll();
  }
}
