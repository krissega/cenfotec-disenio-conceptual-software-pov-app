package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.userFabric;


import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.UserType;

public interface IUser {

    void setUserType(UserType userType);

    void setUsername(String username);

    void setPassword(String password);

    void setName(String name);

    void setLastname(String lastname);

    void setAddress(String address);

    void setEmail(String email);

    void setCedulaJuridica(String cedulaJuridica);

    void setRazonSocial(String razonSocial);

    void setCedula(String cedula);

    void setId(String id);
}
