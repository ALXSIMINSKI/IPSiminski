package by.siminski.validator;

import by.siminski.model.request.OrderRequest;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OrderRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderRequest orderRequest = (OrderRequest) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "Required");
        if (orderRequest.getUsername().length() < 2 || orderRequest.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.requestForm.username");
        }
        if (!StringUtils.isEmpty(orderRequest.getOrganization()) && orderRequest.getOrganization().length() < 2 || orderRequest.getOrganization().length() > 64) {
            errors.rejectValue("organization", "Size.requestForm.organization");
        }
        if (orderRequest.getPhone().length() < 7 || orderRequest.getPhone().length() > 20) {
            errors.rejectValue("phone", "Size.requestForm.phone");
        }
        if (!StringUtils.isEmpty(orderRequest.getDescription()) && orderRequest.getDescription().length() < 10 || orderRequest.getDescription().length() > 500) {
            errors.rejectValue("description", "Size.requestForm.description");
        }
        if (!StringUtils.isEmpty(orderRequest.getEmail()) && orderRequest.getEmail().length() < 6) {
            errors.rejectValue("email", "Size.requestForm.email");
        }
        if (!StringUtils.isEmpty(orderRequest.getEmail()) && !EmailValidator.getInstance().isValid(orderRequest.getEmail())) {
            errors.rejectValue("email", "Size.requestForm.email.match");
        }
    }
}
