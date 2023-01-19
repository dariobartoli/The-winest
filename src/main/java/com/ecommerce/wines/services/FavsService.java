package com.ecommerce.wines.services;

import com.ecommerce.wines.DTOS.FavsDTO;
import com.ecommerce.wines.models.Favs;

import java.util.List;

public interface FavsService {

    public List<FavsDTO> getAllFavsDTO();



    public void saveFavs(Favs favs);

    public void deleteFavs(Favs favs);

    public Favs getFavById(long id);
}
