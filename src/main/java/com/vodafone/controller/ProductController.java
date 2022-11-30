package com.vodafone.controller;

import com.vodafone.model.dto.CreateProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @GetMapping("create.htm")
    public String create(Model model) {
        model.addAttribute("product", new CreateProduct());
        return "createProduct";
    }
}
