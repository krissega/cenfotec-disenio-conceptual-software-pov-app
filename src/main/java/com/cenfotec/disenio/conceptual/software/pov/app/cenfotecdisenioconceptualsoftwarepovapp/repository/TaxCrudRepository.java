package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Tax;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface TaxCrudRepository extends MongoRepository<Tax, Integer> {
    List<Tax> findAll();
}
