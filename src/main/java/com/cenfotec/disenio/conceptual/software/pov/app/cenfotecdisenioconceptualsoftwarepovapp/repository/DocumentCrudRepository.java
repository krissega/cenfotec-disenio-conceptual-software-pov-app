package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.Document;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.State;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.time.LocalDateTime;
import java.util.List;

public interface DocumentCrudRepository extends MongoRepository<Document, Integer> {
    List<Document> findByIdUserOrderByIdAsc(int idUser);//specific user or tickets
    List<Document> findByIdUserGreaterThan(int idUser);//All Bills

    List<Document> findByIdUserAndStateEquals(int idUser, State state);//filter by user id and state

    List<Document> findByStateAndValidBefore(State state, LocalDateTime now);//scheduled task to update valid date
    Document findById(int id);






}
