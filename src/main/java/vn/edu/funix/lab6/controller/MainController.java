package vn.edu.funix.lab6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.funix.lab6.entity.HintQuestion;
import vn.edu.funix.lab6.model.FirstLoginPageRequestModel;
import vn.edu.funix.lab6.service.HintQuestionService;
import vn.edu.funix.lab6.service.UserService;
import vn.edu.funix.lab6.utils.AppUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	@Autowired
	private HintQuestionService hintQuestionService;

	private static final String PAGE_403 = "403Page";

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
			String username = principal.getName();
			Optional<vn.edu.funix.lab6.entity.User> user = userService.findByUserName(username);
			if (user.isPresent()) {
				user.get().setFailureLoginTemp(0);
				userService.saveUser(user.get());

				if (user.get().isFirstLogin()) {
					List<HintQuestion> hintQuestions = hintQuestionService.findAllHintQuestion();
					model.addAllAttributes(hintQuestions);
					return "firstEntryPage";
				} else return "redirect:/welcome";
			}
		}
		return PAGE_403;
	}

	@RequestMapping(value = {"/submitFirstLogin"}, method = RequestMethod.PUT)
	public String submitFirstLogin(Model model, Principal principal, FirstLoginPageRequestModel requestModel) {
		if (principal != null) {
			String username = principal.getName();
			Optional<vn.edu.funix.lab6.entity.User> user = userService.findByUserName(username);
			if (user.isPresent()) {
				try {
				// update firstLogin
				userService.saveNewPassword(username, requestModel.getOldPassword(), requestModel.getNewPassword(), requestModel.getConfirmPassword());
				// update answer
				hintQuestionService.saveHintQuestionChoice(requestModel, user.get());
				return "redirect:/logoutSuccessful";
				} catch (Exception e) {
					return "firstEntryPage";
				}
			}
		}
		return PAGE_403;
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

		return PAGE_403;
	}

}