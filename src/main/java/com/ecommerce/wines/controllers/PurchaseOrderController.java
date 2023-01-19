package com.ecommerce.wines.controllers;

import com.ecommerce.wines.DTOS.FavsDTO;
import com.ecommerce.wines.DTOS.ProductOrderDTO;
import com.ecommerce.wines.DTOS.PurchaseDTO;
import com.ecommerce.wines.DTOS.PurchaseOrderDTO;
import com.ecommerce.wines.models.*;
import com.ecommerce.wines.repositories.ProductOrderRepository;
import com.ecommerce.wines.services.ClientService;
import com.ecommerce.wines.services.ProductService;
import com.ecommerce.wines.services.PurchaseOrderService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    ClientService clientService;

    @GetMapping("/purchaseOrder")
    public List<PurchaseOrderDTO> getPurchaseOrder() {
        return purchaseOrderService.getPurchaseOrder();
    }

    @GetMapping("/clientcurrent/purchaseorder")
    public List<PurchaseOrderDTO> getPurchaseOrderClient(Authentication authentication) {
        Client clientCurrent = clientService.clientFindByEmail(authentication.getName());
        return clientCurrent.getPurchaseOrders().stream().map(purchaseOrder -> new PurchaseOrderDTO(purchaseOrder)).collect(Collectors.toList());
    }



    @PostMapping("/purchaseOrder/create")
    public ResponseEntity<?> createPurchaseOrder(Authentication authentication, @RequestBody PurchaseDTO purchaseDTO) {

        Client client = clientService.clientFindByEmail(authentication.getName());

        if (client == null){
            return new ResponseEntity<>("Client is not authenticated", HttpStatus.FORBIDDEN);
        }
        if(purchaseDTO == null){
            return  new ResponseEntity<>("Purchase is empty", HttpStatus.FORBIDDEN);
        }
        if (purchaseDTO.getProductOrderDTOS().isEmpty()){
            return new ResponseEntity<>("Product Order is empty", HttpStatus.FORBIDDEN);
        }
        List<ProductOrderDTO> productOrderDTOS = purchaseDTO.getProductOrderDTOS().stream().collect(Collectors.toList());
        List<Double> amountTotal = new ArrayList<>();
        PurchaseOrder purchaseOrder1 = new PurchaseOrder(client,0.0,LocalDateTime.now(),purchaseDTO.getPaymentMethod());

        productOrderDTOS.forEach(productOrderDTO -> {
            Product product = productService.findByName(productOrderDTO.getProductName());
            product.setStock(product.getStock() - productOrderDTO.getQuantity());
            productService.saveProduct(product);
            amountTotal.add(Math.round((product.getPrice() * productOrderDTO.getQuantity()) * 100.0) / 100.0);
            ProductOrder productOrder = new ProductOrder(productOrderDTO.getQuantity(), product, purchaseOrder1);
            purchaseOrder1.addProductOrder(productOrder);
            purchaseOrderService.savePurchaseOrder(purchaseOrder1);
            productOrderRepository.save(productOrder);
        });

        Double amountFinal = amountTotal.stream().reduce(Double::sum).orElse(null);
        purchaseOrder1.setMount(Math.round(amountFinal * 100.0) / 100.0);
        purchaseOrderService.savePurchaseOrder(purchaseOrder1);

        return new ResponseEntity<>("Purchase order created", HttpStatus.CREATED);

    }

    @GetMapping("/purchaseOrder/client")
    public Set<PurchaseOrderDTO> getClientPurchaseOrder (Authentication authentication){

        Client client = clientService.clientFindByEmail(authentication.getName());

        return client.getPurchaseOrders().stream().map(PurchaseOrderDTO::new).collect(Collectors.toSet());
    }

    Client purchaseOrderClient;
    PurchaseOrder purchaseOrder;

    @PostMapping("/pdf/request")
    public ResponseEntity<?> requestPDF(Authentication authentication, @RequestParam Long idPurchaseOrder) {

        Client client = clientService.clientFindByEmail(authentication.getName());

        if (client == null){
            return new ResponseEntity<>("Client is not authenticated", HttpStatus.FORBIDDEN);
        }
        if (idPurchaseOrder <= 0){
            return new ResponseEntity<>("The id is invalid", HttpStatus.FORBIDDEN);
        }

        purchaseOrder = purchaseOrderService.findById(idPurchaseOrder);

        if (purchaseOrder == null){
            return new ResponseEntity<>("No purchase orders found", HttpStatus.FORBIDDEN);
        }

        purchaseOrderClient = purchaseOrder.getClient();

        if (client != purchaseOrderClient){
            return new ResponseEntity<>("The purchase order does not belong to the authenticated client.", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Data sent",HttpStatus.OK);
    }

    @GetMapping("/pdf/create")
    public ResponseEntity<?> createPurchaseOrderPDF(Authentication authentication,HttpServletResponse response) throws IOException, DocumentException {

        Client client = clientService.clientFindByEmail(authentication.getName());

        if (client == null){
            return new ResponseEntity<>("Client is not authenticated", HttpStatus.FORBIDDEN);
        }
        if (purchaseOrder == null){
            return new ResponseEntity<>("No purchase orders found", HttpStatus.FORBIDDEN);
        }

        response.setContentType("application/pdf");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=purchaseOrder_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        Pdf pdf = new Pdf();
        pdf.createDocument(response);
        pdf.addTitle("Purchase Order");
        pdf.addLineJumps();
        pdf.addPurchaseOrderTable(purchaseOrder, purchaseOrderClient);
        pdf.addLineJumps();
        pdf.addProductsTable(purchaseOrder.getProductOrders());
        pdf.addLineJumps();
        pdf.addParagraph("TOTAL: $ " + purchaseOrder.getAmount());
        pdf.closeDocument();
        return new ResponseEntity<>("Pdf created", HttpStatus.OK);
    }

}
