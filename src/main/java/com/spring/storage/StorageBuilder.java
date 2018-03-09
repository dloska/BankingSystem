package com.spring.storage;

import com.github.javafaker.Faker;
import com.spring.user.User;
import com.spring.location.Location;
import java.math.BigDecimal;
import java.util.Random;

public final class StorageBuilder implements IStorageBuilder {

  private Location location;
  private BigDecimal freeSpace;
  private BigDecimal overallSpace;
  private String description;
  private BigDecimal price;
  private User user;

  private StorageBuilder() {
  }

  public static StorageBuilder aStorage() {
    return new StorageBuilder();
  }

  public StorageBuilder setLocation(Location location) {
    this.location = location;
    return this;
  }

  public StorageBuilder setFreeSpace(BigDecimal freeSpace) {
    this.freeSpace = freeSpace;
    return this;
  }

  public StorageBuilder setOverallSpace(BigDecimal overallSpace) {
    this.overallSpace = overallSpace;
    return this;
  }

  public StorageBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public StorageBuilder setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public StorageBuilder setUser(User user) {
    this.user = user;
    return this;
  }

  public Storage build() {
    Storage storage = new Storage();
    storage.setLocation(location);
    storage.setFreeSpace(freeSpace);
    storage.setOverallSpace(overallSpace);
    storage.setDescription(description);
    storage.setPrice(price);
    storage.setUser(user);
    return storage;
  }

  @Override
  public Storage generateExample() {

    Faker faker = new Faker();
    Random r = new Random();

    Float min = 10.0f, max = 100.0f;
    Float overallSpace = min + r.nextFloat() * (max - min);
    Float freeSpace = r.nextFloat() * (overallSpace);
    Float price = 1.0f + r.nextFloat() * (1000.0f - 1.0f);

    Storage storage = StorageBuilder.aStorage()
        .setDescription(faker.lorem().sentence(15))
        .setFreeSpace(BigDecimal.valueOf(freeSpace))
        .setOverallSpace(BigDecimal.valueOf(overallSpace))
        .setPrice(BigDecimal.valueOf(price))
        .build();

    return storage;
  }
}
