package com.vodafone.controller;

import com.vodafone.model.Product;
import com.vodafone.model.dto.CreateProduct;
import com.vodafone.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

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

    @PostMapping("create.htm")
    public String save(@Valid @ModelAttribute("product") CreateProduct product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> model = bindingResult.getModel();
            return "createProduct";
        }

        Product newProduct = new Product();
        newProduct.setDescription(product.getDescription());
        newProduct.setCategory(product.getCategory());
        newProduct.setImage(product.getImage());
        newProduct.setPrice(product.getPrice());
        this.productService.create(newProduct);
        return "redirect:/product/show.htm";
    }

    @GetMapping("show.htm")
    public ModelAndView show() {
        ModelAndView model = new ModelAndView("productsTable");
        model.addObject("products", this.productService.getAll());
        return model;
    }
}
