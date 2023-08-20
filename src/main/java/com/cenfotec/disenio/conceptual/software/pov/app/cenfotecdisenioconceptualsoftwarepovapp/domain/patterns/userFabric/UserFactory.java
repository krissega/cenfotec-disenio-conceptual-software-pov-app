package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.userFabric;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.AdminUser;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.RegularHumanUser;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.RegularJuridicUser;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.UserType;


public class UserFactory {
    public static AbstractUser createUser(UserType userType){

        if (userType.toString().equalsIgnoreCase("User_human")) {
            AbstractUser regularUser = new RegularHumanUser();
            return regularUser;
        }else if (userType.toString().equalsIgnoreCase("User_juridic")) {
            AbstractUser juridicUser = new RegularJuridicUser();
            return juridicUser;
        } else if(userType.toString().equalsIgnoreCase("Admin")){
            AbstractUser adminUser = new AdminUser();
            return adminUser;
        } else {
            throw new IllegalArgumentException("Invalid User Type: " + userType.toString());
        }
    }
}
