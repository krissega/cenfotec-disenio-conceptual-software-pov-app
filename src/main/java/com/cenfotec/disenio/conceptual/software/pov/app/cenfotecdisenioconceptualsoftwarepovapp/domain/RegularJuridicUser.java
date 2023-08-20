package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.userFabric.AbstractUser;

public class RegularJuridicUser extends AbstractUser {

    private Integer id;
    private UserType userType;
    private String username;
    private String password;
    private String lastname;
    private String address;
    private String cedulaJuridica;
    private String razonSocial;
    private String email;
    private int authenticated;

    public RegularJuridicUser() {
    }

    public RegularJuridicUser(Integer id, UserType userType, String username, String password, String lastname, String address, String cedulaJuridica, String razonSocial, String email, int authenticated) {
        this.id = id;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.address = address;
        this.cedulaJuridica = cedulaJuridica;
        this.razonSocial = razonSocial;
        this.email = email;
        this.authenticated = authenticated;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

    @Override
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getCedulaJuridica() {
        return cedulaJuridica;
    }

    @Override
    public void setCedulaJuridica(String cedulaJuridica) {
        this.cedulaJuridica = cedulaJuridica;
    }

    @Override
    public String getRazonSocial() {
        return razonSocial;
    }

    @Override
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(int authenticated) {
        this.authenticated = authenticated;
    }
}
