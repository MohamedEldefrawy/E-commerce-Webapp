package com.vodafone.controller;

import com.vodafone.model.Product;
import com.vodafone.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping("home.htm")
    public String home(Model model) {
        List<Product> productList = this.productService.getAll();
        model.addAttribute("products", productList);
        return "shared/home";
    }
}
