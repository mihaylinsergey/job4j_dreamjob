package ru.job4j.dream.model;

import java.time.LocalDate;
import java.util.Objects;

public class Candidate {

    private int id;
    private String name;
    private byte[] photo;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setName(String name) {
        this.name = name;
    }

      public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        Candidate candidate = (Candidate) object;
        return id == candidate.id;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
