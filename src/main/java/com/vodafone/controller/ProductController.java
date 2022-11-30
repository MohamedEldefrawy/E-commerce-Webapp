package com.vodafone.controller;

import com.vodafone.model.dto.CreateProduct;
import com.vodafone.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("create.htm")
    public String create(Model model) {
        model.addAttribute("product", new CreateProduct());
        return "createProduct";
    }

    @GetMapping("show.htm")
    public ModelAndView show() {
        ModelAndView model = new ModelAndView("productsTable");
        model.addObject("products", this.productService.getAll());
        return model;
    }
}
