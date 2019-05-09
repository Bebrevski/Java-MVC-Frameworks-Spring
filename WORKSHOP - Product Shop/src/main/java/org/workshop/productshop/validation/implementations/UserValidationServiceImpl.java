package org.workshop.productshop.validation.implementations;

import org.springframework.stereotype.Component;
import org.workshop.productshop.domain.models.service.UserServiceModel;
import org.workshop.productshop.validation.UserValidationService;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel user) {
        return user != null;
    }
}
