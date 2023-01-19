package com.ecommerce.wines.services;

public interface EmailService {

    void sendEmail(String to, String body);
    void sendEmail1(String to, String subject, String body);

    String buildEmail(String name, String link);

}
