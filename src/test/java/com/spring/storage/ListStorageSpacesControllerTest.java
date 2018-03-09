package com.spring.storage;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.config.WebSecurityConfigurationAware;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ListStorageSpacesControllerTest extends WebSecurityConfigurationAware {

  @Value("${test.config.database.maxStoragesToGenerate}")
  private int maxStoragesToGenerate;

  @Autowired
  private StorageService storageService;

  @Test
  public void listStorages() throws Exception {
    List<Storage> returnedLocations = storageService.getAllStorages();
    log.debug(String.format("All storages: %s", Arrays.toString(returnedLocations.toArray())));
    assertThat(returnedLocations.size()).isGreaterThanOrEqualTo(maxStoragesToGenerate);
  }


}