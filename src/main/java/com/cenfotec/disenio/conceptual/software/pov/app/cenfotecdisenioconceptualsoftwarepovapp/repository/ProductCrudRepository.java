package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;


import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCrudRepository extends MongoRepository<Product, Integer> {

    Optional<List<Product>> findByQtyStockLessThan(int qtyStock);
//    Optional<List<Product>> findByDocuments(Integer documentID);
    Product findById(String id);
    void deleteById(String id);


}
