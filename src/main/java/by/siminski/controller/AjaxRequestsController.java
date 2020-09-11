package by.siminski.controller;

import by.siminski.model.catalog.CatalogItem;
import by.siminski.model.request.OrderRequest;
import by.siminski.services.CatalogItemService;
import by.siminski.services.DocumentParseService;
import by.siminski.services.OrderRequestService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AjaxRequestsController {

    @Autowired
    OrderRequestService orderRequestService;

    @Autowired
    DocumentParseService documentParseService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CatalogItemService catalogItemService;

    @GetMapping("/close-request")
    public @ResponseBody String closeRequest(@RequestParam(name = "id") BigInteger requestIdToClose) {
        orderRequestService.closeRequest(requestIdToClose);
        JSONArray jsonArray = new JSONArray();
        for (OrderRequest request : orderRequestService.getAllRequests()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(messageSource.getMessage("Table.requests.header.id", null,
                    LocaleContextHolder.getLocale()), request.getId().toString());
            jsonObject.put(messageSource.getMessage("Table.requests.header.username", null,
                    LocaleContextHolder.getLocale()), request.getUsername());
            jsonObject.put(messageSource.getMessage("Table.requests.header.organization", null,
                    LocaleContextHolder.getLocale()), request.getOrganization());
            jsonObject.put(messageSource.getMessage("Table.requests.header.phone", null,
                    LocaleContextHolder.getLocale()), request.getPhone());
            jsonObject.put(messageSource.getMessage("Table.requests.header.email", null,
                    LocaleContextHolder.getLocale()), request.getEmail());
            jsonObject.put(messageSource.getMessage("Table.requests.header.description", null,
                    LocaleContextHolder.getLocale()), request.getDescription());
            jsonObject.put(messageSource.getMessage("Table.requests.header.status", null,
                    LocaleContextHolder.getLocale()), request.getStatus().name());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @GetMapping("/catalog-search")
    public @ResponseBody String closeRequest(@RequestParam(name = "text") String text) {
        if(StringUtils.isEmpty(text)) {
            return new JSONArray().toString();
        }
        List<CatalogItem> catalogItems = catalogItemService.getAllCatalogItemsMap().values().stream()
                .flatMap(Collection::stream)
                .filter(catalogItem -> catalogItem.getName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
        JSONArray jsonArray = new JSONArray();
        for (CatalogItem catalogItem : catalogItems) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(messageSource.getMessage("Catalog.search.header.name", null,
                    LocaleContextHolder.getLocale()), catalogItem.getName());
            jsonObject.put(messageSource.getMessage("Catalog.search.header.group", null,
                    LocaleContextHolder.getLocale()), catalogItem.getGroupName());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @PostMapping("/catalog/parse")
    public String parseCatalog(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("parse_catalog_status", documentParseService.parseCatalog());
        return "redirect:/settings";
    }
}
