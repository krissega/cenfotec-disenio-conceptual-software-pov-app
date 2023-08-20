package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;

import com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain.patterns.userFabric.AbstractUser;
public class RegularHumanUser extends AbstractUser {

    private Integer id;
    private UserType userType;
    private String username;
    private String password;
    private String cedula;
    private String name;
    private String lastname;
    private String address;
    private String email;
    private int authenticated;

    public RegularHumanUser(){}
    public RegularHumanUser(Integer id, UserType userType, String username, String password, String cedula, String name, String lastname, String address, String email, int authenticated) {
        this.id = id;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.cedula = cedula;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
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
    public String getCedula() {
        return cedula;
    }

    @Override
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
