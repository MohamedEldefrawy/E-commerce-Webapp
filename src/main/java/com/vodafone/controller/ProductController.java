package com.vodafone.controller;

import com.vodafone.model.Product;
import com.vodafone.model.dto.CreateProduct;
import com.vodafone.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("create.htm")
    public String create(Model model) {
        model.addAttribute("product", new CreateProduct());
        return "products/create";
    }

    @PostMapping("create.htm")
    public String save(@Valid @ModelAttribute("product") CreateProduct product,
                       BindingResult bindingResult,
                       @RequestParam("image") CommonsMultipartFile image,
                       HttpSession session) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> model = bindingResult.getModel();
            return "products/createProduct";
        }
        byte[] imageData = image.getBytes();
        String path = session.getServletContext().getRealPath("/") + "resources/static/images/" + image.getOriginalFilename();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(imageData);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Product newProduct = new Product();
        newProduct.setDescription(product.getDescription());
        newProduct.setCategory(product.getCategory());
        newProduct.setImage(image.getOriginalFilename());
        newProduct.setPrice(product.getPrice());
        this.productService.create(newProduct);
        return "redirect:/product/show.htm";
    }

    @DeleteMapping("show.htm")
    @ResponseBody
    public String delete(@RequestParam(required = false) Long id) {
        boolean result = this.productService.delete(id);
        if (result)
            return "true";
        return "false";
    }

    @GetMapping("show.htm")
    public String show(Model model) {
        model.addAttribute("products", this.productService.getAll());
        model.addAttribute("id", 0L);
        return "products/products";
    }

    @GetMapping("update.htm")
    public String update(Model model, @RequestParam Long id) {
        Product selectedProduct = this.productService.get(id);
        model.addAttribute("product", selectedProduct);
        return "products/update";
    }
}
