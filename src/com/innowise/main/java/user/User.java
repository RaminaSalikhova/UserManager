package com.innowise.main.java.user;

import java.util.List;
import java.util.Objects;

public class User {
    Long id;
    String name;
    String lastname;
    String email;
    List<String> roles;
    List<String> numbers;

    public User() {
    }

    public User(String name, String lastname, String email, List<String> roles, List<String> numbers) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
        this.numbers = numbers;
    }

    public User(Long id,String name, String lastname, String email, List<String> roles, List<String> numbers) {
        this.id=id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
        this.numbers = numbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && lastname.equals(user.lastname) && email.equals(user.email) && roles.equals(user.roles) && numbers.equals(user.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastname, email, roles, numbers);
    }
}
