package pl.edu.agh.backend.db.services;

import lombok.AllArgsConstructor;
import pl.edu.agh.backend.db.ports.*;
import pl.edu.agh.backend.db.models.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ListProductRepository listProductRepository;

    public List<Product> getProducts(){ return productRepository.findAll(); }
    public Product addProduct(Product product){ return productRepository.save(product); }
    public Product modifyProduct(Product product){
        Optional<Product> productInDb = productRepository.findById(product.getID());
        Product modifiedProduct;
        if(productInDb.isPresent()){
            modifiedProduct = productInDb.get();
            modifiedProduct.setName(product.getName());
            modifiedProduct.setUserID(product.getUserID());
            //open for future extensions
        }
        else modifiedProduct = new Product();

        return productRepository.save(modifiedProduct);
    }

    public Optional<Product> removeProduct(Integer productID) {
        Optional<Product> product = productRepository.findById(productID);
        if(product.isPresent()){
            productRepository.deleteById(productID);
            listProductRepository.deleteWithProductId(productID);
        }
        return product;
    }

}