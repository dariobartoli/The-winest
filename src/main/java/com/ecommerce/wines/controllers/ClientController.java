package com.ecommerce.wines.controllers;

import com.ecommerce.wines.DTOS.ClientDTO;
import com.ecommerce.wines.models.Client;
import com.ecommerce.wines.services.ClientService;
import com.ecommerce.wines.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClientsDTO() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientDTO(@PathVariable Long id) {
        return clientService.getClientDTO(id);
    }

    @PostMapping("/clients/create")
    public ResponseEntity<?> createClient(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password
    ){
        if(clientService.clientFindByEmail(email) != null){
            return new ResponseEntity<>("The email is already in use",HttpStatus.FORBIDDEN);
        }
        if (firstName.isEmpty()){
            return new ResponseEntity<>("First name is empty", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()){
            return new ResponseEntity<>("Last name is empty", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()){
            return new ResponseEntity<>("Email is empty", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()){
            return new ResponseEntity<>("Password is empty", HttpStatus.FORBIDDEN);
        }
        Set<String> allTokens = clientService.getAllTokens();
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (allTokens.contains(token));

        Client client = new Client(firstName,lastName,email,passwordEncoder.encode(password), "https://i.ibb.co/QDk1L3J/user.png",token,false);

        clientService.saveClient(client);

        String link = "http://localhost:8080/api/clients/confirm/" + token;

        emailService.sendEmail(client.getEmail(), emailService.buildEmail(client.getFirstName(), link));

        return new ResponseEntity<>("Client created", HttpStatus.CREATED);
    }

    @GetMapping("/clients/confirm/{token}")
    public ResponseEntity<?> confirmClient (@PathVariable String token, HttpServletResponse response) throws IOException {

        if (token.isEmpty()){
            return new ResponseEntity<>("Token cannot be found", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findByToken(token);

        if (client ==null){
            return new ResponseEntity<>("No client with this token was found.", HttpStatus.FORBIDDEN);
        }

        if (client.isActive()){
            return new ResponseEntity<>("The client is already activated", HttpStatus.FORBIDDEN);
        }

        client.setActive(true);
        clientService.saveClient(client);
        response.sendRedirect("/web/src/pages/login-register.html?confirmed=true");

        return new ResponseEntity<>("Client Confirmed", HttpStatus.OK);
    }

    @PatchMapping("/clients/delete")
    public ResponseEntity<?> disabledClient(@RequestParam String email, Authentication authentication){

        Client clientCurrent = clientService.clientFindByEmail(authentication.getName());

        Client client = clientService.clientFindByEmail(email);

        if (clientCurrent == null){
            return  new ResponseEntity<>("Client is not authenticated", HttpStatus.FORBIDDEN);
        }
        if(client == null){
            return new ResponseEntity<>("There is no client with this email address", HttpStatus.FORBIDDEN);
        }
        if (client != clientCurrent){
            return new ResponseEntity<>("The entered email does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }
        if(email.isEmpty()){
            return new ResponseEntity<>("Email is empty", HttpStatus.FORBIDDEN);
        }
        if (!client.isActive()){
            return  new ResponseEntity<>("The client is already disabled", HttpStatus.FORBIDDEN);
        }
        client.setActive(false);
        clientService.saveClient(client);

        return new ResponseEntity<>("Client Disabled",HttpStatus.OK);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication){
        return new ClientDTO(clientService.clientFindByEmail(authentication.getName()));
    }

    @PatchMapping("/clients/current/changePassword")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestParam String password, @RequestParam String oldPassword){

        Client clientCurrent = clientService.clientFindByEmail(authentication.getName());

        if (clientCurrent == null){
            return new ResponseEntity<>("Client is not authenticated", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()){
            return new ResponseEntity<>("Password is empty",HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoder.matches(oldPassword, clientCurrent.getPassword())){
            return new ResponseEntity<>("Wrong Password",HttpStatus.FORBIDDEN);
        }

        clientCurrent.setPassword(passwordEncoder.encode(password));
        clientService.saveClient(clientCurrent);

        return new ResponseEntity<>("Changed password",HttpStatus.OK);
    }

    @PostMapping("/clients/current/uploadImage")
    public ResponseEntity<?> saveImage(@RequestParam String image,Authentication authentication) {

        Client clientCurrent = clientService.clientFindByEmail(authentication.getName());

        if (clientCurrent == null){
            return new ResponseEntity<>("Client is not authenticated",HttpStatus.FORBIDDEN);
        }
        if (image.isEmpty()){
            return new ResponseEntity<>("Image is empty",HttpStatus.FORBIDDEN);
        }

        clientCurrent.setImage(image);
        clientService.saveClient(clientCurrent);

        return new ResponseEntity<>("Changed profile image", HttpStatus.OK);
    }

}
