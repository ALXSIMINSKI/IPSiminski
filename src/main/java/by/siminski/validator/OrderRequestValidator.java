package by.siminski.validator;

import by.siminski.model.request.OrderRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organization", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Required");
        if (orderRequest.getUsername().length() < 2 || orderRequest.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.requestForm.username");
        }
        if (orderRequest.getOrganization().length() < 2 || orderRequest.getOrganization().length() > 64) {
            errors.rejectValue("organization", "Size.requestForm.organization");
        }
        if (orderRequest.getPhone().length() < 7 || orderRequest.getPhone().length() > 20) {
            errors.rejectValue("phone", "Size.requestForm.phone");
        }
        if (orderRequest.getDescription().length() < 10 || orderRequest.getDescription().length() > 500) {
            errors.rejectValue("description", "Size.requestForm.description");
        }
    }
}
