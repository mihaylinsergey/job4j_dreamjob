package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private int id;
    private String email;
    private String password;

    public User() {
    }

    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{"
               + "id=" + id
               + ", email='" + email + '\''
               + ", password='" + password + '\''
               + '}';
    }
}
