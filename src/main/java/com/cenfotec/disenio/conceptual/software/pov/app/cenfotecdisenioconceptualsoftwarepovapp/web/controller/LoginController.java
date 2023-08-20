package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.web.controller;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.User;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.LoginTemplateMethod.UserSecurityService UserSecurityService;
    @Autowired
    private UserService userService;
    @PostMapping("")
    public User userLogin(@RequestBody User user, Authentication authentication) {
        UserDetails userWithDetails = UserSecurityService.loadUserByUsername(user.getUsername());
        if (userWithDetails instanceof UserDetails) {
            User myUser = userService.getUserByUsername(user.getUsername());
            myUser.setAuthenticated(1);
            myUser.setPassword(user.getPassword());
            return myUser;
        } else {
            return null;
        }
    }
}

