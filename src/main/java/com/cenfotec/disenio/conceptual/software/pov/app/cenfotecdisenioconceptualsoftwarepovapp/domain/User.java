package com.cenfotec.disenio.conceptual.software.pov.app.cenfotecdisenioconceptualsoftwarepovapp.domain;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

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



    public User(){

    }

    public String getId() {
        return id;
    }

    public User setId(String  id) {
        this.id = id;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public User setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }


    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCedula() {
        return cedula;
    }

    public User setCedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCedulaJuridica() {
        return cedulaJuridica;
    }

    public User setCedulaJuridica(String cedulaJuridica) {
        this.cedulaJuridica = cedulaJuridica;
        return this;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public User setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getAuthenticated() {
        return authenticated;
    }

    public User setAuthenticated(int authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public static User make(){
        return new User();
    }

    public User build(){
        return this;
    }


}
