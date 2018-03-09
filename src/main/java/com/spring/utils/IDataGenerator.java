package com.spring.utils;

import java.util.List;

public interface IDataGenerator<T> {

  T generateSingleEntity();

  List<T> generateMultipleEntities();
}
