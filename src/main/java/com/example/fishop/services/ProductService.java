package com.example.fishop.services;

import com.example.fishop.dto.ProductDTO;
import com.example.fishop.dto.response.ResponseProductDTO;
import com.example.fishop.entity.Product;
import com.example.fishop.entity.ProductSpecie;
import com.example.fishop.repo.ProductRepo;
import com.example.fishop.repo.ProductSpecieRepo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo repo;
    private ProductSpecieRepo specieRepo;

    public ProductService(ProductRepo repo, ProductSpecieRepo specieRepo) {
        this.repo = repo;
        this.specieRepo = specieRepo;
    }

    public Long isPresent(Product product){
        Product product1 = get(product.getId());
        if(product1 == null) return null;
        return product1.getId();
    }

    public Product get(Long id)
    {
        Optional<Product> optional = repo.findById(id);
        return optional.orElse(null);
    }

    public List<ResponseProductDTO> get()
    {
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

//            Iterable<Product> iterable = repo.findByNameContaining(search);
//
//            if (specieId != -1)
//                for (Product product : iterable)
//                    if(Objects.equals(product.getSpecie().getId(), specieId))
//                        result.add(new ResponseProductDTO(product));

        }
        else if(specieId != -1) {
            repo.searchByParams(search,specieId,minPrice,maxPrice).forEach(product -> result.add(new ResponseProductDTO(product)));

//            Optional<ProductSpecie> optional = specieRepo.findById(specieId);
//            optional.ifPresent(productSpecie -> productSpecie.getProducts().forEach(product -> {
//                    result.add(new ResponseProductDTO(product));
//            }));
        }
        else {
            repo.searchByParams(minPrice,maxPrice).forEach(product -> result.add(new ResponseProductDTO(product)));


//            repo.findAll().forEach(product -> {
//                result.add(new ResponseProductDTO(product));
//            });
        }
//        for (ResponseProductDTO product : result)
//            if(!(product.getPrice() > minPrice && product.getPrice() < maxPrice))
//                result.remove(product);

        return result;
    }

    public Product save(Product product)
    {
        return repo.save(product);
    }

    public void remove(Long id)
    {
        repo.deleteById(id);
    }
}
