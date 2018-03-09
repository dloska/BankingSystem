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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StorageSpaceEditController {

  @Autowired
  private StorageService storageService;

  @PostMapping("storage/user/edit")
  @Transactional
  String saveStorage(@Valid @ModelAttribute StorageForm storageForm, Errors errors,
      RedirectAttributes ra) {
    if (errors.hasErrors()) {
      return "redirect:/storage/user/edit";
    }
    storageService.saveStorageSpace(storageForm);
    MessageHelper.addSuccessAttribute(ra, "storage.edit.success");
    return "redirect:/storage/user/list";
  }

  @GetMapping("storage/user/edit/{id}")
  public String newStorage(@PathVariable("id") Long id, Model model, Principal principal) {
    Assert.notNull(principal);
    model.addAttribute(new StorageForm(storageService.getStorageById(id)));
    return "storage/user/edit";
  }

  @GetMapping("storage/user/delete/{id}")
  public String deleteStorage(@PathVariable("id") Long id, Principal principal,
      RedirectAttributes ra) {
    Assert.notNull(principal);
    storageService.deleteStorageByID(id);
    MessageHelper.addSuccessAttribute(ra, "storage.delete.success");
    return "redirect:/storage/user/list";
  }
}
