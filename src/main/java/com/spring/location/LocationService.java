package com.spring.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

  @Autowired
  private LocationRepository locationRepository;


}
