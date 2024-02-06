package com.example.fishop.repo;

import com.example.fishop.entity.ProductSpecie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecieRepo extends JpaRepository<ProductSpecie,Long> {
    public ProductSpecie getSpecieByName(String specie);
    public ProductSpecie getSpecieById(Long id);
}
