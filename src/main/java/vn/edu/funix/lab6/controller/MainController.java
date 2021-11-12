package vn.edu.funix.lab6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vn.edu.funix.lab6.entity.HintQuestion;
import vn.edu.funix.lab6.model.FirstLoginPageRequestModel;
import vn.edu.funix.lab6.service.HintQuestionService;
import vn.edu.funix.lab6.service.UserService;
import vn.edu.funix.lab6.utils.AppUtils;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	@Autowired
	private HintQuestionService hintQuestionService;

	private static final String PAGE_403 = "403Page";

	@GetMapping(value = { "/", "/welcome", "login" })
	public String welcomePage(Model model, Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			Optional<vn.edu.funix.lab6.entity.User> user = userService.findByUserName(username);
			if (user.isPresent() && user.get().isFirstLogin()) {
				return "/firstEntryPage";
			}
			model.addAttribute("title", "Welcome");
			model.addAttribute("message", "This is welcome page!");
			return "homePage";
		} else return "loginPage";
	}

	@GetMapping(value = {"/firstEntryPage"})
	public String firstEntryPage(Model model, Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			Optional<vn.edu.funix.lab6.entity.User> user = userService.findByUserName(username);
			if (user.isPresent()) {
				user.get().setFailureLoginTemp(0);
				userService.saveUser(user.get());

				if (user.get().isFirstLogin()) {
					Map<Long, HintQuestion> hintQuestions = hintQuestionService.findAllHintQuestion()
							.stream().collect(Collectors.toMap(HintQuestion::getQuestionId, Function.identity()));
					FirstLoginPageRequestModel loginPageRequestModel = new FirstLoginPageRequestModel();
					model.addAttribute("requestForm", loginPageRequestModel);
					model.addAttribute("hintQuestions", hintQuestions);
					return "firstEntryPage";
				} else return "redirect:/welcome";
			}
		}
		return PAGE_403;
	}

	@PostMapping(value = {"/submitFirstLogin"})
	public String submitFirstLogin(Principal principal, FirstLoginPageRequestModel requestModel) {
		if (principal != null) {
			String username = principal.getName();
			Optional<vn.edu.funix.lab6.entity.User> user = userService.findByUserName(username);
			if (user.isPresent()) {
				try {
				// update firstLogin
				userService.saveNewPassword(username, requestModel.getOldPassword(), requestModel.getNewPassword(), requestModel.getConfirmPassword());
				// update answer
				hintQuestionService.saveHintQuestionChoice(requestModel, user.get());
				return "redirect:/logout";
				} catch (Exception e) {
					return "firstEntryPage";
				}
			}
		}
		return PAGE_403;
	}

	@GetMapping(value = "/403")
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginUser = (User) ((Authentication) principal).getPrincipal();
			String userInfo = AppUtils.toString(loginUser);
			model.addAttribute("userInfo", userInfo);
			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);
		}
		return PAGE_403;
	}

}