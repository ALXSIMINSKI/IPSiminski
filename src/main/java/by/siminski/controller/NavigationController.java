package by.siminski.controller;

import by.siminski.model.request.OrderRequest;
import by.siminski.services.OrderRequestService;
import by.siminski.services.security.SecurityService;
import by.siminski.validator.OrderRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NavigationController {

    @Autowired
    private SecurityService securityService;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    OrderRequestService orderRequestService;

    @Autowired
    OrderRequestValidator orderRequestValidator;

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "welcome";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "admin";
    }

    @GetMapping("/my_requests")
    public String requests(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("allRequests", orderRequestService.getAllRequests());
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "my_requests";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "contacts";
    }

    @GetMapping("/offerings")
    public String offerings(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "offerings";
    }

    @GetMapping("/order")
    public String order(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("requestForm", new OrderRequest());
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "order";
    }

    @PostMapping("/order")
    public String order(@ModelAttribute("requestForm") OrderRequest requestForm, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);

        orderRequestValidator.validate(requestForm, bindingResult);
        if(bindingResult.hasErrors()) {
            return "order";
        }

        orderRequestService.registerRequest(requestForm);
        redirectAttributes.addFlashAttribute("result", "Your request has been submitted. I'll call you for conforming. :)");
        return "redirect:/order";
    }
}
