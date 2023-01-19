package com.ecommerce.wines.services.Implement;

import com.ecommerce.wines.DTOS.ClientDTO;
import com.ecommerce.wines.DTOS.ProductDTO;
import com.ecommerce.wines.models.Product;
import com.ecommerce.wines.repositories.ProductRepository;
import com.ecommerce.wines.services.ProductService;
import com.ecommerce.wines.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplement implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDTO> getProductsDTO() {
        return productRepository.findAll().stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductDTO(long id) {
        return new ProductDTO(productRepository.findById(id));
    }


    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void changeStock(int stock, String name) {
        Product product = productRepository.findByName(name);
        product.setStock(stock);
        productRepository.save(product);

    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);

    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


}
