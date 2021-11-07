package vn.edu.funix.lab6.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.funix.lab6.utils.AppUtils;

import java.security.Principal;

@Controller
public class MainController {

	@RequestMapping(value = { "/", "/welcome", "login" }, method = RequestMethod.GET)
	public String welcomePage(Model model, Principal principal) {
		if (principal != null) {
			model.addAttribute("title", "Welcome");
			model.addAttribute("message", "This is welcome page!");
			return "homePage";
		} else return "loginPage";
	}

	@RequestMapping(value = {"/firstEntryPage"}, method = RequestMethod.GET)
	public String firstEntryPage(Model model, Principal principal) {
		if (principal != null) {
			model.addAttribute("title", "Welcome");
			model.addAttribute("message", "This is first entry page!");
			return "firstEntryPage";
		} else return "403Page";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutPage";
	}


	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			String userInfo = AppUtils.toString(loginedUser);

			model.addAttribute("userInfo", userInfo);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

}