package com.starterapp.fxgarage.test.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Doctor extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Patient> patients = new LinkedList<>();

    public Doctor() {
    }

    public Doctor(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
