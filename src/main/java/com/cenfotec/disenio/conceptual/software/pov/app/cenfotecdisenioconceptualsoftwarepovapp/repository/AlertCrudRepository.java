package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface AlertCrudRepository extends MongoRepository<Alert, Integer> {

    @Override
    List<Alert> findAll();
}
