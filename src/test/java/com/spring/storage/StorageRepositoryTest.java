package com.spring.storage;

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
public class StorageRepositoryTest extends WebAppConfigurationAware {


  @Value("${test.config.database.maxStoragesToGenerate}")
  private int maxStoragesToGenerate;

  @Autowired
  private StorageRepository storageRepository;

  @Test
  public void shouldReturnStorages() {
    List<Storage> returnedStorages = storageRepository.findAll();
    log.debug(String.format("All storages: %s", Arrays.toString(returnedStorages.toArray())));
    assertThat(returnedStorages.size()).isGreaterThanOrEqualTo(maxStoragesToGenerate);
  }

}