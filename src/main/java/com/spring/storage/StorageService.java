package com.spring.storage;

import com.spring.user.User;
import com.spring.user.UserService;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageService {

  @Autowired
  private StorageRepository storageRepository;

  @Autowired
  private UserService userService;

  private void connectStorageToCurrentUserAndSave(Storage storage) {
    User currentUser = userService.getCurrentUser();
    storage.setUser(currentUser);
    currentUser.addStorage(storage);
    storageRepository.save(storage);
  }

  @Transactional
  public Storage saveStorageSpace(StorageForm storageForm) {
    Storage storage = storageForm.createStorage();
    connectStorageToCurrentUserAndSave(storage);
    return storage;
  }

  @Transactional
  public List<Storage> getAllStorages() {
    List<Storage> storagesList;
    storagesList = storageRepository.findAll();
    return storagesList;
  }


  @Transactional
  public List<Storage> getSortedStorages(String fieldName, String order) {
    if (fieldName == null || order == null) {
      return getAllStorages();
    }
    Direction direction = order.equals("asc") ? Direction.ASC : Direction.DESC;
    List<Storage> storagesList;
    storagesList = storageRepository.findAll(new Sort(new Order(direction, fieldName)));
    return storagesList;
  }

  @Transactional
  public void deleteStorageByID(Long id) {
    storageRepository.delete(id);
  }

  @Transactional
  public List<Storage> getAllUserStorages() {
    List<Storage> storagesList;
    User currentUser = userService.getCurrentUser();
    Hibernate.initialize(currentUser.getStorages());
    storagesList = currentUser.getStorages();
    return storagesList;
  }

  @Transactional
  public Storage getStorageById(Long id) {
    Storage storage;
    storage = storageRepository.findOne(id);
    return storage;
  }
}
