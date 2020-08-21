package com.starterapp.fxgarage.test.backend.entity;

import javax.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Patient extends AbstractEntity implements Cloneable {

    public enum ChronicDisease {
        ChronicDisease1, ChronicDisease2, ChronicDisease3, ChronicDisease4, ChronicDisease5
    }

    public enum SimpleDisease {
        SimpleDisease1, SimpleDisease2, SimpleDisease3, SimpleDisease4, SimpleDisease5, SimpleDisease6
    }

    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Patient.ChronicDisease chronicDisease;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Patient.SimpleDisease simpleDisease;



    public ChronicDisease getChronicDisease() {
        return chronicDisease;
    }

    public void setChronicDisease(ChronicDisease chronicDisease) {
        this.chronicDisease = chronicDisease;
    }

    public SimpleDisease getSimpleDisease(){
        return simpleDisease;
    }

    public void setSimpleDisease(SimpleDisease simpleDisease) {
        this.simpleDisease = simpleDisease;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }



    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }



    public void setDoctor(Doctor doctor) {
        this.doctor = doctor; }

    public Doctor getDoctor() {

        return doctor;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }


}
