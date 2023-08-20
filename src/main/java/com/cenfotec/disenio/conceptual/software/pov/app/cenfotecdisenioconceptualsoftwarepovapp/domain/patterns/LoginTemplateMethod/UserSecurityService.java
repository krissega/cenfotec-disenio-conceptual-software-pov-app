package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.LoginTemplateMethod;



import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.repository.logic.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public UserSecurityService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.User userEntity = this.userDAO.findByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User: "+username+" not found");
        }

        String[] roles = new String[1];
        roles[0] = userEntity.getUserType().toString();

        return User.builder().username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(roles)
                .build();
    }
}
