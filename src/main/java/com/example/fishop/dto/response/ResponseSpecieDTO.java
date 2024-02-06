package com.example.fishop.dto.response;

import com.example.fishop.entity.ProductSpecie;

public class ResponseSpecieDTO {
    Long id;
    String name;

    public ResponseSpecieDTO(ProductSpecie specie) {
        this.id = specie.getId();
        this.name = specie.getName();
    }

    public ResponseSpecieDTO() {
    }

    public ResponseSpecieDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
