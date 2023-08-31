package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Invoice;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentCrudRepository extends MongoRepository<Invoice, String> {
    List<Invoice> findByIdUserOrderByIdAsc(String idUser);//specific user or tickets
    List<Invoice> findByIdUserGreaterThan(String idUser);//All Bills

    List<Invoice> findByIdUserAndStateEquals(String  idUser, State state);//filter by user id and state

    List<Invoice> findByStateAndValidBefore(State state, LocalDateTime now);//scheduled task to update valid date
    Optional<Invoice> findById(String id);
    void deleteById(String idDocument);
}
