package com.example.fishop.service;

import com.example.fishop.dto.response.ResponseSpecieDTO;
import com.example.fishop.entity.ProductSpecie;
import com.example.fishop.repo.ProductSpecieRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSpecieService {
    private ProductSpecieRepo repo;

    public ProductSpecieService(ProductSpecieRepo repo) {
        this.repo = repo;
    }

    public ProductSpecie getById(Long id)
    {
        return repo.findById(id).orElse(null);
    }

    public ProductSpecie getByName(String specieName)
    {
        return repo.getSpecieByName(specieName);
    }

    public List<ResponseSpecieDTO> get()
    {
        List<ResponseSpecieDTO> species = new ArrayList<>();
        repo.findAll().forEach(specie -> {
            ResponseSpecieDTO dto = new ResponseSpecieDTO(specie);
            species.add(dto);
        });
        return species;
    }

    public ProductSpecie save(ProductSpecie specie)
    {
        return repo.save(specie);
    }


}
