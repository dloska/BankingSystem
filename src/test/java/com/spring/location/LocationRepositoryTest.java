package com.spring.location;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.config.WebAppConfigurationAware;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class LocationRepositoryTest extends WebAppConfigurationAware {

  @Value("${test.config.database.maxStoragesToGenerate}")
  private int maxStoragesToGenerate;

  @Autowired
  private LocationRepository locationsRepository;

  @Test
  public void shouldReturnLocations() {
    List<Location> returnedLocations = locationsRepository.findAll();
    log.debug(String.format("All locations: %s", Arrays.toString(returnedLocations.toArray())));
    assertThat(returnedLocations.size()).isGreaterThanOrEqualTo(maxStoragesToGenerate);
  }


}