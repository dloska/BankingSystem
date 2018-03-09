package com.spring.storage.controllers;

import com.spring.storage.StorageForm;
import com.spring.storage.StorageService;
import com.spring.support.web.MessageHelper;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StorageSpacePublishController {

  @ModelAttribute("module")
  String module() {
    return "publishStorageSpace";
  }

  @Autowired
  private StorageService storageService;

  @PostMapping("storage/new")
  @Transactional
  String saveStorage(@Valid @ModelAttribute StorageForm storageForm, Errors errors,
      RedirectAttributes ra) {
    if (errors.hasErrors()) {
      return "redirect:/storage/new";
    }
    storageService.saveStorageSpace(storageForm);
    MessageHelper.addSuccessAttribute(ra, "storage.save.success");
    return "redirect:/storage/new";
  }

  @GetMapping("storage/new")
  public String newStorage(Model model, Principal principal) {
    Assert.notNull(principal);
    model.addAttribute(new StorageForm());
    return "storage/new";
  }
}
