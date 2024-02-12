package com.example.fishop.controller;

import com.example.fishop.dto.ProductDTO;
import com.example.fishop.dto.response.ResponseProductDTO;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.ProductSpecie;
import com.example.fishop.service.ProductService;
import com.example.fishop.service.ProductSpecieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductViewController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductSpecieService specieService;

    @GetMapping("/all")
    public String all(Model model) {
        List<ResponseProductDTO> products = productService.get();
        model.addAttribute("text","");
        model.addAttribute("speciename","");
        model.addAttribute("sliderprice_minimum",0);
        model.addAttribute("sliderprice_value",productService.getMaxPrice());

        model.addAttribute("sliderprice_maximum",productService.getMaxPrice());
        model.addAttribute("products",products);
        model.addAttribute("species",specieService.get());
        return "all";
    }

    @PostMapping(value = "/all/search")
    public String search(
            @Valid @Size(max = 120) @RequestParam(defaultValue = "") String text,
            @Valid @Size(max = 20) @RequestParam(defaultValue = "") String speciename,
            @Valid @Min(0) @RequestParam(defaultValue = "0") int minPrice,
            @Valid @Min(0) @RequestParam int maxPrice, Model model)
    {
        ProductSpecie specie = specieService.getByName(speciename);
        Long specieid = -1L;
        if(specie!= null) specieid = specie.getId();

        model.addAttribute("text",text);
        model.addAttribute("speciename",speciename);
        model.addAttribute("sliderprice_minimum",minPrice);
        model.addAttribute("sliderprice_value",maxPrice);

        model.addAttribute("sliderprice_maximum",productService.getMaxPrice());
        model.addAttribute("products",productService.search(text,specieid,minPrice,maxPrice));
        model.addAttribute("species",specieService.get());
        return "all";
    }


    @GetMapping(value = "/all/{productId}")
    public String getProductDetails(@PathVariable Long productId ,Model model) {
        Product p = productService.get(productId);
        if(p == null) return "redirect:/";
        model.addAttribute("product",new ResponseProductDTO(p));
        return "product";
    }

    @GetMapping(value = "/all/{productId}/edit")
    public String productEdit(@PathVariable Long productId, Model model) {
        model.addAttribute("species",specieService.get());
        model.addAttribute("product",new ResponseProductDTO(productService.get(productId)));
        return "admin/product-edit";
    }

    @PostMapping(value = "/api/all/remove")
    public String remove(Long productId) {
        productService.remove(productId);
        return "redirect:/all";
    }

    @GetMapping(value = "/")
    public String home(Model model)
    {
        model.addAttribute("products",productService.getBest());
        return "home";
    }

    @PostMapping(value="/api/all/editProduct", consumes = {"application/x-www-form-urlencoded"})
    public String editProduct(@Valid ProductDTO productDTO) {
        Product product = productService.get(productDTO.getId());
        ProductSpecie specie = specieService.getByName(productDTO.getSpecie());
        if(specie == null)
        {
            specie = new ProductSpecie(productDTO.getSpecie());
            specie = specieService.save(specie);
        }
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSpecie(specie);
        product.setQuantity(productDTO.getQuantity());
        product.setDefaultPrice(productDTO.getDefaultprice());
        product.setDiscount(productDTO.getDiscount());

        product.UpdateData();
        product = productService.save(product);

        return "redirect:/all/"+product.getId();
    }
    @PostMapping(value="/api/all/addProduct", consumes = {"application/x-www-form-urlencoded"})
    public String addProduct(@Valid ProductDTO productDTO) {
        ProductSpecie specie = specieService.getByName(productDTO.getSpecie());
        if(specie == null)
        {
            specie = new ProductSpecie(productDTO.getSpecie());
            specie = specieService.save(specie);
        }
        Product product = productService.save(new Product(
            productDTO.getName(),
            productDTO.getDescription(),
            specie,
            productDTO.getDefaultprice(),
            productDTO.getQuantity(),
            productDTO.getDiscount()
        ));
        List<Product> products = specie.getProducts();
        products.add(product);
        specie.setProducts(products);
        specieService.save(specie);

        return "redirect:/all/"+product.getId();
    }


}
