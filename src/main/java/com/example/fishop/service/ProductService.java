package com.example.fishop.service;

import com.example.fishop.dto.response.ResponseProductDTO;
import com.example.fishop.entity.Product;
import com.example.fishop.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo repo;

    private int maxPrice;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
        if(repo.count() > 0)
            maxPrice = getDBPrice();
    }

    public Product get(Long id) {
        Optional<Product> optional = repo.findById(id);
        return optional.orElse(null);
    }

    public List<ResponseProductDTO> get() {
        List<ResponseProductDTO> result = new ArrayList<>();
        Iterable<Product> optional = repo.findAll();
        optional.forEach(product -> {
            ResponseProductDTO dto = new ResponseProductDTO(product);
            result.add(dto);
        });
        return result;
    }

    public List<ResponseProductDTO> search(String search, Long specieId, int minPrice, int maxPrice)
    {
        List<ResponseProductDTO> result = new ArrayList<>();

        if(!search.isBlank()) {
            Iterable<Product> iterable;
            if(specieId != -1) {
                iterable = repo.searchByParams(search,specieId,minPrice,maxPrice);
            }
            else
                iterable = repo.searchByParams(search,minPrice,maxPrice);

            iterable.forEach(product -> result.add(new ResponseProductDTO(product)));
        }
        else if(specieId != -1) {
            repo.searchByParams(specieId,minPrice,maxPrice).forEach(product -> result.add(new ResponseProductDTO(product)));
        }
        else {
            repo.searchByParams(minPrice,maxPrice).forEach(product -> result.add(new ResponseProductDTO(product)));
        }
        return result;
    }

    public Product getWithHighestPrice()
    {
        return repo.getWithHighestPrice();
    }

    public Product save(Product product) {
        if(product.getPrice() > this.maxPrice) this.maxPrice = (int) Math.ceil(product.getPrice());
        return repo.save(product);
    }

    public void remove(Long id) {
        Product product = get(id);
        if(product == null) return;
        repo.delete(product);

        if(product.getPrice() >= (this.maxPrice-1)) this.maxPrice = getDBPrice();

    }

    public List<ResponseProductDTO> getBest()
    {
        List<ResponseProductDTO> result = new ArrayList<>();
        Iterable<Product> prods = repo.getWithHighestRating();
        prods.forEach(product -> {
            result.add(new ResponseProductDTO(product));
        });

        return result;
    }

    private int getDBPrice() {
        return (int) Math.ceil(repo.getWithHighestPrice().getPrice());
    }

    public int getMaxPrice() {
        return this.maxPrice;
    }
}
