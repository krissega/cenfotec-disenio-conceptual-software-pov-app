package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.userBuilder;
import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.*;

import java.util.List;

public class UserBuilder {
    private String  id;

    private UserType userType;
    private String username;
    private String password;
    private String cedula;
    private String name;
    private String lastname;
    private String address;
    private String cedulaJuridica;
    private String razonSocial;
    private String email;
    private int authenticated;
    private List<Document> documents;

    public UserBuilder setId(String  id) {
        this.id = id;
        return this;
    }

    public UserBuilder setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setCedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder setCedulaJuridica(String cedulaJuridica) {
        this.cedulaJuridica = cedulaJuridica;
        return this;
    }

    public UserBuilder setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setAuthenticated(int authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public UserBuilder setDocuments(List<Document> documents) {
        this.documents = documents;
        return this;
    }

    public RegularHumanUser buildRegularHumanUser() {
        return new RegularHumanUser(id, userType, username, password, cedula, name, lastname, address, email, authenticated);
    }

    public RegularJuridicUser buildRegularJuridicUser() {
        return new RegularJuridicUser(id, userType, username, password, lastname, address, cedulaJuridica, razonSocial, email, authenticated);
    }

    public AdminUser buildAdminUser() {
        return new AdminUser(id, userType, username, password, cedula, name, lastname, address, email, authenticated);
    }



}
