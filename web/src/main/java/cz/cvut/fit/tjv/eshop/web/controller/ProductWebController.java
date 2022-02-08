package cz.cvut.fit.tjv.eshop.web.controller;

import cz.cvut.fit.tjv.eshop.web.client.ProductClient;
import cz.cvut.fit.tjv.eshop.web.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductWebController {

    private final ProductClient productClient;

    public ProductWebController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productClient.fetchAllProducts());
        return "products";
    }

    @GetMapping("/products/add")
    public String addProductGet(Model model) {
        model.addAttribute("productModel", new ProductModel());
        return "addProduct";
    }

    @PostMapping("/products/add")
    public String addProductSubmit(Model model, @ModelAttribute ProductModel productModel) {
        model.addAttribute("productModel", productClient.create(productModel));
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductGet(Model model, @PathVariable long id) {
        model.addAttribute("productModel", productClient.fetchProduct(id));
        return "editProduct";
    }

    @PostMapping("/products/edit/{id}")
    public String editProductSubmit(Model model, @PathVariable long id, @ModelAttribute ProductModel productModel) {
        model.addAttribute("productModel", productClient.update(productModel, id));
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(Model model, @PathVariable long id) {
        model.addAttribute(productClient.delete(id));
        return "redirect:/products";
    }
}
