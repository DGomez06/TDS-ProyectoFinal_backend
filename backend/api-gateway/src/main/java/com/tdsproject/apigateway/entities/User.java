package com.tdsproject.apigateway.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer Id;
    private String firstname;
    private String lastname;
    @Column(name = "email")
    private String email;
    private String phone;
    private String password;

    public Integer getId() {
        return Id;
    }

    public User(){}
    public User(String FirstName, String LastName, String Email, String Phone, String Password) {
        firstname = FirstName;
        lastname = LastName;
        email = Email;
        phone = Phone;
        password = Password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, firstname, lastname, email, phone);
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String pass) {
        password = pass;
    }

}
