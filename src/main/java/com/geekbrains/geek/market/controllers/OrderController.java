package com.geekbrains.geek.market.controllers;

import com.geekbrains.geek.market.entities.Order;
import com.geekbrains.geek.market.entities.OrderItem;
import com.geekbrains.geek.market.services.OrderService;
import com.geekbrains.geek.market.utils.Cart;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    @Autowired
    private Cart cart;

    @GetMapping
    public String firstRequest(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "orders";
    }

    @GetMapping("/add")
    public String addOrder(Model model){
        return "add_order";
    }

    @PostMapping("/save")
    public String saveOrder(@RequestParam (name="customer_name") String customerName, @RequestParam (name="customer_name") String customerPhone,
                            @RequestParam (name="customer_address") String customerAddress){
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerPhone(customerPhone);
        order.setCustomerAddress(customerAddress);
        for(OrderItem orderItem : cart.getItems()) orderItem.setOrder(order);
        order.setItems(cart.getItems());
        order.setPrice(cart.getPrice());
        orderService.saveOrder(order);
        cart.clearCart();
        return "redirect:/orders";
    }
}
