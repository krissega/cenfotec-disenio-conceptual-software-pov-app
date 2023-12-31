package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.User;;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<User, Integer> {

    User getUserById(int idUser);

//    @Query(value = "Select * from documents where id_user = ?", nativeQuery = true)
//    List<Document> getDocumentsByIdUser(int idUser);

    User findByUsernameAndPassword(String username, String Password);

    User findByUsername(String username);
}
