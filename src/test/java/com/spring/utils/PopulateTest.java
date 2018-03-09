package com.spring.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.user.User;
import com.spring.user.UserRepository;
import com.spring.config.WebAppConfigurationAware;
import com.spring.location.Location;
import com.spring.location.LocationRepository;
import com.spring.storage.Storage;
import com.spring.storage.StorageRepository;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class PopulateTest extends WebAppConfigurationAware {

  @Value("${test.config.database.howManyUsersShouldBeGenerated}")
  private int howManyUsersShouldBeGenerated;

  @Value("${test.config.database.minStoragesToGenerate}")
  private int minStoragesToGenerate;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private StorageRepository storageRepository;
  @Autowired
  private LocationRepository locationsRepository;


  @Test
  public void shouldGetAllEntities() {
    List<User> foundUsers = userRepository.findAll();
    List<Location> foundLocations = locationsRepository.findAll();
    List<Storage> foundStorages = storageRepository.findAll();
    log.debug(String.format("All users : %s", Arrays.toString(foundUsers.toArray())));
    log.debug(String.format("All locations: %s", Arrays.toString(foundLocations.toArray())));
    log.debug(String.format("All storages: %s", Arrays.toString(foundStorages.toArray())));
    assertThat(foundUsers.size()).isGreaterThanOrEqualTo(howManyUsersShouldBeGenerated);
    assertThat(foundLocations.size()).isGreaterThanOrEqualTo(howManyUsersShouldBeGenerated);
    assertThat(foundStorages.size()).isGreaterThanOrEqualTo(howManyUsersShouldBeGenerated);

  }

  @Test
  public void everyUserShouldContainAtLeastThreeStorages() {
    List<User> foundUsers = userRepository.findAll();
    assertThat(foundUsers)
        .allMatch(user -> user.getStorages().size() >= minStoragesToGenerate);
  }

  @Test
  public void shouldGetLocationByStorage() {
    List<Storage> foundStorages = storageRepository.findAll();
    log.debug(String.format("All storages: %s", Arrays.toString(foundStorages.toArray())));

    assertThat(foundStorages.size()).isGreaterThanOrEqualTo(howManyUsersShouldBeGenerated);
    assertThat(foundStorages).allMatch(
        storage -> storage.getLocation() != null && storage.getLocation() instanceof Location);
  }

  @Test
  public void shouldGetStorageByLocation() {
    List<Location> foundLocations = locationsRepository.findAll();
    log.debug(String.format("All locations: %s", Arrays.toString(foundLocations.toArray())));

    assertThat(foundLocations.size()).isGreaterThanOrEqualTo(howManyUsersShouldBeGenerated);
    assertThat(foundLocations).allMatch(
        location -> location.getStorage() != null && location.getStorage() instanceof Storage);
  }

  @Test
  public void shouldGetUserByStorage() {
    List<Storage> foundStorages = storageRepository.findAll();
    log.debug(String.format("All storages: %s", Arrays.toString(foundStorages.toArray())));

    assertThat(foundStorages.size()).isGreaterThanOrEqualTo(howManyUsersShouldBeGenerated);
    assertThat(foundStorages).allMatch(
        storage -> storage.getUser() != null && storage.getUser() instanceof User);
  }


}
