package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Discount;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface DiscountCrudRepository extends MongoRepository<Discount, Integer> {
    List<Discount> findAll();
}
