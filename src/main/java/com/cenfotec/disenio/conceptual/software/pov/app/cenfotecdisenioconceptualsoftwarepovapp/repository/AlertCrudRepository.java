package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AlertCrudRepository extends MongoRepository<Alert, Integer> {

}
