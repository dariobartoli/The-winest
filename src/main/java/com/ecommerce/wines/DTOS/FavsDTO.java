package com.ecommerce.wines.DTOS;

import com.ecommerce.wines.models.Favs;

public class FavsDTO {


    private long id;
    private String name;
    private String image;



    public FavsDTO(Favs favs) {
        this.id = favs.getId();
        this.name = favs.getName();
        this.image = favs.getImage();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
