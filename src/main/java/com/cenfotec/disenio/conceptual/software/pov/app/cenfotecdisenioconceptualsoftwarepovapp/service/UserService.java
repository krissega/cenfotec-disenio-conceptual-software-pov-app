package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.User;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.userFabric.AbstractUser;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.userFabric.UserFactory;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public List<User> getAll(){
        return userDAO.getAll();
    }
    public Optional<User> getProduct(String idProduct){
        return userDAO.getUser(idProduct);
    }
    
    public User save (User user){ return userDAO.saveUser(user);}

    public User createUser(User pUser){
        User savedUser = new User();
        if(pUser.getUserType().toString().equalsIgnoreCase("User_human")){
            User regularHumanUser = User.make()
                    .setUserType(pUser.getUserType())
                    .setUsername(pUser.getUsername())
                    .setPassword(pUser.getPassword())
                    .setCedula(pUser.getCedula())
                    .setName(pUser.getName())
                    .setLastname(pUser.getLastname())
                    .setAddress(pUser.getAddress())
                    .setEmail(pUser.getEmail())
                    .setAuthenticated(pUser.getAuthenticated())
                    .build();
            savedUser = this.save(regularHumanUser);
        }else if(pUser.getUserType().toString().equalsIgnoreCase("User_juridic")){
            User regularJuridicUser = User.make()
                    .setUserType(pUser.getUserType())
                    .setUsername(pUser.getUsername())
                    .setPassword(pUser.getPassword())
                    .setCedulaJuridica(pUser.getCedulaJuridica())
                    .setRazonSocial(pUser.getRazonSocial())
                    .setAddress(pUser.getAddress())
                    .setEmail(pUser.getEmail())
                    .setAuthenticated(pUser.getAuthenticated())
                    .build();
            savedUser = this.save(regularJuridicUser);
        }else if(pUser.getUserType().toString().equalsIgnoreCase("Admin")){
            User adminUser = User.make()
                    .setUserType(pUser.getUserType())
                    .setUsername(pUser.getUsername())
                    .setPassword(pUser.getPassword())
                    .setCedula(pUser.getCedula())
                    .setName(pUser.getName())
                    .setLastname(pUser.getLastname())
                    .setAddress(pUser.getAddress())
                    .setEmail(pUser.getEmail())
                    .setAuthenticated(pUser.getAuthenticated())
                    .build();
                    savedUser = this.save(adminUser);
        }else{

        }

        return savedUser;

    }
//    public User createUser(User pUser){
//        UserType userType = pUser.getUserType();
//        String username   = pUser.getUsername();
//        String password   = pUser.getPassword();
//        String name       = pUser.getName();
//        String lastname   = pUser.getLastname();
//        String address    = pUser.getAddress();
//        String email      = pUser.getEmail();
//        String cedulaJuridica = pUser.getCedulaJuridica();
//        String razonSocial = pUser.getRazonSocial();
//        User interfaz = UserFactory.createUser(userType, username, password, name, lastname, address, email, cedulaJuridica, razonSocial);
//
//        this.save(interfaz);
//        return interfaz;
//    }
//    public IUser createAdminUser(AdminUser pUser){
//        UserType userType = pUser.getUserType();
//        String username   = pUser.getUsername();
//        String password   = pUser.getPassword();
//        String name       = pUser.getName();
//        String lastname   = pUser.getLastname();
//        String address    = pUser.getAddress();
//        String email      = pUser.getEmail();
//        String cedulaJuridica = pUser.getCedulaJuridica();
//        String razonSocial = pUser.getRazonSocial();
//        IUser user = UserFactory.createUser(userType, username, password, name, lastname, address, email, cedulaJuridica, razonSocial);
//        this.save((User) user);
//        return user;
//    }
//    public boolean deleteUser(int idUser){
//        boolean deleted = false;
//        if(getUser(idUser).isPresent()){
//            userRepository.deleteUser(idUser);
//            return true;
//        }else{
//            return false;
//        }
//    }

    public AbstractUser getUser(String idUser) {
        Optional<User> loadedUser = userDAO.getUser(idUser);
        AbstractUser iUser = UserFactory.createUser(loadedUser.get().getUserType());
        iUser.setId(loadedUser.get().getId());
        iUser.setUserType(loadedUser.get().getUserType());
        iUser.setUsername(loadedUser.get().getUsername());
        iUser.setPassword(loadedUser.get().getPassword());
        iUser.setEmail(loadedUser.get().getEmail());
        iUser.setAddress(loadedUser.get().getAddress());
        iUser.setAuthenticated(loadedUser.get().getAuthenticated());
        if(loadedUser.get().getUserType().toString().equalsIgnoreCase("Admin") || loadedUser.get().getUserType().toString().equalsIgnoreCase("User_human")){
            iUser.setName(loadedUser.get().getName());
            iUser.setLastname(loadedUser.get().getLastname());
            iUser.setCedula(loadedUser.get().getCedula());
        }else if(loadedUser.get().getUserType().toString().equalsIgnoreCase("User_juridic")){
            iUser.setRazonSocial(loadedUser.get().getRazonSocial());
            iUser.setCedulaJuridica(loadedUser.get().getCedulaJuridica());
        }
        return iUser;
    }

    public User getUserByUsername(String username){
        return userDAO.findByUsername(username);
    }
}
