package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentCrudRepository extends MongoRepository<Document, Integer> {
    List<Document> findByIdUserOrderByIdAsc(String idUser);//specific user or tickets
    List<Document> findByIdUserGreaterThan(String idUser);//All Bills

    List<Document> findByIdUserAndStateEquals(String  idUser, State state);//filter by user id and state

    List<Document> findByStateAndValidBefore(State state, LocalDateTime now);//scheduled task to update valid date
    Document findById(String  id);
    void deleteById(String idDocument);
}
