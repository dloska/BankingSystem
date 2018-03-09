package com.spring.storage.controllers;

import com.spring.storage.StorageService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StorageSpaceListController {

  @Autowired
  private StorageService storageService;

  @GetMapping("storage/all/list")
  public String listStorages(Model model,
      @RequestParam(value = "sortField", required = false) String sortField,
      @RequestParam(value = "order", required = false) String order) {
    model.addAttribute("module", "listAllStorageSpaces");
    model.addAttribute("storages", storageService.getSortedStorages(sortField, order));

    return "storage/all/list";
  }

  @GetMapping("storage/user/list")
  public String listUserStorages(Model model, Principal principal,
      @RequestParam(value = "sortField", required = false) String sortField,
      @RequestParam(value = "order", required = false) String order) {
    Assert.notNull(principal);
    model.addAttribute("module", "listUserStorageSpaces");
    model.addAttribute("storages", storageService.getSortedStorages(sortField, order));
    return "storage/user/list";
  }
}
