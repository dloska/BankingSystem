package com.spring.storage;

import com.spring.user.User;
import com.spring.location.Location;
import com.spring.utils.IBuilder;
import java.math.BigDecimal;

public interface IStorageBuilder extends IBuilder<Storage> {

  IStorageBuilder setLocation(Location location);

  IStorageBuilder setUser(User user);

  IStorageBuilder setFreeSpace(BigDecimal freeSpace);

  IStorageBuilder setOverallSpace(BigDecimal overallSpace);

  IStorageBuilder setDescription(String description);

  IStorageBuilder setPrice(BigDecimal price);
}
