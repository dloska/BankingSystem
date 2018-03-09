package com.spring.profile;

import com.spring.user.User;
import com.spring.user.UserService;
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
public class ProfileController {

  @ModelAttribute("module")
  String module() {
    return "profile";
  }

  public static final String PROFILE_VIEW_NAME = "profile/profile";

  @Autowired
  private UserService userService;

  @GetMapping("profile")
  String showProfile(Model model, Principal principal) {
    Assert.notNull(principal);
    model.addAttribute(new ProfileForm(userService.findOneByEmail(principal.getName())));
    return PROFILE_VIEW_NAME;
  }

  @PostMapping("profile")
  @Transactional
  String updateProfile(@Valid @ModelAttribute ProfileForm profileForm, Errors errors,
      RedirectAttributes ra) {
    if (errors.hasErrors()) {
      return PROFILE_VIEW_NAME;
    }
    User updatedUser = profileForm.updateUserFields(userService.getCurrentUser());
    User savedUser = userService.save(updatedUser);
    userService.signin(savedUser);
    MessageHelper.addSuccessAttribute(ra, "profile.success");
    return "redirect:/profile";
  }
}
