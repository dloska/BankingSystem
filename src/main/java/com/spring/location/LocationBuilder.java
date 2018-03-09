package com.spring.location;

import com.github.javafaker.Faker;
import com.spring.storage.Storage;
import java.math.BigDecimal;
import java.util.Random;

public final class LocationBuilder implements ILocationBuilder {

  private String city;
  private String street;
  private String country;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private Storage storage;

  private LocationBuilder() {
  }

  public static LocationBuilder aLocation() {
    return new LocationBuilder();
  }

  public LocationBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public LocationBuilder setStreet(String street) {
    this.street = street;
    return this;
  }

  public LocationBuilder setCountry(String country) {
    this.country = country;
    return this;
  }

  public LocationBuilder setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
    return this;
  }

  public LocationBuilder setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
    return this;
  }

  public LocationBuilder setStorage(Storage storage) {
    this.storage = storage;
    return this;
  }

  public Location build() {
    Location location = new Location();
    location.setCity(city);
    location.setStreet(street);
    location.setCountry(country);
    location.setLatitude(latitude);
    location.setLongitude(longitude);
    location.setStorage(storage);
    return location;
  }

  @Override
  public Location generateExample() {
    Faker faker = new Faker();
    Random r = new Random();

    Location location = LocationBuilder.aLocation()
        .setCity(faker.address().city())
        .setCountry(faker.address().country())
        .setStreet(faker.address().streetAddress())
        .setLatitude(BigDecimal.valueOf(-60.0f + r.nextFloat() * (60.0f - (-60.0f))))
        .setLongitude(BigDecimal.valueOf(-180.0f + r.nextFloat() * (180.0f - (-180.0f))))
        .build();

    return location;
  }
}
