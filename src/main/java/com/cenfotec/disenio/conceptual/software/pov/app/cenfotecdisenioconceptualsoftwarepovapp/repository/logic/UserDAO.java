package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.User;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {
    @Autowired
    private UserCrudRepository userCrudRepository;

    public User findByUsername(String username) {
        return userCrudRepository.findByUsername(username);
    }

    public User login(String username, String password){return userCrudRepository.findByUsernameAndPassword(username, password);}

    public List<User> getAll(){return (List<User>) userCrudRepository.findAll();}
    public User getUserByDocument(String idUser){return userCrudRepository.getUserById(idUser);}
    public User saveUser(User user){return userCrudRepository.save(user);}
    public void deleteUser(String idUser){userCrudRepository.deleteById(idUser);}

    public Optional<User> getUser(String idUser) {
        return userCrudRepository.findById(idUser);
    }
}
