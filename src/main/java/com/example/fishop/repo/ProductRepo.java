package com.example.fishop.repo;

import com.example.fishop.entity.Product;
import com.example.fishop.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product,Long> {
    public Iterable<Product> findByNameContaining(String name);
    public Iterable<Product> findBySpecieId(Long specieId);

    @Query("SELECT *\n" +
            "FROM products\n" +
            "WHERE name LIKE '%?1%'\n" +
                "AND price >= ?3\n" +
                "AND price <= ?4\n" +
                "AND specie_id = ?2;")
    public Iterable<Product> searchByParams(String name,Long specie_id,int minimum_price,int maximum_price);

    @Query("SELECT *\n" +
            "FROM products\n" +
            "WHERE name LIKE '%?1%'\n" +
                "AND price >= ?2\n" +
                "AND price <= ?3;")
    public Iterable<Product> searchByParams(String name,int minimum_price,int maximum_price);

    @Query("SELECT *\n" +
            "FROM products\n" +
            "WHERE specie_id = ?1'\n" +
            "AND price >= ?2\n" +
            "AND price <= ?3;")
    public Iterable<Product> searchByParams(Long specie_id,int minimum_price,int maximum_price);

    @Query("SELECT *\n" +
            "FROM products\n" +
            "WHERE price >= ?1\n" +
            "AND price <= ?2;")
    public Iterable<Product> searchByParams(int minimum_price,int maximum_price);
}
