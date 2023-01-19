package com.ecommerce.wines.controllers;

import com.ecommerce.wines.DTOS.MomentDTO;
import com.ecommerce.wines.models.Client;
import com.ecommerce.wines.models.Moment;
import com.ecommerce.wines.services.ClientService;
import com.ecommerce.wines.services.MomentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MomentController {

    @Autowired
    MomentService momentService;
    @Autowired
    ClientService clientService;

    @GetMapping("/clients/moments")
    public List<MomentDTO> getAll() {
        return momentService.getAllMomentDTO();
    }

    @GetMapping("/clients/moments/current")
    public Set<MomentDTO> getMomentsClientCurrent(Authentication authentication) {
        Client clientCurrent = clientService.clientFindByEmail(authentication.getName());
        return clientCurrent.getMoments().stream().map(MomentDTO::new).collect(Collectors.toSet());
    }

    @PostMapping("/clients/moments")
    public ResponseEntity<?> newMoment(@RequestParam String img, @RequestParam String title, @RequestParam String description, Authentication authentication){

        Client clientCurrent = clientService.clientFindByEmail(authentication.getName());

        if(clientCurrent == null){
            return new ResponseEntity<>("Client is not authenticated", HttpStatus.FORBIDDEN);
        }
        if(img.isEmpty()){
            return new ResponseEntity<>("Image is empty", HttpStatus.FORBIDDEN);
        }
        if(title.isEmpty()){
            return new ResponseEntity<>("Title is empty", HttpStatus.FORBIDDEN);
        }
        if(description.isEmpty()){
            return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
        }

        Moment newMoment = new Moment(img, title, description, clientCurrent);
        momentService.saveMoment(newMoment);
        clientCurrent.addMoment(newMoment);
        clientService.saveClient(clientCurrent);

        return new ResponseEntity<>("Moment created",HttpStatus.CREATED);
    }

    @DeleteMapping("/clients/moments/delete")
    public ResponseEntity<?> deleteMoment(Authentication authentication, @RequestParam int id){
        Client clientCurrent = clientService.clientFindByEmail(authentication.getName());
        Moment moment = momentService.momentFindById(id);
        if (clientCurrent == null){
            return new ResponseEntity<>("unauthenticated client", HttpStatus.FORBIDDEN);
        }
        if (!clientCurrent.getMoments().stream().map(Moment::getId).collect(Collectors.toSet()).contains(moment.getId())){
            return new ResponseEntity<>("this moment does not belong to you", HttpStatus.FORBIDDEN);
        }
        if(id <= 0){
            return new ResponseEntity<>("Missing id", HttpStatus.FORBIDDEN);
        }
        momentService.deleteMoment(moment);
        return new ResponseEntity<>("Moment deleted", HttpStatus.ACCEPTED);
    }
}
