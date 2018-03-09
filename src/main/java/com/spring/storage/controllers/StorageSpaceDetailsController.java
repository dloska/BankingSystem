package com.spring.storage.controllers;

import com.spring.storage.StorageForm;
import com.spring.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StorageSpaceDetailsController {

  @Autowired
  private StorageService storageService;

  @GetMapping("storage/all/details/{id}")
  public String storageDetails(@PathVariable("id") Long id, Model model) {
    model.addAttribute(new StorageForm(storageService.getStorageById(id)));
    return "storage/all/details";
  }

}
