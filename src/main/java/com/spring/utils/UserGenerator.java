package com.spring.utils;

import com.spring.user.User;
import com.spring.user.UserBuilder;
import com.spring.location.Location;
import com.spring.location.LocationBuilder;
import com.spring.storage.Storage;
import com.spring.storage.StorageBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Builder;

@Builder
public class UserGenerator implements IDataGenerator<User> {

  private int howManyUsersShouldBeGenerated = 1;

  private int minStoragesToGenerate = 1;

  private int maxStoragesToGenerate = 2;


  private void addStoragesWithLocationsToUser(User user) {
    Random rand = new Random();
    int upperBound =
        minStoragesToGenerate + rand.nextInt(maxStoragesToGenerate - minStoragesToGenerate);
    for (int j = 1; j <= upperBound; j++) {
      Storage storage = StorageBuilder.aStorage().generateExample();
      Location location = LocationBuilder.aLocation().generateExample();
      storage.setLocation(location);
      location.setStorage(storage);
      storage.setUser(user);
      user.getStorages().add(storage);
    }
  }

  @Override
  public User generateSingleEntity() {
    return UserBuilder.aUser().generateExample();
  }

  @Override
  public List<User> generateMultipleEntities() {
    List<User> users = new ArrayList<>();
    for (int i = 1; i <= howManyUsersShouldBeGenerated; i++) {
      User user = UserBuilder.aUser().generateExample();
      addStoragesWithLocationsToUser(user);
      users.add(user);
    }
    return users;
  }
}
