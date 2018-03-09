package com.spring.utils;

public interface IBuilder<T> {

  T build();

  T generateExample();
}
