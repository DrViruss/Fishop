package com.example.fishop.repo;

import com.example.fishop.entity.Product;
import com.example.fishop.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product,Long> {
    public Iterable<Product> findByNameContaining(String name);
    public Iterable<Product> findBySpecieId(Long specieId);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.specie.id = ?2 AND p.price >= ?3 AND p.price <= ?4")
    public Iterable<Product> searchByParams(String name,Long specieid,int minimum_price,int maximum_price);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.price >= ?2 AND p.price <= ?3")
    public Iterable<Product> searchByParams(String name,int minimum_price,int maximum_price);

    @Query("SELECT p FROM Product p WHERE p.specie.id = ?1 AND p.price >= ?2 AND p.price <= ?3")
    public Iterable<Product> searchByParams(Long specieid,int minimum_price,int maximum_price);

    @Query("SELECT p FROM Product p WHERE p.price >= ?1 AND p.price <= ?2")
    public Iterable<Product> searchByParams(int minimum_price,int maximum_price);

    @Query("SELECT p FROM Product p ORDER BY p.price DESC LIMIT 1")
    public Product getWithHighestPrice();
}
