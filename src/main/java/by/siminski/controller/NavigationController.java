package by.siminski.controller;

import by.siminski.model.catalog.CatalogItem;
import by.siminski.model.request.OrderRequest;
import by.siminski.services.CatalogItemService;
import by.siminski.services.DocumentParseService;
import by.siminski.services.OrderRequestService;
import by.siminski.services.security.SecurityService;
import by.siminski.validator.OrderRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

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

    @Autowired
    DocumentParseService documentParseService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CatalogItemService catalogItemService;

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "welcome";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "settings";
    }

    @GetMapping("/requests")
    public String requests(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("allRequests", orderRequestService.getAllRequests());
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        String headers = messageSource.getMessage("Table.requests.header.id", null, LocaleContextHolder.getLocale()) + "," +
                         messageSource.getMessage("Table.requests.header.username", null, LocaleContextHolder.getLocale()) + "," +
                         messageSource.getMessage("Table.requests.header.organization", null, LocaleContextHolder.getLocale()) + "," +
                         messageSource.getMessage("Table.requests.header.phone", null, LocaleContextHolder.getLocale()) + "," +
                         messageSource.getMessage("Table.requests.header.email", null, LocaleContextHolder.getLocale()) + "," +
                         messageSource.getMessage("Table.requests.header.description", null, LocaleContextHolder.getLocale()) + "," +
                         messageSource.getMessage("Table.requests.header.status", null, LocaleContextHolder.getLocale());
        model.addAttribute("headerList", headers);
        return "requests";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "contacts";
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);

        Map<String, List<CatalogItem>> catalogItemsMap = catalogItemService.getAllCatalogItemsMap();
        model.addAttribute("catalogItemsMap", catalogItemsMap);
        return "catalog";
    }

    @GetMapping("/make-request")
    public String makeRequest(Model model) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("requestForm", new OrderRequest());
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);
        return "make-request";
    }

    @PostMapping("/make-request")
    public String makeRequest(@ModelAttribute("requestForm") OrderRequest requestForm, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        String username = securityService.findLoggedInUsername();
        model.addAttribute("isAnon", SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        model.addAttribute("userName", username);

        orderRequestValidator.validate(requestForm, bindingResult);
        if(bindingResult.hasErrors()) {
            return "make-request";
        }

        orderRequestService.registerRequest(requestForm);
        redirectAttributes.addFlashAttribute("result", messageSource.getMessage("Successful.request.message", null, LocaleContextHolder.getLocale()));
        return "redirect:/make-request";
    }
}
