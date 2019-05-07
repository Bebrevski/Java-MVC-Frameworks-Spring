package org.workshop.productshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.workshop.productshop.domain.entities.Order;
import org.workshop.productshop.domain.models.service.OrderServiceModel;
import org.workshop.productshop.mappings.MappingsInitializer;

@Configuration
public class ApplicationBeanConfiguration {

    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        MappingsInitializer.initMappings(mapper);
    }

    @Bean
    public ModelMapper modelMapper(){
        return mapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
