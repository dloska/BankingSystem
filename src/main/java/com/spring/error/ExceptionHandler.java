package com.spring.error;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * General error handler for the application.
 */
@Slf4j
@ControllerAdvice
class ExceptionHandler {

  /**
   * Handle exceptions thrown by handlers.
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
  public ModelAndView exception(Exception exception, WebRequest request) {
    ModelAndView modelAndView = new ModelAndView("error/general");
    modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
    log.error("Exception occured",exception);
    return modelAndView;
  }
}