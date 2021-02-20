package io.pivotal.microservices.services.web;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Home page controller.
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "redirect:login";
	}

	@RequestMapping("/home")
	public String hhome() {
		return "index";
	}



//	@RequestMapping("/securedPage")
//	public String securedPage(Model model,
//							  @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
//							  @AuthenticationPrincipal OAuth2User oauth2User) {
//		model.addAttribute("userName", oauth2User.getName());
//		model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
//		model.addAttribute("userAttributes", oauth2User.getAttributes());
//		return "securedPage";
//	}

}
