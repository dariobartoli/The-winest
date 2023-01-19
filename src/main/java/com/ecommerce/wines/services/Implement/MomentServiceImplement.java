package com.ecommerce.wines.services.Implement;

import com.ecommerce.wines.DTOS.MomentDTO;
import com.ecommerce.wines.models.Moment;
import com.ecommerce.wines.repositories.MomentRepository;
import com.ecommerce.wines.services.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MomentServiceImplement implements MomentService {

    @Autowired
    MomentRepository momentRepository;

    @Override
    public void saveMoment(Moment moment) {
        momentRepository.save(moment);
    }

    @Override
    public List<MomentDTO> getAllMomentDTO() {
        return momentRepository.findAll().stream().map(moment -> new MomentDTO(moment)).collect(Collectors.toList());
    }

    @Override
    public Moment momentFindById(long id) {
        return momentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMoment(Moment moment) {
        momentRepository.delete(moment);
    }
}
