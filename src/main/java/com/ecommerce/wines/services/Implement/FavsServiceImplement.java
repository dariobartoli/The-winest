package com.ecommerce.wines.services.Implement;

import com.ecommerce.wines.DTOS.FavsDTO;
import com.ecommerce.wines.models.Favs;
import com.ecommerce.wines.repositories.FavsRepository;
import com.ecommerce.wines.services.FavsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavsServiceImplement implements FavsService {

    @Autowired
    FavsRepository favsRepository;


    @Override
    public List<FavsDTO> getAllFavsDTO() {
        return favsRepository.findAll().stream().map(favs -> new FavsDTO(favs)).collect(Collectors.toList());
    }



    @Override
    public void saveFavs(Favs favs) {
        favsRepository.save(favs);
    }

    @Override
    public void deleteFavs(Favs favs) {
        favsRepository.delete(favs);
    }

    @Override
    public Favs getFavById(long id) {
        return favsRepository.findById(id).orElse(null);
    }
}