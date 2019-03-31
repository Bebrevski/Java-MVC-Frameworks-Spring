package org.workshop.productshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.workshop.productshop.domain.models.service.UserServiceModel;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);
}
