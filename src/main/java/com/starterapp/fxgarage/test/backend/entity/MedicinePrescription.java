package com.starterapp.fxgarage.test.backend.entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.LinkedList;
import java.util.List;

public class MedicinePrescription {
    private List<Patient> patients = new LinkedList<>();

    public enum Medicine{

        MEDICINE1, MEDICINE2, MEDICINE3, MEDICINE4
    }

    private Integer quantity = 1;

    private String name = "";

    private String date = "";


    @Enumerated(EnumType.STRING)
    private MedicinePrescription.Medicine medicine;

    public Medicine getMedicine() {
        return medicine;
    }
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }



}
