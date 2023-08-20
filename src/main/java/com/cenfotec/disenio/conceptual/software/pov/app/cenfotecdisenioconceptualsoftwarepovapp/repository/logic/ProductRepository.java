package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic;


import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.ProductCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    @Autowired
    private ProductCrudRepository productCrudRepository;

    public List<Product> getAll(){return (List<Product>) productCrudRepository.findAll();}
    public Optional<List<Product>> getLowInventory(int qty){
        return productCrudRepository.findByQtyStockLessThan(qty);
    }

    public Optional<Product> getProduct(int idProduct){
        return productCrudRepository.findById(idProduct);
    }

    public Product saveProduct(Product product){
        return productCrudRepository.save(product);
    }

    public void deleteProduct(int idProduct){
        productCrudRepository.deleteById(idProduct);
    }

    public Optional<Product> findById(int idProduct) {
        return productCrudRepository.findById(idProduct);
    }



}
