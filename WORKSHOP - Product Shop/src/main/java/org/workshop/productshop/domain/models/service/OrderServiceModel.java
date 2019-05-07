package org.workshop.productshop.domain.models.service;

import java.math.BigDecimal;

public class OrderServiceModel {
    private String imageUrl;
    private String name;
    private BigDecimal price;

    public OrderServiceModel() {
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
