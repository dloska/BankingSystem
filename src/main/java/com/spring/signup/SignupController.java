package com.spring.signup;

import com.spring.user.User;
import com.spring.user.UserService;
import com.spring.support.web.Ajax;
import com.spring.support.web.MessageHelper;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
class SignupController {

  private static final String SIGNUP_VIEW_NAME = "signup/signup";

  @Autowired
  private UserService userService;

  @GetMapping("signup")
  String signup(Model model,
      @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
    model.addAttribute(new SignupForm());
    if (Ajax.isAjaxRequest(requestedWith)) {
      return SIGNUP_VIEW_NAME.concat(" :: signupForm");
    }
    return SIGNUP_VIEW_NAME;
  }

  @PostMapping("signup")
  @Transactional
  String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors,
      RedirectAttributes ra) {
    if (errors.hasErrors()) {
      return SIGNUP_VIEW_NAME;
    }
    User user = userService.save(signupForm.createUser());
    userService.signin(user);
    // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
    MessageHelper.addSuccessAttribute(ra, "signup.success");
    return "redirect:/";
  }
}
