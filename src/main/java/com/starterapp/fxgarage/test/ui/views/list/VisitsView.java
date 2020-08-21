package com.starterapp.fxgarage.test.ui.views.list;


import com.starterapp.fxgarage.test.backend.entity.Doctor;
import com.starterapp.fxgarage.test.backend.entity.Patient;
import com.starterapp.fxgarage.test.backend.service.PatientService;
import com.starterapp.fxgarage.test.ui.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "list",layout = MainLayout.class)
@PageTitle("Visit || PRMD")

public class VisitsView extends FormLayout {

    Binder<Patient> binder = new Binder<>(Patient.class);

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    ComboBox<Doctor> doctor = new ComboBox<>("Patient's Doctor");
    DatePicker visitDateField = new DatePicker("Date of the Visit");

    Button save = new Button("Save");
    Button delete = new Button("Delete");

    private PatientService patientService;


    Grid<Patient> grid = new Grid<>(Patient.class);



    public VisitsView(PatientService patientService, List<Doctor> doctors) {
        this.patientService = patientService;

        addClassName("visits-form");
        binder.bindInstanceFields(this);
        configureGrid();



        add(grid, fieldsLayout, createButtonsLayout());
    }

    VerticalLayout fieldsLayout = new VerticalLayout(firstName, lastName, doctor, visitDateField);

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickShortcut(Key.ENTER);

        return new HorizontalLayout(save, delete);
    }

    private void configureGrid() {

        grid.addClassName("visits-grid");
        grid.removeColumnByKey("doctor");
        grid.setColumns("firstName", "lastName");
        grid.addColumn(contact -> {
            Doctor doctor = contact.getDoctor();
            return doctor == null ? "-" : doctor.getName();

        }).setHeader("Patient's Doctor");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }








    }