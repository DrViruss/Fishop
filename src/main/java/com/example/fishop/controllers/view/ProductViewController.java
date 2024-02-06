package com.example.fishop.controllers.view;

import com.example.fishop.dto.ProductDTO;
import com.example.fishop.dto.response.ResponseProductDTO;
import com.example.fishop.dto.response.ResponseSpecieDTO;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.ProductSpecie;
import com.example.fishop.services.ProductService;
import com.example.fishop.services.ProductSpecieService;
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
    public String all(Model model)
    {
        List<ResponseProductDTO> products = productService.get();

        model.addAttribute("price",99999); //TODO: ask in db max price
        model.addAttribute("products",products);
        model.addAttribute("species",specieService.get());
        return "all";
    }

    @PostMapping(value = "/all/search")
    public String search(@RequestParam(defaultValue = "") String text,@RequestParam(defaultValue = "-1") Long specieid,@RequestParam(defaultValue = "0") int minPrice,@RequestParam int maxPrice, Model model) {
        model.addAttribute("price",99999); //TODO: ask in db max price
        model.addAttribute("products",productService.search(text,specieid,minPrice,maxPrice));
        model.addAttribute("species",specieService.get());
        return "all";
    }


    @GetMapping(value = "/all/{productId}")
    public String getProductDetails(@PathVariable Long productId, Model model) {
        model.addAttribute("product",new ResponseProductDTO(productService.get(productId)));
        return "product";
    }

    @GetMapping(value = "/all/{productId}/edit")
    public String productEdit(@PathVariable Long productId, Model model) {
        model.addAttribute("species",specieService.get());
        model.addAttribute("product",new ResponseProductDTO(productService.get(productId)));
        return "product-edit";
    }

    @PostMapping(value = "/api/all/remove")
    public String remove(Long productId) {
        productService.remove(productId);
        return redirectToAll();
    }

    @PostMapping(value="/api/all/editProduct", consumes = {"application/x-www-form-urlencoded"})
    public String editProduct(ProductDTO productDTO) {
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

        return redirectToProduct(product.getId());
    }
//TODO: удаление из карзины
    @PostMapping(value="/api/all/addProduct", consumes = {"application/x-www-form-urlencoded"})
    public String addProduct(ProductDTO productDTO) {
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

        return redirectToProduct(product.getId());
    }

    private String redirectToProduct(Long id)
    {
        return redirectToAll()+"/"+id;
    }

    private String redirectToAll()
    {
        return redirectTo("all");
    }

    private String redirectTo(String to)
    {
        return "redirect:/"+to;
    }

}