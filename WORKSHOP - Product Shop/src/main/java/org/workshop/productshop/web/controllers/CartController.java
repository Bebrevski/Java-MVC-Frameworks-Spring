package org.workshop.productshop.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.workshop.productshop.domain.models.view.ProductDetailsViewModel;
import org.workshop.productshop.domain.models.view.ShoppingCartItem;
import org.workshop.productshop.service.ProductService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(String id, int quantity, HttpSession session) {
        this.initCart(session);

        ProductDetailsViewModel product = this.modelMapper
                .map(this.productService.findProductById(id), ProductDetailsViewModel.class);

        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        this.addItemToCart(cartItem, session);
        return super.redirect("/home");
    }

    @GetMapping("/details")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession session) {
        this.initCart(session);
        modelAndView.addObject("totalPrice", this.calcTotal(((List<ShoppingCartItem>) session.getAttribute("shopping-cart"))));

        return super.view("cart/cart-details");
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private void addItemToCart(ShoppingCartItem item, HttpSession session) {
        for (ShoppingCartItem shoppingCartItem : ((List<ShoppingCartItem>) session.getAttribute("shopping-cart"))) {
            if (shoppingCartItem.getProduct().getId().equals(item.getProduct().getId())) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        ((List<ShoppingCartItem>) session.getAttribute("shopping-cart")).add(item);
    }

    private BigDecimal calcTotal(List<ShoppingCartItem> cart) {
        BigDecimal result = new BigDecimal(0);
        for (ShoppingCartItem item : cart) {
            result = result.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return result;
    }
}
