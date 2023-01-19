package com.ecommerce.wines.services;

import com.ecommerce.wines.DTOS.MomentDTO;
import com.ecommerce.wines.models.Moment;

import java.util.List;

public interface MomentService {

    public void saveMoment(Moment moment);

    public List<MomentDTO> getAllMomentDTO();

    public Moment momentFindById(long id);

    public void deleteMoment(Moment moment);
}
