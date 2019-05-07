package org.workshop.productshop.validation;

import org.workshop.productshop.domain.models.service.UserServiceModel;

public interface UserValidationService {
    boolean isValid(UserServiceModel user);
}
